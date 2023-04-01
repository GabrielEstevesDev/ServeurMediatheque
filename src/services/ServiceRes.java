package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.GregorianCalendar;

import bserveur.ServiceAbstract;
import bttp.bttp;
import document.Document;
import document.RestrictionException;
import mediatheque.Abonne;
import mediatheque.IDocument;
import mediatheque.Mediatheque;

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
			String num =socketIn.readLine();
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {
				socketOut.println(bttp.encoder("Le numéro d'abonné est incorrect."));
				//fermeture du service
				this.getSocket().close();
			}
			
			Date today = new Date();
			if(ab.getDateBan()!=null && ab.getDateBan().after(today)) {
				Date date = ab.getDateBan();
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				socketOut.println(bttp.encoder("Vous êtes toujours bannis jusqu'au "+calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+(calendar.get(GregorianCalendar.MONTH)+1)+"/"+calendar.get(GregorianCalendar.YEAR)+"."));
				this.getSocket().close();
			}
			//demande du numéro de document pour le réserver
			socketOut.println(bttp.encoder("Quel document que vous voulez reserver ? Saisissez le numéro."));
			String numDoc =socketIn.readLine();
			IDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			if(doc==null) {
				socketOut.println(bttp.encoder("Ce "+Document.class.getSimpleName()+" n'existe pas."));
				//on arrête le service
				this.getSocket().close();
			}
			
			try {
				//réservation
				doc.reservationPour(ab);
				socketOut.println(bttp.encoder("La réservation a bien été effectué pour le "+ doc.getClass().getSimpleName() +" "+numDoc));

			} catch ( RestrictionException e) {
				//on récupère le message de l'exception
				String msgException = e.getMessage();
				//si le réserveur à déjà emprunté ou déja réservé le document
				if(doc.emprunteur()!=null && doc.emprunteur().equals(ab) || doc.reserveur()!=null && doc.reserveur().equals(ab)) {
					//on affiche le message d'erreur
					socketOut.println(bttp.encoder(msgException));
				}
				else {//sinon
					//on lui demande si il veut recevoir un mail quand il sera disponible
					socketOut.println(bttp.encoder(msgException+"\nSi vous voulez recevoir une alerte quand le "+ doc.getClass().getSimpleName()+" sera disponible ? Entrez 1. Sinon entrez 0."));
					String rep =socketIn.readLine();
					if(rep.equals("1")) {// si oui
						//on met l'attribut recevoirMailQuandDisponible à true
						Mediatheque.getDoc(Integer.parseInt(numDoc)).setSendMailTrue();
						//fermeture du service
						socketOut.println(bttp.encoder("Vous recevrez un message quand le "+ doc.getClass().getSimpleName()+" sera de nouveau disponible"));
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

