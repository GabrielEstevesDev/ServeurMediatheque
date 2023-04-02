package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import bserveur.ServiceAbstract;
import bttp.bttp;
import mediatheque.IDocument;
import mediatheque.Mediatheque;

public class ServiceRet extends ServiceAbstract{

	public ServiceRet(Socket socketCoteServeur) {
		super(socketCoteServeur);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
			
			PrintWriter socketOut = new PrintWriter (this.getSocket().getOutputStream ( ), true);
			
			//demande numéro du document pour le retour
			socketOut.println(bttp.encoder("Quel document souhaitez-vous retourner ? Saisissez son numéro."));
			String numDoc =socketIn.readLine();
			IDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			
			if(doc==null) { 
				socketOut.println(bttp.encoder("Ce document n'existe pas."));
				//on arrête le service
				this.getSocket().close();
				return;
			}
			else if(doc.emprunteur()==null && doc.reserveur()==null){ //si pas de réserveur ou pas d'emprunteur
				socketOut.println(bttp.encoder("Vous ne pouvez pas rendre un document qui n'a pas été emprunté ou réservé."));
				this.getSocket().close();
				return; //fin de la fonction
			}
			else if(doc.reserveur()!=null) { //si réservé
				socketOut.println(bttp.encoder("La réservation a bien été annulé pour ce Document."));
				doc.retour(); //annulation de réservation
				this.getSocket().close();
				return;
			}
			String msg = "Attention ! Vous êtes bannis 1 mois !";
			boolean ban=false;
			if (doc.getBonEtat()) {
				// demande de l'état du document
				socketOut.println(bttp.encoder("Dans quel état est votre document ? Saisissez 1 si il est en bon état. 0 si il est en mauvais état."));
				String etat =socketIn.readLine();
				if(etat.equals("0")) {//si mauvais
					doc.mauvaisEtat(); //on met l'état du doc en mauvais état et on bannis l'abonne
					msg+=" Vous avez rendu le document en mauvaise état.";
					ban=true;
				}
			}
			
			
			if(doc.renduEnretard()) { //si le rendu est en retard 
				doc.emprunteur().banniMois(); //on bannis l'abonne 
				if(ban==false)
					msg+=" Vous avez rendu le "+doc.getClass().getSimpleName()+" avec un retard de plus de 2 semaines.";
				else
					msg+=" De plus vous avez rendu le "+doc.getClass().getSimpleName()+" avec un retard de plus de 2 semaines.";
				ban=true;
			}
			doc.retour();
			//fin service
			if(ban) //si l'abonne est bannis ou non nous affichons le message
				socketOut.println(bttp.encoder("Le retour à bien été effectué pour le DVD "+numDoc+". "+msg));
			else
				socketOut.println(bttp.encoder("Le retour à bien été effectué pour le DVD "+numDoc+ " ."));
			this.getSocket().close();
		} catch (IOException e) {
			try {
				this.getSocket().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

}
