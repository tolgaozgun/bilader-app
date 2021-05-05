package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class FollowHandler extends ProcessHandler {

	private static final String TARGET_USER_ID_KEY = "following_id";
	private static final String CURRENT_USER_ID_KEY = "user_id";
	private static final String TIME_KEY = "time";
	private static final String NAME_KEY = "name";
	private static final String AVATAR_URL_KEY = "avatar_url";
	private static final String[] KEYS = { TARGET_USER_ID_KEY };
	private static final String[] FOLLOW_CHECK_KEYS = { CURRENT_USER_ID_KEY,
			TARGET_USER_ID_KEY };
	private final String DATABASE_TABLE_USERS = "users";
	private final String DATABASE_TABLE_FOLLOWERS = "followers";
	private final String DATABASE_TABLE_NOTIFICATIONS = "notifications";

	public FollowHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String userId;
		adapter = new DatabaseAdapter();

		if ( params == null || params.size() == 0 ) {
			return ResultCode.INVALID_REQUEST;
		}

		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS_ID, params );
		if ( !adapter.doesExist( DATABASE_TABLE_USERS, checkParams ) ) {
			return ResultCode.ACCOUNT_DOES_NOT_EXIST;
		}

		// Checks if the current user is not verified.
		if ( !isVerified() ) {
			return ResultCode.NOT_VERIFIED;
		}

		userId = params.get( USER_ID_KEY );
		params.put( CURRENT_USER_ID_KEY, userId );
		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		if ( userId.equals( params.get( TARGET_USER_ID_KEY ) ) ) {
			return ResultCode.CANNOT_FOLLOW_YOURSELF;
		}

		checkParams = cloneMapWithKeys( FOLLOW_CHECK_KEYS, params );
		if ( adapter.doesExist( DATABASE_TABLE_FOLLOWERS, checkParams ) ) {
			return ResultCode.UNFOLLOW_OK;
		}

		checkParams.clear();
		checkParams.put( "id", params.get( TARGET_USER_ID_KEY ) );
		if ( adapter.doesExist( DATABASE_TABLE_USERS, checkParams ) ) {
			return ResultCode.FOLLOW_OK;
		}

		return ResultCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		long timeNow;
		String userId;
		Map<Integer, Object[]> userResultMap;
		String targetId;
		String[] wanted;
		String content;
		String type;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		result = checkParams();
		timeNow = System.currentTimeMillis();
		
		wanted = new String[2];
		wanted[0] = NAME_KEY;
		wanted[1] = AVATAR_URL_KEY;

		if ( result.isSuccess() ) {
			userId = params.get( CURRENT_USER_ID_KEY );
			targetId = params.get( TARGET_USER_ID_KEY );
			if ( result == ResultCode.FOLLOW_OK ) {
				params.put( TIME_KEY, String.valueOf( timeNow ) );
				adapter.create( DATABASE_TABLE_FOLLOWERS, params );
				content = "followed you";
				type = "FOLLOW";
			} else {
				adapter.delete( DATABASE_TABLE_FOLLOWERS, params );
				content = "unfollowed you";
				type = "UNFOLLOW";
			}
			
			// Create notification for the target user.
			params.clear();
			params.put( USER_ID_KEY, userId );
			userResultMap = adapter.select( DATABASE_TABLE_USERS, wanted, params );
			params.clear();
			params.put( "user_id", targetId );
			params.put( "small_content", ( String ) userResultMap.get( 0 )[ 0 ]
					+ " " + content );
			params.put( "content", content );
			params.put( "extra_id", userId );
			params.put( "type", type );
			params.put( "title", ( String ) userResultMap.get( 0 )[ 0 ] );
			params.put( "image", ( String ) userResultMap.get( 0 )[ 1 ] );
			params.put( "time", String.valueOf( timeNow ) );
			adapter.create( DATABASE_TABLE_NOTIFICATIONS, params );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
