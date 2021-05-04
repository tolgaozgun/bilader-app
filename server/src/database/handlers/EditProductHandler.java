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

public class EditProductHandler extends ProcessHandler {

	private static final String PRODUCT_ID_KEY = "id";
	private static final String TEMP_PRODUCT_ID_KEY = "product_id";
	private static final String PICTURE_URL_KEY = "picture_url";
	private static final String TITLE_KEY = "title";
	private static final String DESC_KEY = "description";
	private static final String PRICE_KEY = "price";
	private static final String CATEGORY_ID_KEY = "category_id";
	private static final String[] KEYS = { TEMP_PRODUCT_ID_KEY, PICTURE_URL_KEY,
			TITLE_KEY, DESC_KEY, PRICE_KEY, CATEGORY_ID_KEY };
	private static final String[] PRODUCT_VERIFICATION_KEYS = {
			PRODUCT_ID_KEY };
	private static final String DATABASE_TABLE = "products";
	private static final String DATABASE_TABLE_USERS = "users";

	public EditProductHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String productId;

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

		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		productId = params.get( TEMP_PRODUCT_ID_KEY );
		params.put( PRODUCT_ID_KEY, productId );
		params.remove( TEMP_PRODUCT_ID_KEY );

		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( PRODUCT_VERIFICATION_KEYS, params );
		if ( !adapter.doesExist( DATABASE_TABLE, checkParams ) ) {
			return ResultCode.PRODUCT_DOES_NOT_EXIST;
		}

		return ResultCode.EDIT_PRODUCT_OK;
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		Map< String, String > checkParams;
		Map< String, Object > updateList;

		adapter = new DatabaseAdapter();
		checkParams = new HashMap< String, String >();
		updateList = new HashMap< String, Object >();
		json = new JSONObject();
		result = checkParams();

		if ( result.isSuccess() ) {
			// Adds the new product to database.
			checkParams = cloneMapWithKeys( PRODUCT_VERIFICATION_KEYS, params );
			for ( String key : KEYS ) {
				if ( params.containsKey( key ) ) {
					updateList.put( key, params.get( key ) );
				}
			}
			updateList.put( PRODUCT_ID_KEY, params.get( PRODUCT_ID_KEY ) );
			adapter.update( DATABASE_TABLE, checkParams, updateList );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "product_id", params.get( PRODUCT_ID_KEY ) );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
