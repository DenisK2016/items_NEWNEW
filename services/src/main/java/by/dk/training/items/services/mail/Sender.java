package by.dk.training.items.services.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {

	private String username;
	private String password;
	private Properties props;

	public Sender(String username, String password) {
		this.username = username;
		this.password = password;
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	public void send(String subject, String text, String toEmail) {
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			// from
			message.setFrom(new InternetAddress(username));
			// to
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			// title
			message.setSubject(subject);
			// text
			message.setText(text);
			// send message
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
