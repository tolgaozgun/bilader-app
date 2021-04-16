package database.handlers.codes;

public enum RegisterCode {

	OK( "Registered successfully" ),
	ALREADY_REGISTERED( "This email has already registered!" ), 
	NOT_EDU_MAIL("You need to use your Bilkent mail to register!"),
	INVALID_REQUEST("Invalid request! Please contact developers.");

	private String message;

	RegisterCode( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
