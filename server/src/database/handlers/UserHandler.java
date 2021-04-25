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

public class UserHandler extends ProcessHandler {

	private static final String USER_PARAM_KEY = "user_id";
	private static final String[] KEYS = { USER_PARAM_KEY };

	public UserHandler( Map< String, String[] > params ) {
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

		params.put( USER_ID_KEY, params.get( USER_PARAM_KEY ) );
		params.remove( USER_PARAM_KEY );

		if ( adapter.doesExist( DATABASE_TABLE_USERS, params ) ) {
			return ResultCode.USERS_OK;
		}

		return ResultCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		JSONObject tempJson;

		Map< Integer, Object[] > userResult;
		DatabaseAdapter adapter;
		String[] userWanted;
		ResultCode result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		tempJson = new JSONObject();
		result = checkParams();

		userWanted = new String[ 2 ];
		userWanted[ 0 ] = "avatar_url";
		userWanted[ 1 ] = "name";

		if ( result.isSuccess() ) {
			userResult = adapter.select( DATABASE_TABLE_USERS, userWanted,
					params );
			for ( int i = 0; i < userWanted.length; i++ ) {
				tempJson.put( userWanted[ i ], userResult.get( 0 )[ i ] );
			}
			json.put( "user", tempJson );
		} else {
			json.put( "user", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
