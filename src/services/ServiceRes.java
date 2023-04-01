package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bserveur.ServiceAbstract;
import bttp.bttp;
import document.Document;
import document.IDocument;
import document.RestrictionException;
import mediatheque.Abonne;
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

			socketOut.println(bttp.encoder(Mediatheque.afficherDocs()+"\nQuel est votre numéro d'abonné ?"));
			String num =socketIn.readLine();

			Abonne ab=Mediatheque.getAbo(Integer.parseInt(num));
			if(ab==null) {
				socketOut.println(bttp.encoder("Le numéro d'"+Abonne.class.getSimpleName() +" est incorrect."));
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
			socketOut.println(bttp.encoder("Quel document que vous voulez reserver ? Saisissez le numéro."));
			String numDoc =socketIn.readLine();
			IDocument doc = Mediatheque.getDoc(Integer.parseInt(numDoc));
			if(doc==null) {
				socketOut.println(bttp.encoder("Ce "+Document.class.getSimpleName()+" n'existe pas."));
				this.getSocket().close();
			}
			try {
				doc.reservationPour(ab);
				socketOut.println(bttp.encoder("La réservation à bien été effectué pour le "+ IDocument.class.getSimpleName() +" "+numDoc));

			} catch ( RestrictionException e) {
				String msgException = e.getMessage();
				if(doc.emprunteur()!=null && doc.emprunteur().equals(ab) || doc.reserveur()!=null && doc.reserveur().equals(ab)) {
					socketOut.println(bttp.encoder(msgException));
				}
				else {
					socketOut.println(bttp.encoder(msgException+"\nSi vous voulez recevoir une alerte quand le "+ IDocument.class.getSimpleName()+" sera disponible ? Entrez 1. Sinon entrez 0."));
					String rep =socketIn.readLine();
					if(rep.equals("1")) {
						Mediatheque.getDoc(Integer.parseInt(numDoc)).setSendMailTrue();
						socketOut.println(bttp.encoder("Vous recevrez un message quand le DVD sera de nouveau disponible"));
					}
					else
						socketOut.println(bttp.encoder("Bien reçu, vous ne recevrez pas de message."));
				}
			}

		} catch (IOException e) {
			this.closeSocket();
			e.printStackTrace();
		}

	}

}

