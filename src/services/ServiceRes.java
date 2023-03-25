package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import abonne.Abonne;
import bserveur.ServiceAbstract;
import bttp.bttp;
import document.Document;
import document.RestrictionException;
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
			socketOut.println(bttp.encoder("Quel document que vous voulez reserver ? Saisissez le numéro."));
			String numDoc =socketIn.readLine();
			if(Mediatheque.getDoc(Integer.parseInt(numDoc))==null) {
				socketOut.println(bttp.encoder("Ce "+Document.class.getSimpleName()+" n'existe pas."));
				this.getSocket().close();

			}
			try {
				Mediatheque.getDoc(Integer.parseInt(numDoc)).reservationPour(ab);
				socketOut.println(bttp.encoder("La réservation à bien été effectué pour le "+ Document.class.getSimpleName() +" "+numDoc));

			} catch ( RestrictionException e) {
				socketOut.println(bttp.encoder(e.getMessage()+"\nSi vous voulez recevoir une alerte quand le "+Document.class.getSimpleName()+" sera disponible ? Entrez oui."));
				/*String rep =socketIn.readLine();
				if(rep=="oui") {

				}*/
			}

		} catch (IOException e) {
			this.closeSocket();
			e.printStackTrace();
		}

	}
	public static void sendMail() {
		final String username = "testjava17@outlook.com";
		final String password = "Testjava*";
		String to = "louis.lenouvel@eut.u-paris.fr";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Sujet de l'email");
			message.setText("Contenu de l'email");
			Transport.send(message);
			System.out.println("Email envoyé avec succès");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}

