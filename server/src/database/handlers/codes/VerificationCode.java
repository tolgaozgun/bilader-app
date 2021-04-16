package database.handlers.codes;

public enum VerificationCode {

	OK( "Verification success!" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	ALREADY_VERIFIED( "Your account is already verified!" ),
	WRONG_PASSWORD( "Wrong verification code!" ),
	EXPIRED("Your verification code has expired! Please try again.");

	private String message;

	VerificationCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
