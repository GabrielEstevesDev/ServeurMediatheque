package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bserveur.ServiceAbstract;
import bttp.bttp;
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
			Mediatheque.getDoc(Integer.parseInt(numDoc)).retour();
			socketOut.println(bttp.encoder("Le retour  à bien été effectué pour le DVD "+numDoc));
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
