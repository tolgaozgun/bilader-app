package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class SendMessageHandler extends ProcessHandler {

	private final static String DATABASE_TABLE_MESSAGES = "messages";
	private final static String DATABASE_TABLE_CHATS = "chats";
	private final static String DATABASE_TABLE_NOTIFICATIONS = "notifications";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String CHAT_ID_KEY = "chat_id";
	private final static String CONTENT_KEY = "content";
	private final static String SENDER_ID_KEY = "sender_id";
	private final static String RECEIVER_ID_KEY = "receiver_id";
	private final static String TIME_KEY = "time";
	private final static String PARTICIPANT_ONE_KEY = "participant_one";
	private final static String PARTICIPANT_TWO_KEY = "participant_two";
	private final static String LAST_MESSAGE_KEY = "last_message";
	private final static String LAST_MESSAGE_DATE_KEY = "last_message_date";
	private final static String NAME_KEY = "name";
	private final static String AVATAR_URL_KEY = "avatar_url";
	private final static String[] KEYS = { CHAT_ID_KEY, CONTENT_KEY };
	private final static String[] VERIFY_KEYS = { CHAT_ID_KEY };

	public SendMessageHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		Map< String, String > additionalParams;
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
		params.put( SENDER_ID_KEY, userId );
		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		checkParams = cloneMapWithKeys( VERIFY_KEYS, params );

		additionalParams = new HashMap< String, String >();

		additionalParams.put( PARTICIPANT_ONE_KEY, userId );
		additionalParams.put( PARTICIPANT_TWO_KEY, userId );
		if ( adapter.doesExist( DATABASE_TABLE_CHATS, checkParams, "AND",
				additionalParams, "OR" ) ) {
			return ResultCode.MESSAGES_OK;
		}

		return ResultCode.MESSAGES_FAIL;
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		String[] wanted;
		String[] userWanted;
		ResultCode result;
		Map< String, String > chatIdParam;
		Map< String, Object > updateList;
		Map< Integer, Object[] > resultMap;
		Map< Integer, Object[] > userResultMap;
		String currentUserId;
		String otherUserId;
		String messageId;
		String content;
		long currentTime;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		currentTime = System.currentTimeMillis();
		messageId = "";
		updateList = new HashMap< String, Object >();
		wanted = new String[ 2 ];
		wanted[ 0 ] = PARTICIPANT_ONE_KEY;
		wanted[ 1 ] = PARTICIPANT_TWO_KEY;
		currentUserId = params.get( SENDER_ID_KEY );
		params.remove( USER_ID_KEY );

		userWanted = new String[ 2 ];
		userWanted[ 0 ] = NAME_KEY;
		userWanted[ 1 ] = AVATAR_URL_KEY;

		if ( result.isSuccess() ) {
			content = params.get( CONTENT_KEY );
			messageId = createMessageId();
			chatIdParam = cloneMapWithKeys( VERIFY_KEYS, params );
			resultMap = adapter.select( DATABASE_TABLE_CHATS, wanted,
					chatIdParam );
			if ( resultMap.get( 0 )[ 0 ].equals( currentUserId ) ) {
				otherUserId = ( String ) resultMap.get( 0 )[ 1 ];
			} else {
				otherUserId = ( String ) resultMap.get( 0 )[ 0 ];
			}
			params.put( RECEIVER_ID_KEY, otherUserId );
			params.put( TIME_KEY, String.valueOf( currentTime ) );

			updateList.put( LAST_MESSAGE_KEY, params.get( CONTENT_KEY ) );
			updateList.put( LAST_MESSAGE_DATE_KEY,
					String.valueOf( currentTime ) );

			// Update last message on chats table
			adapter.update( DATABASE_TABLE_CHATS, chatIdParam, updateList );

			// Update messages table.
			adapter.create( DATABASE_TABLE_MESSAGES, params );

			params.clear();
			params.put( USER_ID_KEY, currentUserId );
			json.put( "sender_id", currentUserId );
			userResultMap = adapter.select( DATABASE_TABLE_USERS, userWanted,
					params );
			json.put( "sender_name", userResultMap.get( 0 )[ 0 ] );
			json.put( "sender_avatar_url", userResultMap.get( 0 )[ 1 ] );

			params.clear();
			params.put( "user_id", otherUserId );
			params.put( "content", content );
			params.put( "image", ( String ) userResultMap.get( 0 )[ 1 ] );
			params.put( "time", String.valueOf( currentTime ) );
			adapter.create( DATABASE_TABLE_NOTIFICATIONS, params );

		}

		json.put( "message_id", messageId );
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

	private String createMessageId()
			throws ClassNotFoundException, SQLException {
		Map< String, String > mapMessageId;
		UUID messageId;
		DatabaseAdapter adapter;
		boolean doesExist;

		mapMessageId = new HashMap< String, String >();
		messageId = UUID.randomUUID();
		mapMessageId.put( "message_id", messageId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE_MESSAGES, mapMessageId );
		while ( doesExist ) {
			messageId = UUID.randomUUID();
			mapMessageId.put( "message_id", messageId.toString() );
		}
		params.put( "message_id", messageId.toString() );
		return messageId.toString();
	}

}