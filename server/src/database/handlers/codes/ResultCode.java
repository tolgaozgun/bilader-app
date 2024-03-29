package database.handlers.codes;

/**
 * ENUM class that holds all error codes for the back-end server.
 * 
 * @author Tolga Özgün
 *
 */

public enum ResultCode {

	ADD_VERIFICATION_OK( "Verification code sent!", true ),
	VERIFICATION_OK( "Verification success!", true ),
	FOLLOWERS_OK( "Success", true ), FOLLOWING_OK( "Data retrieved.", true ),
	LOGIN_OK( "Logged in!", true ), FORGOT_PASS_OK( "Request success!", true ),
	CHANGE_PASS_OK( "Password change success!", true ),
	ADD_PRODUCT_OK( "Product creation success", true ),
	REGISTER_OK( "Registered successfully", true ),
	MESSAGES_OK( "Chat found", true ), CHATS_OK( "Chats retrieved.", true ),
	PRODUCT_OK( "Products found", true ), REVIEW_OK( "Reviews loaded.", true ),
	EDIT_PICTURE_OK( "Successfully changed profile picture!", true ),
	ADD_REPORT_OK( "Report sent!", true ),
	WISHLIST_OK( "Wishlist loaded.", true ),
	ADD_WISHLIST_OK( "Product added to wishlist", true ),
	CATEGORIES_OK( "Retrieved categories", true ),
	PRODUCT_COUNT_OK( "Retrieved product count", true ),
	REMOVE_OK( "Product removed from wishlist", true ),
	FOLLOW_OK( "Successfully followed!", true ),
	ADD_REVIEW_OK( "Review successfully sent!", true ),
	EDIT_PRODUCT_OK( "Product edited successfully", true ),
	DELETE_PRODUCT_OK( "Product deleted successfully", true ),
	LOGOUT_OK( "Logout successful!", true ),
	CHAT_CREATE_OK( "Chat created successfully!", true ),
	USERS_OK( "User found!", true ),
	UNFOLLOW_OK( "User unfollowed successfully!", true ),
	NOTIFICATION_OK( "Notifications found!", true ),
	RETRIEVE_REVIEWS_OK( "Reviews found!", true ),
	CHAT_DOES_NOT_EXIST( "This chat does not exist!", false ),
	CHATS_DO_NOT_EXIST( "Chats do not exist!", false ),
	CATEGORIES_DO_NOT_EXIST( "Categories do not exist!", false ),
	PRODUCT_DOES_NOT_EXIST( "Product does not exist!", false ),
	CANNOT_FOLLOW_YOURSELF( "You cannot follow yourself", false ),
	MESSAGES_FAIL( "Chat not found!", false ),
	INVALID_REQUEST( "Invalid request! Please contact developers.", false ),
	ALREADY_VERIFIED( "Your account is already verified!", false ),
	ALREADY_WISHLISTED( "This product is already in your wishlist", false ),
	ALREADY_FOLLOWED( "You are already following this user!", false ),
	CHECK_NOT_WISHLISTED( "This product is not wishlisted.", true ),
	CHECK_ALREADY_WISHLISTED( "This product is already in your wishlist",
			true ),
	CHAT_ALREADY_EXIST( "This chat already exists!", false ),
	CHECK_ALREADY_FOLLOWING( "You are already following this user!", true ),
	CHECK_NOT_FOLLOWING( "You are not following this user!", true ),

	ACCOUNT_DOES_NOT_EXIST( "This email is not registered!", false ),
	NOT_VERIFIED( "Your account is not verified!", false ),
	WRONG_VERIFICATION_CODE( "Wrong verification code!", false ),
	NO_PENDING_REQUEST(
			"There isn't a pending request for this account.", false ),
	NO_REVIEWS_FOUND( "No reviews found!", false ),
	VERIFICATION_EXPIRED(
			"Your verification code has expired! Please try again.", false ),
	NONE_FOUND( "No data found!", false ),
	WRONG_PASSWORD( "Wrong password and email combination!", false ),
	ALREADY_REGISTERED( "This email has already registered!", false ),
	REMOVE_NOT_WISHLISTED( "This product is not wishlisted!", false ),
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
