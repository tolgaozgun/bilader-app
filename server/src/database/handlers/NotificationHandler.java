package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.CompareType;
import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class NotificationHandler extends ProcessHandler {

	private final static String DATABASE_TABLE = "notifications";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String CONTENT_KEY = "content";
	private final static String IMAGE_KEY = "image";
	private final static String TIME_KEY = "time";
	private final static String TITLE_KEY = "title";
	private final static String TYPE_KEY = "type";
	private final static String SMALL_CONTENT_KEY = "small_content";
	private final static String EXTRA_ID_KEY = "extra_id";
	private final static String NOTIFICATION_ID_KEY = "notification_id";
	private final static String NOTIFICATION_USER_ID_KEY = "user_id";

	private final static String[] KEYS = { TIME_KEY };
	private final static String[] VERIFICATION_KEYS = { NOTIFICATION_USER_ID_KEY };

	public NotificationHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, true ) );
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

		userId = params.get( USER_ID_KEY );
		params.put( NOTIFICATION_USER_ID_KEY, userId );
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

		checkParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
		if ( adapter.doesExist( DATABASE_TABLE, checkParams ) ) {
			return ResultCode.NOTIFICATION_OK;
		}

		return ResultCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		JSONObject messagesJson;
		JSONObject tempJson;
		DatabaseAdapter adapter;
		ResultCode result;
		Map< Integer, Object[] > usersMap;
		Map< String, String > checkParams;
		Map< String, String > compareParams;
		String[] wanted;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 8 ];
		wanted[ 0 ] = CONTENT_KEY;
		wanted[ 1 ] = IMAGE_KEY;
		wanted[ 2 ] = TIME_KEY;
		wanted[ 3 ] = NOTIFICATION_ID_KEY;
		wanted[ 4 ] = TITLE_KEY;
		wanted[ 5 ] = SMALL_CONTENT_KEY;
		wanted[ 6 ] = EXTRA_ID_KEY;
		wanted[ 7 ] = TYPE_KEY;

		if ( result.isSuccess() ) {
			checkParams = new HashMap< String, String >();
			checkParams.put( NOTIFICATION_USER_ID_KEY, params.get( NOTIFICATION_USER_ID_KEY ) );
			compareParams = new HashMap< String, String >();
			compareParams.put( TIME_KEY, params.get( TIME_KEY ) );
			usersMap = adapter.select( DATABASE_TABLE, wanted, checkParams,
					compareParams, CompareType.BIGGER );
			messagesJson = new JSONObject();
			for ( int i = 0; i < usersMap.size(); i++ ) {
				tempJson = new JSONObject();
				tempJson.put( CONTENT_KEY, usersMap.get( i )[ 0 ] );
				tempJson.put( IMAGE_KEY, usersMap.get( i )[ 1 ] );
				tempJson.put( TIME_KEY, usersMap.get( i )[ 2 ] );
				tempJson.put( NOTIFICATION_ID_KEY, usersMap.get( i )[ 3 ] );
				tempJson.put( TITLE_KEY, usersMap.get( i )[ 4 ] );
				tempJson.put( SMALL_CONTENT_KEY, usersMap.get( i )[ 5 ] );
				tempJson.put( EXTRA_ID_KEY, usersMap.get( i )[ 6 ] );
				tempJson.put( TYPE_KEY, usersMap.get( i )[ 7 ] );
				messagesJson.put( String.valueOf( i ), tempJson );
			}
			json.put( "notifications", messagesJson );
		} else {
			json.put( "notifications", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
