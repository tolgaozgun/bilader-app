package database.handlers.codes;

public enum ChangePasswordCode {

	OK( "Password change success!" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	NOT_VERIFIED( "Your account is not verified!" ),
	WRONG_PASSWORD( "Wrong verification code!" ),
	ACCOUNT_DOES_NOT_EXIST( "This email is not registered in the system!" ),
	EXPIRED("Your verification code has expired! Please try again.");

	private String message;

	ChangePasswordCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


}
