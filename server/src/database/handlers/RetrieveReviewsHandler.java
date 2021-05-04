package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class RetrieveReviewsHandler extends ProcessHandler {

	private final static String DATABASE_TABLE = "reviews";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String RECEIVER_ID_KEY = "receiver_id";
	private final static String SENDER_ID_KEY = "sender_id";
	private final static String REVIEW_ID_KEY = "id";
	private final static String CONTENT_ID_KEY = "content";
	private final static String USER_NAME_KEY = "name";
	private final static String USER_AVATAR_KEY = "avatar_url";
	private final static String TIME_KEY = "time";
	private final static String[] KEYS = { RECEIVER_ID_KEY };
	private final static String[] VERIFY_REVIEWS_KEY = { RECEIVER_ID_KEY };

	public RetrieveReviewsHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
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

		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		checkParams = cloneMapWithKeys( VERIFY_REVIEWS_KEY, params );
		if ( !adapter.doesExist( DATABASE_TABLE, checkParams ) ) {
			return ResultCode.NO_REVIEWS_FOUND;
		}

		return ResultCode.RETRIEVE_REVIEWS_OK;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		Map< Integer, Object[] > usersMap;
		Map< Integer, Object[] > userDetailsMap;
		Map< String, String > checkParams;
		DatabaseAdapter adapter;
		ResultCode result;
		String[] wanted;
		String[] userWanted;
		String senderId;
		JSONObject json;
		JSONObject messagesJson;
		JSONObject tempJson;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 4 ];
		wanted[ 0 ] = SENDER_ID_KEY;
		wanted[ 1 ] = REVIEW_ID_KEY;
		wanted[ 2 ] = CONTENT_ID_KEY;
		wanted[ 3 ] = TIME_KEY;

		userWanted = new String[ 2 ];
		userWanted[ 0 ] = USER_NAME_KEY;
		userWanted[ 1 ] = USER_AVATAR_KEY;

		if ( result.isSuccess() ) {
			checkParams = cloneMapWithKeys( VERIFY_REVIEWS_KEY, params );
			usersMap = adapter.select( DATABASE_TABLE, wanted, checkParams );
			messagesJson = new JSONObject();
			for ( int i = 0; i < usersMap.size(); i++ ) {
				tempJson = new JSONObject();
				checkParams = new HashMap< String, String >();

				senderId = ( String ) usersMap.get( i )[ 0 ];
				checkParams.put( "id", senderId );
				userDetailsMap = adapter.select( DATABASE_TABLE_USERS,
						userWanted, checkParams );
				tempJson.put( "sender_name", userDetailsMap.get( 0 )[ 0 ] );
				tempJson.put( "sender_avatar", userDetailsMap.get( 0 )[ 1 ] );
				tempJson.put( SENDER_ID_KEY, senderId );
				tempJson.put( REVIEW_ID_KEY, usersMap.get( i )[ 1 ] );
				tempJson.put( CONTENT_ID_KEY, usersMap.get( i )[ 2 ] );
				tempJson.put( TIME_KEY, usersMap.get( i )[ 3 ] );
				messagesJson.put( String.valueOf( i ), tempJson );
			}
			json.put( "reviews", messagesJson );
		} else {
			json.put( "reviews", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
