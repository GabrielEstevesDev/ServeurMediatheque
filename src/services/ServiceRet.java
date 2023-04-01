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
				socketOut.println(bttp.encoder("Ce Document n'existe pas."));
				//on arrête le service
				this.getSocket().close();
			}
			// demande de l'état du document
			socketOut.println(bttp.encoder("Dans quel état est votre "+doc.getClass().getSimpleName()+" ? Saisissez 1 si il est en bon état. 0 si il est en mauvais état."));
			String etat =socketIn.readLine();
			String msg = "";
			if(etat.equals("0")) {//si mauvais
				Mediatheque.getDoc(Integer.parseInt(numDoc)).setEtat(false); //on met l'état du doc en mauvais état
				Mediatheque.getDoc(Integer.parseInt(numDoc)).emprunteur().banniMois(); // on bannit l'abonné pendant 2 mois
				msg="Attention ! Vous êtes bannis 2 mois ! Vous avez rendu le "+doc.getClass().getSimpleName()+" en mauvaise état.";
			}
			Mediatheque.getDoc(Integer.parseInt(numDoc)).setRetour();
			Date retour = Mediatheque.getDoc(Integer.parseInt(numDoc)).dateRetour();
			Date today = new Date();
			if(today.after(retour)) {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).emprunteur().banniMois();
				msg="Attention ! Vous êtes bannis 2 mois ! Vous avez rendu le "+doc.getClass().getSimpleName()+" avec un retard de plus de 2 semaines.";
			}
			Mediatheque.getDoc(Integer.parseInt(numDoc)).retour();
			//fin service
			socketOut.println(bttp.encoder("Le retour à bien été effectué pour le DVD "+numDoc+" "+msg));
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
