package database.handlers.codes;

public enum ResultCode {

	ADD_VERIFICATION_OK( "Verification code sent!", true ),
	VERIFICATION_OK( "Verification success!", true ),
	FOLLOWERS_OK( "Success", true ), FOLLOWING_OK( "Data retrieved.", true ),
	LOGIN_OK( "Logged in!", true ), FORGOT_PASS_OK( "Request success!", true ),
	CHANGE_PASS_OK( "Password change success!", true ),
	ADD_PRODUCT_OK( "Product creation success", true ),
	REGISTER_OK( "Registered successfully", true ),
	MESSAGES_OK( "Chat found", true ), CHATS_OK( "Chats retrieved.", true ),
	PRODUCT_OK( "Products found", true ), 
	NOTIFICATION_OK( "Notifications found!", true ),
	CHAT_DOES_NOT_EXIST( "This chat does not exist!", false ),
	CHATS_DO_NOT_EXIST( "Chats do not exist!", false ),
	MESSAGES_FAIL( "Chat not found!", false ),
	INVALID_REQUEST( "Invalid request! Please contact developers.", false ),
	ALREADY_VERIFIED( "Your account is already verified!", false ),
	ACCOUNT_DOES_NOT_EXIST( "This email is not registered!", false ),
	NOT_VERIFIED( "Your account is not verified!", false ),
	WRONG_VERIFICATION_CODE( "Wrong verification code!", false ),
	NO_PENDING_REQUEST( "There isn't a pending request for this account.",
			false ),
	VERIFICATION_EXPIRED(
			"Your verification code has expired! Please try again.", false ),
	NONE_FOUND( "No data found!", false ),
	WRONG_PASSWORD( "Wrong password and email combination!", false ),
	ALREADY_REGISTERED( "This email has already registered!", false ),
	NOT_EDU_MAIL( "You need to use your Bilkent mail to register!", false ),
	INVALID_SESSION( "Invalid session, please log in again!", false ),
	NO_DATA_FOUND( "No data found!", false );

	private String message;
	private boolean success;

	ResultCode( String message, boolean success ) {
		this.message = message;
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}
}
