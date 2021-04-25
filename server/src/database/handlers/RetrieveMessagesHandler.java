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

public class RetrieveMessagesHandler extends ProcessHandler {

	private final static String DATABASE_TABLE = "messages";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String CHAT_ID_KEY = "chat_id";
	private final static String TIME_KEY = "time";
	private final static String SENDER_ID_KEY = "sender_id";
	private final static String RECEIVER_ID_KEY = "receiver_id";
	private final static String CONTENT_KEY = "content";
	private final static String[] KEYS = { CHAT_ID_KEY, TIME_KEY };
	private final static String[] VERIFY_KEYS = { CHAT_ID_KEY };

	public RetrieveMessagesHandler( Map< String, String[] > parameters ) {
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

		checkParams = cloneMapWithKeys( VERIFY_KEYS, params );
		if ( adapter.doesExist( DATABASE_TABLE, checkParams ) ) {
			return ResultCode.MESSAGES_OK;
		}

		return ResultCode.MESSAGES_FAIL;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		JSONObject messagesJson;
		JSONObject tempJson;
		Map< Integer, Object[] > usersMap;
		Map< String, String > checkParams;
		Map< String, String > compareParams;
		String[] wanted;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 4 ];
		wanted[ 0 ] = TIME_KEY;
		wanted[ 1 ] = SENDER_ID_KEY;
		wanted[ 2 ] = RECEIVER_ID_KEY;
		wanted[ 3 ] = CONTENT_KEY;

		if ( result.isSuccess() ) {
			checkParams = cloneMapWithKeys( VERIFY_KEYS, params );
			compareParams = new HashMap<String, String>();
			compareParams.put( TIME_KEY, params.get( TIME_KEY ) );
			usersMap = adapter.select( DATABASE_TABLE, wanted, checkParams,
					compareParams, CompareType.BIGGER );
			messagesJson = new JSONObject();
			for ( int i = 0; i < usersMap.size(); i++ ) {
				tempJson = new JSONObject();
				tempJson.put( TIME_KEY, usersMap.get( i )[ 0 ] );
				tempJson.put( SENDER_ID_KEY, usersMap.get( i )[ 1 ] );
				tempJson.put( RECEIVER_ID_KEY, usersMap.get( i )[ 2 ] );
				tempJson.put( CONTENT_KEY, usersMap.get( i )[ 3 ] );
				messagesJson.put( String.valueOf( i ), tempJson );
			}
			json.put( "messages", messagesJson );
		} else {
			json.put( "messages", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
