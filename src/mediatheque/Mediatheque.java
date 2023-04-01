package mediatheque;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mediatheque {
	private static List<Abonne> abos;
	private static List<ConcurrentDocument> docs;
	
	
	public static void setMediatheque(List<Abonne> abos1,List<ConcurrentDocument> docs1) {
		abos = abos1;
		docs = docs1;
	}
	
	public static Abonne getAbo(int i) {
		for(Abonne ab :abos) {
			if(ab.getId()==i) {
				return ab;
			}
		}
		return null;
	}

	public static ConcurrentDocument getDoc(int numDoc) {
		for(ConcurrentDocument doc :docs) {
			if(doc.numero()==numDoc) {
				return doc;
			}
		}
		return null;
	}
	
	public static String afficherDocs() {
		String s = "";
		for(ConcurrentDocument doc: docs) {
			s+=doc.toString()+"\n";
		}
		return s;
	}
	
	public static void sendEmail(String destinataire, int numero) {
		final String username = "testjava053@gmail.com";
		final String password = "zbaiykgsoqgdjxmn";
		String to = destinataire;

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
			message.setSubject("DVD "+ numero +" de nouveau disponible dans notre médiathèque ! ");
			message.setText("Vous pouvez maintenant réserver le DVD "+ numero +".");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
