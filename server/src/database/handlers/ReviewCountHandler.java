package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class ReviewCountHandler extends ProcessHandler {

	private final String DATABASE_TABLE = "reviews";
	private final static String RECEIVER_ID_KEY = "receiver_id";
	private final static String[] keys = { RECEIVER_ID_KEY };

	public ReviewCountHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys, true ) );
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

		if ( adapter.doesExist( DATABASE_TABLE, params ) ) {
			return ResultCode.FOLLOWERS_OK;
		}

		return ResultCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		int count;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();

		if ( result.isSuccess() ) {
			count = adapter.count( DATABASE_TABLE, params );
			json.put( "count", count );
		} else {
			json.put( "count", "0" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
