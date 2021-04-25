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

public class CategoriesHandler extends ProcessHandler {

	private final static String DATABASE_TABLE = "categories";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String CATEGORIES_PARAM_KEY = "category_id";
	private final static String CATEGORIES_ID_KEY = "id";
	private final static String CATEGORIES_NAME_KEY = "name";
	private final static String[] KEYS = {};
	private final static String[] OPTIONAL_KEYS = { CATEGORIES_PARAM_KEY };

	public CategoriesHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS,
				OPTIONAL_KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String categoryId;
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

		if ( params.containsKey( CATEGORIES_PARAM_KEY ) ) {
			categoryId = params.get( CATEGORIES_PARAM_KEY );
			params.remove( CATEGORIES_PARAM_KEY );
			params.put( CATEGORIES_ID_KEY, categoryId );
		}
		if ( adapter.doesExist( DATABASE_TABLE, params ) ) {
			return ResultCode.CATEGORIES_OK;
		}

		return ResultCode.CATEGORIES_DO_NOT_EXIST;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		Map< Integer, Object[] > categoriesMap;
		DatabaseAdapter adapter;
		ResultCode result;
		String[] wanted;
		JSONObject json;
		JSONObject messagesJson;
		JSONObject tempJson;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		categoriesMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 2 ];
		wanted[ 0 ] = CATEGORIES_ID_KEY;
		wanted[ 1 ] = CATEGORIES_NAME_KEY;

		if ( result.isSuccess() ) {
			categoriesMap = adapter.select( DATABASE_TABLE, wanted, params );
			messagesJson = new JSONObject();
			for ( int i = 0; i < categoriesMap.size(); i++ ) {
				tempJson = new JSONObject();
				tempJson.put( CATEGORIES_ID_KEY, categoriesMap.get( i )[ 0 ] );
				tempJson.put( CATEGORIES_NAME_KEY,
						categoriesMap.get( i )[ 1 ] );
				messagesJson.put( String.valueOf( i ), tempJson );
			}
			json.put( "categories", messagesJson );
		} else {
			json.put( "categories", "" );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}

}
