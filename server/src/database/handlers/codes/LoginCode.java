package database.handlers.codes;

public enum LoginCode {

	OK( "Logged in!" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	NOT_VERIFIED( "Please verify your account before using!" ),
	WRONG_PASSWORD( "Wrong password and email combination!" );

	private String message;

	LoginCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
