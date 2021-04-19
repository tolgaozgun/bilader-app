package database.handlers.codes;

public enum FollowersCode {

	OK( "Success" ),
	INVALID_REQUEST( "Invalid request! Please contact developers." ),
	INVALID_SESSION( "Invalid session, please log in again!" ),
	NOT_VERIFIED( "Please verify your account before using!" ),
	NONE_FOUND( "No data found!" );

	private String message;

	FollowersCode( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
