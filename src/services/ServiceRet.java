package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

import bserveur.ServiceAbstract;
import bttp.bttp;
import document.Document;
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
			socketOut.println(bttp.encoder("Quel document souhaitez-vous retourner ? Saisissez son numéro."));
			String numDoc =socketIn.readLine();
			socketOut.println(bttp.encoder("Dans quel état est votre"+Document.class.getSimpleName()+" ? Saisissez bon ou mauvais."));
			String etat =socketIn.readLine();
			String msg = "";
			if(etat.equals("mauvais")) {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).mauvaisEtat();
				Mediatheque.getDoc(Integer.parseInt(numDoc)).emprunteur().banniMois();
				msg="Attention ! Vous êtes bannis 2 mois ! Vous avez rendu le "+Document.class.getSimpleName()+" en mauvaise état.";
			}
			Mediatheque.getDoc(Integer.parseInt(numDoc)).setRetour();
			Date retour = Mediatheque.getDoc(Integer.parseInt(numDoc)).dateRetour();
			Date today = new Date();
			if(today.after(retour)) {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).emprunteur().banniMois();
				msg="Attention ! Vous êtes bannis 2 mois ! Vous avez rendu le "+Document.class.getSimpleName()+" avec un retard de plus de 2 semaines.";
			}
			Mediatheque.getDoc(Integer.parseInt(numDoc)).retour();
			socketOut.println(bttp.encoder("Le retour  à bien été effectué pour le DVD "+numDoc+" "+msg));
			this.getSocket().close();
		} catch (IOException e) {
			try {
				this.getSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

}
