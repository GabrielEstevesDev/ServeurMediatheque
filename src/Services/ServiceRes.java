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

public class ServiceRes extends ServiceAbstract {

	public ServiceRes(Socket socketCotéServeur) {
		super(socketCotéServeur);
		
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
			
			PrintWriter socketOut = new PrintWriter (this.getSocket().getOutputStream ( ), true);
			
			socketOut.println(bttp.encoder(requetes.getAllDocuments()+"\nVeuillez saisir votre numéro d'abonné"));
			String num =socketIn.readLine();
		
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {
				socketOut.println(bttp.encoder("Le numéro d'abonné est incorrect"));
				this.getSocket().close();
			}
			socketOut.println(bttp.encoder("Saisissez le numéro de document que vous voulez reserver ?"));
			String numDoc =socketIn.readLine();
			try {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).reservationPour(ab);
				socketOut.println(bttp.encoder("La réservation à bien été effectué pour le DVD "+numDoc));
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
