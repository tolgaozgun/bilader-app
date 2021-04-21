package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.PasscodeAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class LoginHandler extends ProcessHandler {

	private final static String EMAIL_KEY = "email";
	private final static String[] KEYS = { EMAIL_KEY, "password" };
	private final static String[] USER_CHECK_KEYS = { EMAIL_KEY };
	private final String DATABASE_TABLE_USERS = "users";
	private final String DATABASE_TABLE_SESSIONS = "sessions";
	private final String SESSION_TOKEN_KEY = "session_token";
	private final String PASSWORD_KEY = "password";
	private final String PASSWORD_HASH_KEY = "password_hash";
	private final String PASSWORD_SALT_KEY = "password_salt";

	public LoginHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, false ) );
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
		checkParams = cloneMapWithKeys( USER_CHECK_KEYS, params );
		if ( !adapter.doesExist( DATABASE_TABLE_USERS, checkParams ) ) {
			return ResultCode.ACCOUNT_DOES_NOT_EXIST;
		}

		if ( !isVerified() ) {
			return ResultCode.NOT_VERIFIED;
		}

		if ( adapter.doesExist( DATABASE_TABLE_USERS, params ) ) {
			return ResultCode.LOGIN_OK;
		}

		return ResultCode.WRONG_PASSWORD;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		String token;
		String[] requestedArray;
		String userId;
		Map< Integer, Object[] > returnArray;
		Map< String, String > tokenSubmitParams;
		Map< String, Object > updateMap;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
				DATABASE_TABLE_USERS, PASSWORD_KEY, PASSWORD_HASH_KEY,
				PASSWORD_SALT_KEY, EMAIL_KEY );
		result = checkParams();
		json.put( "verified-error", false );

		if ( result.isSuccess() ) {

			requestedArray = new String[ 1 ];
			requestedArray[ 0 ] = USER_ID_KEY;

			returnArray = adapter.select( DATABASE_TABLE_USERS, requestedArray,
					params );

			userId = ( String ) returnArray.get( 0 )[ 0 ];
			json.put( USER_ID_KEY, userId );

			tokenSubmitParams = new HashMap< String, String >();
			token = UUID.randomUUID().toString();
			tokenSubmitParams.put( USER_ID_KEY, userId );

			// If a session with the provided user id exists in the database,
			// update it. If not, create new.
			if ( adapter.doesExist( DATABASE_TABLE_SESSIONS,
					tokenSubmitParams ) ) {
				updateMap = new HashMap< String, Object >();
				updateMap.put( SESSION_TOKEN_KEY, token );
				adapter.update( DATABASE_TABLE_SESSIONS, tokenSubmitParams,
						updateMap );
			} else {
				tokenSubmitParams.put( SESSION_TOKEN_KEY, token );
				adapter.create( DATABASE_TABLE_SESSIONS, tokenSubmitParams );
			}

		} else {
			if ( result == ResultCode.NOT_VERIFIED ) {
				json.put( "verified-error", true );
			}
			token = "";
		}

		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		json.put( "token", token );
		return json;

	}
}
