package database.adapters;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * An adapter class that maintains the Mail Server connection with this JSP
 * Server.
 * 
 * @author Tolga Özgün
 *
 */

public class MailAdapter {

	private static final String MAIL_ADDRESS = "breakdowndevelopment@gmail.com";
	private static final String MAIL_PASSWORD = "BILADERapp123#";
	private static final String MAIL_HOST = "smtp.gmail.com";
	private static final String MAIL_PORT = "465";
	private static final String MAIL_SSL_ENABLE = "true";
	private static final String MAIL_AUTH = "true";

	/**
	 * Sends a mail to the provided email address without a given code.
	 * Reformats the requests to match
	 * {@link #sendMail(MailType, String, String)}
	 * 
	 * @param mailType MailType enum value of mail type.
	 * @param email    String value of recipient email address
	 */
	public static void sendMail( MailType mailType, final String email ) {

		sendMail( mailType, null, email );

	}

	/**
	 * Sends a mail to the provided email address without a given code.
	 * Reformats the requests to match {@link #sendMail(String, String, String)}
	 * 
	 * @param mailType MailType enum value of mail type.
	 * @param code     String value of code.
	 * @param email    String value of recipient email address
	 */
	public static void sendMail( MailType mailType, final String code,
			final String email ) {

		String content;
		content = mailType.getContent();

		if ( code != null ) {
			content = content.replaceAll( "%code%", code );
		}

		sendMail( email, mailType.getSubject(), content );

	}

	/**
	 * Sends a mail to an email address with a subject and content using
	 * credentials.
	 * 
	 * @param to      String value of recipient email address.
	 * @param subject String value of subject.
	 * @param content String value of content.
	 */
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
		Session session = Session.getInstance( properties, new Authenticator() {

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

			// Send message
			Transport.send( message );
		} catch ( MessagingException mex ) {
			mex.printStackTrace();
		}
	}

}
