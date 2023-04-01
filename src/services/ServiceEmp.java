package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import bserveur.ServiceAbstract;
import bttp.bttp;
import document.Document;
import document.RestrictionException;
import mediatheque.Abonne;
import mediatheque.ConcurrentDocument;
import mediatheque.Mediatheque;

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
			String num =socketIn.readLine();
		
			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {//si ab == null
				socketOut.println(bttp.encoder("Le numéro d'"+Abonne.class.getSimpleName() +"est incorrect"));
				this.getSocket().close(); //on arrête le service
			}
			Date today = new Date();
			if(ab.getDateBan()!=null && ab.getDateBan().after(today)) {
				Date date = ab.getDateBan();
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				socketOut.println(bttp.encoder("Vous êtes toujours bannis jusqu'au "+calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+(calendar.get(GregorianCalendar.MONTH)+1)+"/"+calendar.get(GregorianCalendar.YEAR)+"."));
				this.getSocket().close();
			}
			//Demande du numéro de document
			socketOut.println(bttp.encoder("Quel document vous voulez emprunté ? Saisissez son numéro."));
			String numDoc =socketIn.readLine();
			ConcurrentDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			if(doc==null) {//si doc null
				socketOut.println(bttp.encoder("Ce "+Document.class.getSimpleName()+"n'existe pas"));
				this.getSocket().close();//on arrête le service
			}
			try {
				doc.empruntPar(ab);
				Date datemprunt=doc.dateRetour();
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(datemprunt);
				socketOut.println(bttp.encoder("L'emprunt à bien été effectué pour le "+ Document.class.getSimpleName() +" "+numDoc+"\nN'oubliez pas de le rendre avant le : "+calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+(calendar.get(GregorianCalendar.MONTH)+1)+"/"+calendar.get(GregorianCalendar.YEAR)+" à "+datemprunt.getHours()+":"+datemprunt.getMinutes()));
				this.getSocket().close();
			} catch ( RestrictionException e) {
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
