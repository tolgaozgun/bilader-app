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

public class FollowersHandler extends ProcessHandler {

	private final String DATABASE_TABLE = "followers";
	private final String DATABASE_TABLE_USERS = "users";
	private final static String FOLLOWING_KEY = "following_id";
	private final static String FOLLOWERS_USER_ID_KEY = "user_id";
	private final static String[] keys = { FOLLOWING_KEY };

	public FollowersHandler( Map< String, String[] > params ) {
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
		Map< Integer, Object[] > usersMap;
		String[] usersId;
		String[] wanted;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 1 ];
		wanted[ 0 ] = FOLLOWERS_USER_ID_KEY;

		if ( result.isSuccess() ) {
			usersMap = adapter.select( DATABASE_TABLE, wanted, params );
			usersId = new String[ usersMap.size() ];
			for ( int i = 0; i < usersMap.size(); i++ ) {
				usersId[ i ] = ( String ) usersMap.get( i )[ 0 ];
			}
			JSONObject js = idToUserList( usersId );
			json.put( "users", js );
		} else {
			json.put( "users", "" );
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}
}
