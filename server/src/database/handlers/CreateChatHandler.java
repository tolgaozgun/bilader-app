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

public class CreateChatHandler extends ProcessHandler {

	private static final String PARTICIPANT_ONE_KEY = "participant_one";
	private static final String PARTICIPANT_TWO_KEY = "participant_two";
	private static final String[] KEYS = { PARTICIPANT_ONE_KEY };
	private static final String[] PRODUCT_CHECK_KEYS = { PARTICIPANT_ONE_KEY,
			PARTICIPANT_TWO_KEY };
	private final String DATABASE_TABLE_CHATS = "chats";

	public CreateChatHandler( Map< String, String[] > parameters ) {
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

		params.put( PARTICIPANT_TWO_KEY, params.get( USER_ID_KEY ) );

		// Checks if the current user is not verified.
		if ( !isVerified() ) {
			return ResultCode.NOT_VERIFIED;
		}

		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		checkParams = cloneMapWithKeys( PRODUCT_CHECK_KEYS, params );
		if ( adapter.doesExist( DATABASE_TABLE_CHATS, checkParams ) ) {
			return ResultCode.CHAT_ALREADY_EXIST;
		}

		return ResultCode.CHAT_CREATE_OK;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		String chatId;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		chatId = "";
		result = checkParams();

		if ( result.isSuccess() ) {
			chatId = createChatId();
			params.put( "last_message", "No previous messages!");
			params.put( "last_message_date", "0");
			adapter.create( DATABASE_TABLE_CHATS, params );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "chat_id", chatId );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}
	
	private String createChatId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapChatId;
		UUID chatId;
		DatabaseAdapter adapter;
		boolean doesExist;

		mapChatId = new HashMap< String, String >();
		chatId = UUID.randomUUID();
		mapChatId.put( "chat_id", chatId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE_CHATS, mapChatId );
		while ( doesExist ) {
			chatId = UUID.randomUUID();
			mapChatId.put( "chat_id", chatId.toString() );
		}
		params.put( "chat_id", chatId.toString() );
		
		return chatId.toString();

	}
}
