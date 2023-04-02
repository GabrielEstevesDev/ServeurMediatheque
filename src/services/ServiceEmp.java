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
import mediatheque.Abonne;
import mediatheque.AbonneBanisException;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;

public class ServiceEmp extends ServiceAbstract {

	public ServiceEmp(Socket socketCotéServeur) {
		super(socketCotéServeur);
		
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
			
			PrintWriter socketOut = new PrintWriter (this.getSocket().getOutputStream ( ), true);
			
			//Demande du numéro d'abonné
			socketOut.println(bttp.encoder(Mediatheque.afficherDocs()+"\nQuel est votre numéro d'abonné ?"));
			String num =bttp.decoder(socketIn.readLine());
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {//si ab == null
				socketOut.println(bttp.encoder("Le numéro d'abonné est incorrect"));
				this.getSocket().close(); //on arrête le service
			}
			try { //verifie si l'abonne n'est pas déjà bannis
				ab.estBannis();
			} catch (AbonneBanisException e1) {
				socketOut.println(bttp.encoder(e1.getMessage()));
				this.getSocket().close(); //il est toujours bannis alors nous le lui indiquons et fermons la communication
			}
			//Demande du numéro de document
			socketOut.println(bttp.encoder("Quel document vous voulez emprunté ? Saisissez son numéro."));
			String numDoc =bttp.decoder(socketIn.readLine());
			IDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			if(doc==null) {//si doc null
				socketOut.println(bttp.encoder("Ce "+doc.getClass().getSimpleName()+"n'existe pas"));
				this.getSocket().close();//on arrête le service
			}
			try {
				doc.empruntPar(ab);//emprunt du document
				Date datemprunt=doc.dateRetouremprunt(); //récupération de la date max de retour
				GregorianCalendar calendar = new GregorianCalendar(); 
				calendar.setTime(datemprunt); //pour que l'utilisateur sache quand il doit rendre le document
				socketOut.println(bttp.encoder("L'emprunt à bien été effectué pour le "+ doc.getClass().getSimpleName() +" "+numDoc+"\nN'oubliez pas de le rendre avant le : "+calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+(calendar.get(GregorianCalendar.MONTH)+1)+"/"+calendar.get(GregorianCalendar.YEAR)+" à "+datemprunt.getHours()+":"+datemprunt.getMinutes()));
				this.getSocket().close();
			} catch ( RestrictionException e) {
				//affichage du message d'erreur lors de l'emprunt
				socketOut.println(bttp.encoder(e.getMessage()));
				this.getSocket().close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				this.getSocket().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
