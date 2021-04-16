package database.adapters;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailAdapter {

	private static final String MAIL_ADDRESS = "breakdowndevelopment@gmail.com";
	private static final String MAIL_PASSWORD = "BILADERapp123#";
	private static final String MAIL_HOST = "smtp.gmail.com";
	private static final String MAIL_PORT = "465";
	private static final String MAIL_SSL_ENABLE = "true";
	private static final String MAIL_AUTH = "true";

	public static void sendMail( MailType mailType, final String code,
			final String email ) {

		String content;
		content = mailType.getContent();

		if ( code != null ) {
			content = content.replaceAll( "%code%", code );
		}

		sendMail( email, mailType.getSubject(), content );

	}

	private static void sendMail( final String to, final String subject,
			final String content ) {

		// Get system properties
		Properties properties;
		properties = System.getProperties();

		// Setup mail server
		properties.put( "mail.smtp.host", MAIL_HOST );
		properties.put( "mail.smtp.port", MAIL_PORT );
		properties.put( "mail.smtp.ssl.enable", MAIL_SSL_ENABLE );
		properties.put( "mail.smtp.auth", MAIL_AUTH );

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance( properties,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication( MAIL_ADDRESS,
								MAIL_PASSWORD );

					}

				} );

		// Used to debug SMTP issues
		session.setDebug( true );

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage( session );

			// Set From: header field of the header.
			message.setFrom( new InternetAddress( MAIL_ADDRESS ) );

			// Set To: header field of the header.
			message.addRecipient( Message.RecipientType.TO,
					new InternetAddress( to ) );

			// Set Subject: header field
			message.setSubject( subject );

			// Now set the actual message
			message.setText( content );

			System.out.println( "sending..." );
			// Send message
			Transport.send( message );
			System.out.println( "Sent message successfully...." );
		} catch ( MessagingException mex ) {
		}
	}

}
