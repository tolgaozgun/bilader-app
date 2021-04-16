package database.handlers.codes;

public enum AddVerificationCode {

	OK( "Verification code sent!" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	ALREADY_VERIFIED( "Your account is already verified!" ),
	ACCOUNT_DOES_NOT_EXIST("This email is not registered!");

	private String message;

	AddVerificationCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
