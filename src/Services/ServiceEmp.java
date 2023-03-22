package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import Mediatheque.Mediatheque;
import bserveur.ServiceAbstract;
import bttp.bttp;
import db.requetes;
import document.Abonne;
import document.RestrictionException;

public class ServiceEmp extends ServiceAbstract {

	public ServiceEmp(Socket socketCotéServeur) {
		super(socketCotéServeur);
		
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
			
			PrintWriter socketOut = new PrintWriter (this.getSocket().getOutputStream ( ), true);
			
			socketOut.println(bttp.encoder(Mediatheque.afficherDocs()+"\nVeuillez saisir votre numéro d'abonné"));
			String num =socketIn.readLine();
		
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {
				socketOut.println(bttp.encoder("Le numéro d'abonné est incorrect"));
				this.getSocket().close();
			}
			socketOut.println(bttp.encoder("Saisissez le numéro de document que vous voulez emprunté ?"));
			String numDoc =socketIn.readLine();
			try {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).empruntPar(ab);
				socketOut.println(bttp.encoder("L'emprunt à bien été effectué pour le DVD "+numDoc));
				this.getSocket().close();
			} catch (RestrictionException e) {
				socketOut.println(bttp.encoder(e.getMessage()));
				this.getSocket().close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.getSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
