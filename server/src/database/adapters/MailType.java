package database.adapters;

/**
 * Enum class for mail types.
 * 
 * @author Tolga Özgün
 *
 */

public enum MailType {

	FORGOT_PASSWORD( "Forgot Password Code", "%code%" ),
	VERIFICATION( "Verification Code", "%code%" ), LOGIN( "Login Attempt", "" ),
	REGISTER( "Register Attempt", "" );

	private String subject;
	private String content;

	/**
	 * Constructor.
	 * 
	 * @param subject String value of subject.
	 * @param content String value of content.
	 */
	MailType( String subject, String content ) {
		this.subject = subject;
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

}
