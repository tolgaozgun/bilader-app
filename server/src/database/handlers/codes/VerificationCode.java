package database.handlers.codes;

public enum VerificationCode {

	OK( "Verification success!" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	ALREADY_VERIFIED( "Your account is already verified!" ),
	WRONG_PASSWORD( "Wrong verification code!" ),
	ACCOUNT_DOES_NOT_EXIST("This email is not registered!"),
	NO_PENDING_REQUEST( "You don't have a pending verification request!" ),
	EXPIRED( "Your verification code has expired! Please try again." );

	private String message;

	VerificationCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
