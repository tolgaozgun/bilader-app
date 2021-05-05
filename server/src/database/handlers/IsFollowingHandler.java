package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class IsFollowingHandler extends ProcessHandler {

	private static final String FOLLOWING_ID_KEY = "following_id";
	private static final String FOLLOWERS_USER_ID_KEY = "user_id";
	private static final String[] KEYS = { FOLLOWING_ID_KEY };
	private static final String[] PRODUCT_CHECK_KEYS = { FOLLOWERS_USER_ID_KEY, FOLLOWING_ID_KEY };
	private final String DATABASE_TABLE_FOLLOWERS = "followers";

	public IsFollowingHandler( Map< String, String[] > parameters ) {
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
		
		params.put( FOLLOWERS_USER_ID_KEY, params.get( USER_ID_KEY ) );
		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		checkParams = cloneMapWithKeys( PRODUCT_CHECK_KEYS, params );
		if ( adapter.doesExist( DATABASE_TABLE_FOLLOWERS, checkParams ) ) {
			return ResultCode.CHECK_ALREADY_FOLLOWING;
		}

		return ResultCode.CHECK_NOT_FOLLOWING;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		ResultCode result;

		json = new JSONObject();
		result = checkParams();

		if ( result == ResultCode.CHECK_ALREADY_FOLLOWING ) {
			json.put( "following", true );
		} else {
			json.put( "following", false );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
