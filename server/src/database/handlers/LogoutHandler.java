package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class LogoutHandler extends ProcessHandler {

	private static final String[] KEYS = {  };

	public LogoutHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String sessionToken;
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
		sessionToken = params.get( TOKEN_KEY );
		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		params.put( USER_ID_KEY, userId );
		params.put( TOKEN_KEY, sessionToken );
		return ResultCode.LOGOUT_OK;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		result = checkParams();

		if ( result.isSuccess() ) {
			adapter.delete( DATABASE_TABLE_SESSIONS, params );
		}
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}
}