package testmain;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class main {
	public static void main(String[] args) {
		final String username = "testjava053@gmail.com";
		final String password = "zbaiykgsoqgdjxmn";
		String to = "louis.lenouvel@etu.u-paris.fr";

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
			message.setSubject("DVD de nouveau disponible dans notre médiathèque");
			message.setText("Vous pouvez maintenant réserver le DVD.");
			Transport.send(message);
			System.out.println("Email envoyé avec succès");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
