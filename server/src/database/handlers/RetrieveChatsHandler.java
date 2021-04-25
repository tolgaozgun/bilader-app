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

public class RetrieveChatsHandler extends ProcessHandler {

	private final static String DATABASE_TABLE = "chats";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String CHAT_ID_KEY = "chat_id";
	private final static String PARTICIPANT_ONE_KEY = "participant_one";
	private final static String PARTICIPANT_TWO_KEY = "participant_two";
	private final static String USER_NAME_KEY = "name";
	private final static String USER_AVATAR_KEY = "avatar_url";
	private final static String DATE_KEY = "last_message_date";
	private final static String LAST_MESSAGE_KEY = "last_message";
	private final static String[] OPTIONAL_KEYS = { PARTICIPANT_ONE_KEY };
	private final static String[] KEYS = {};

	public RetrieveChatsHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS,
				OPTIONAL_KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		Map< String, String > anotherParams;
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
		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}
		params.put( USER_ID_KEY, userId );

		checkParams = new HashMap< String, String >();

		if ( params.containsKey( PARTICIPANT_ONE_KEY ) ) {

			checkParams.put( PARTICIPANT_ONE_KEY,
					params.get( PARTICIPANT_ONE_KEY ) );
			checkParams.put( PARTICIPANT_TWO_KEY, userId );

			anotherParams = new HashMap< String, String >();

			anotherParams.put( PARTICIPANT_ONE_KEY, userId );
			anotherParams.put( PARTICIPANT_TWO_KEY,
					params.get( PARTICIPANT_ONE_KEY ) );

			if ( adapter.doesExist( DATABASE_TABLE, checkParams, "AND", "OR",
					anotherParams, "AND" ) ) {
				return ResultCode.CHATS_OK;
			}
		} else {
			checkParams.put( PARTICIPANT_ONE_KEY, userId );
			checkParams.put( PARTICIPANT_TWO_KEY, userId );
			if ( adapter.doesExist( DATABASE_TABLE, checkParams, "OR" ) ) {
				return ResultCode.CHATS_OK;
			}
		}

		return ResultCode.CHATS_DO_NOT_EXIST;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		Map< Integer, Object[] > usersMap;
		Map< Integer, Object[] > userDetailsMap;
		Map< String, String > checkParams;
		Map< String, String > anotherParams;
		DatabaseAdapter adapter;
		ResultCode result;
		String[] wanted;
		String[] userWanted;
		String currentUserId;
		String otherUserId;
		JSONObject json;
		JSONObject messagesJson;
		JSONObject tempJson;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 5 ];
		wanted[ 0 ] = PARTICIPANT_ONE_KEY;
		wanted[ 1 ] = PARTICIPANT_TWO_KEY;
		wanted[ 2 ] = DATE_KEY;
		wanted[ 3 ] = LAST_MESSAGE_KEY;
		wanted[ 4 ] = CHAT_ID_KEY;

		userWanted = new String[ 2 ];
		userWanted[ 0 ] = USER_NAME_KEY;
		userWanted[ 1 ] = USER_AVATAR_KEY;

		if ( result.isSuccess() ) {
			currentUserId = params.get( USER_ID_KEY );
			checkParams = new HashMap< String, String >();

			if ( params.containsKey( PARTICIPANT_ONE_KEY ) ) {

				checkParams.put( PARTICIPANT_ONE_KEY, currentUserId );
				checkParams.put( PARTICIPANT_TWO_KEY,
						params.get( PARTICIPANT_ONE_KEY ) );
				anotherParams = new HashMap< String, String >();

				anotherParams.put( PARTICIPANT_ONE_KEY,
						params.get( PARTICIPANT_ONE_KEY ) );
				anotherParams.put( PARTICIPANT_TWO_KEY, currentUserId );
				
				usersMap = adapter.select( DATABASE_TABLE, wanted, checkParams, "AND",
						"OR", anotherParams, "AND" );

			} else {
				checkParams.put( PARTICIPANT_ONE_KEY, currentUserId );
				checkParams.put( PARTICIPANT_TWO_KEY, currentUserId );
				usersMap = adapter.select( DATABASE_TABLE, wanted, checkParams,
						"OR" );
			}
			messagesJson = new JSONObject();
			for ( int i = 0; i < usersMap.size(); i++ ) {
				tempJson = new JSONObject();
				if ( !usersMap.get( i )[ 0 ].equals( currentUserId ) ) {
					otherUserId = ( String ) usersMap.get( i )[ 0 ];
				} else {
					otherUserId = ( String ) usersMap.get( i )[ 1 ];
				}
				checkParams = new HashMap< String, String >();
				checkParams.put( USER_ID_KEY, otherUserId );
				userDetailsMap = adapter.select( DATABASE_TABLE_USERS,
						userWanted, checkParams );
				tempJson.put( "participant_name",
						userDetailsMap.get( 0 )[ 0 ] );
				tempJson.put( "participant_avatar",
						userDetailsMap.get( 0 )[ 1 ] );
				tempJson.put( "participant_id", otherUserId );
				tempJson.put( DATE_KEY, usersMap.get( i )[ 2 ] );
				tempJson.put( LAST_MESSAGE_KEY, usersMap.get( i )[ 3 ] );
				tempJson.put( CHAT_ID_KEY, usersMap.get( i )[ 4 ] );
				messagesJson.put( String.valueOf( i ), tempJson );
			}
			json.put( "chats", messagesJson );
		} else {
			json.put( "chats", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
