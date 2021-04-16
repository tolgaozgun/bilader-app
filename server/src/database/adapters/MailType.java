package database.adapters;

public enum MailType {

	FORGOT_PASSWORD( "Forgot Password Code", "%code%" ),
	VERIFICATION( "Verification Code", "%code%" ), LOGIN( "Login Attempt", "" ),
	REGISTER( "Register Attempt", "" );

	private String subject;
	private String content;

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
