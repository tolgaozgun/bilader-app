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

public class UpdatePhotoHandler extends ProcessHandler {

	private static final String AVATAR_URL_KEY = "avatar_url";
	private static final String[] KEYS = { AVATAR_URL_KEY };
	private static final String DATABASE_TABLE_USERS = "users";

	public UpdatePhotoHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String userId;

		adapter = new DatabaseAdapter();
		// Checks if the parameters are non-existent.
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

		return ResultCode.EDIT_PICTURE_OK;
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		Map< String, Object > updateList;

		adapter = new DatabaseAdapter();
		updateList = new HashMap< String, Object >();
		json = new JSONObject();
		result = checkParams();

		if ( result.isSuccess() ) {
			// Adds the new product to database.
			updateList.put( AVATAR_URL_KEY, params.get( AVATAR_URL_KEY ) );
			params.remove( AVATAR_URL_KEY );
			adapter.update( DATABASE_TABLE_USERS, params, updateList );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
