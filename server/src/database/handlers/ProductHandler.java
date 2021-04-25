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

public class ProductHandler extends ProcessHandler {

	private static final String[] KEYS = {};
	private static final String[] OPTIONAL_KEYS = { "product_id", "price", "seller_id",
			"category_id" };
	private final String DATABASE_TABLE = "products";

	public ProductHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, OPTIONAL_KEYS,
				true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String productId;
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

		if(params.containsKey( "product_id" )) {
			productId = params.get( "product_id" );
			params.put( "id", productId );
			params.remove( "product_id" );
		}
		
		if ( adapter.doesExist( DATABASE_TABLE, params ) ) {
			return ResultCode.PRODUCT_OK;
		}

		return ResultCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		JSONObject tempJson;
		JSONObject jsonSqlResult;
		Map< Integer, Object[] > sqlResult;

		Map< Integer, Object[] > userResult;
		DatabaseAdapter adapter;
		String[] wanted;
		String[] sellerWanted;
		String sellerId;
		Object[] objectList;
		ResultCode result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		jsonSqlResult = new JSONObject();
		result = checkParams();

		// Wanted columns from the database
		wanted = new String[ 8 ];
		wanted[ 0 ] = "id";
		wanted[ 1 ] = "picture_url";
		wanted[ 2 ] = "title";
		wanted[ 3 ] = "description";
		wanted[ 4 ] = "price";
		wanted[ 5 ] = "seller_id";
		wanted[ 6 ] = "category_id";
		wanted[ 7 ] = "creation_date";

		sellerWanted = new String[ 2 ];
		sellerWanted[ 0 ] = "avatar_url";
		sellerWanted[ 1 ] = "name";

		if ( result.isSuccess() ) {
			sqlResult = adapter.select( DATABASE_TABLE, wanted, params );
			if ( sqlResult != null && sqlResult.size() > 0 ) {
				for ( int key : sqlResult.keySet() ) {
					tempJson = new JSONObject();
					sellerId = ( String ) sqlResult.get( key )[ 5 ];
					params = new HashMap< String, String >();
					params.put( USER_ID_KEY, sellerId );
					userResult = adapter.select( DATABASE_TABLE_USERS,
							sellerWanted, params );
					objectList = sqlResult.get( key );
					for ( int i = 0; i < wanted.length; i++ ) {
						tempJson.put( wanted[ i ], objectList[ i ] );
					}
					for ( int i = 0; i < sellerWanted.length; i++ ) {
						tempJson.put( "seller_" + sellerWanted[ i ],
								userResult.get( 0 )[ i ] );
					}
					jsonSqlResult.put( String.valueOf( key ), tempJson );
				}
				json.put( "result", jsonSqlResult );
			} else {
				json.put( "result", "" );
			}
		}
		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
