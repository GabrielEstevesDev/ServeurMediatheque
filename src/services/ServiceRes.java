package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import bserveur.ServiceAbstract;
import bttp.bttp;
import mediatheque.Abonne;
import mediatheque.AbonneBanisException;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;

public class ServiceRes extends ServiceAbstract {
	public ServiceRes(Socket socketCotéServeur) {
		super(socketCotéServeur);
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));

			PrintWriter socketOut = new PrintWriter (this.getSocket().getOutputStream ( ), true);
			
			//demande du numéro d'abonné
			socketOut.println(bttp.encoder(Mediatheque.afficherDocs()+"\nQuel est votre numéro d'abonné ?"));
			String num =bttp.decoder(socketIn.readLine());
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {
				socketOut.println(bttp.encoder("Le numéro d'abonné est incorrect."));
				//fermeture du service
				this.getSocket().close();
				return; 
			}
			
			try { //verifie si l'abonne n'est pas déjà bannis
				ab.estBannis();
			} catch (AbonneBanisException e1) {
				socketOut.println(bttp.encoder(e1.getMessage()));
				this.getSocket().close();
				return;//il est toujours bannis alors nous le lui indiquons et fermons la communication
			}
			//demande du numéro de document pour le réserver
			socketOut.println(bttp.encoder("Quel document vous voulez reserver ? Saisissez le numéro."));
			String numDoc =bttp.decoder(socketIn.readLine());
			IDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			if(doc==null) {
				socketOut.println(bttp.encoder("Ce document n'existe pas."));
				//on arrête le service
				this.getSocket().close();
				return;
			}
			
			try {
				//réservation
				doc.reservationPour(ab);
				socketOut.println(bttp.encoder("La réservation a bien été effectué pour le document "+numDoc+ " pendant 2h."));

			} catch ( RestrictionException e) {
				//on récupère le message de l'exception
				String msgException = e.getMessage();
				//si l'abonné n'a pas l'âge on ne lui demande pas s'il veut recevoir un mail quand il sera disponible
				//pb de dépendance avec ce if pour tester une condition (nous n'avons pas trouvé d'autre alternative)
				if(msgException.equals("Vous n'avez pas l'âge recquis pour réserver ce DVD")) {
					socketOut.println(bttp.encoder(msgException));
				}
				//si le réserveur à déjà emprunté ou déja réservé le document
				if(ab.equals(doc.emprunteur()) || ab.equals(doc.reserveur()) ) {
					//on affiche le message d'erreur
					socketOut.println(bttp.encoder(msgException));
				}
				else {//sinon
					//on lui demande si il veut recevoir un mail quand il sera disponible
					socketOut.println(bttp.encoder(msgException+"\nSi vous voulez recevoir une alerte quand le document sera disponible ? Entrez 1. Sinon entrez 0."));
					String rep =bttp.decoder(socketIn.readLine());
					if(rep.equals("1")) {// si oui
						//on met l'attribut recevoirMailQuandDisponible à true
						doc.setSendMailTrue();
						//fermeture du service
						socketOut.println(bttp.encoder("Vous recevrez un message quand le document sera de nouveau disponible"));
					}
					else
						//si non fermeture du service 
						socketOut.println(bttp.encoder("Bien reçu, vous ne recevrez pas de message."));
				}
			}

		} catch (IOException e) {
			this.closeSocket();
			e.printStackTrace();
		}

	}

}

