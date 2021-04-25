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

public class WishlistHandler extends ProcessHandler {

	private static final String WISHLIST_USER_ID_KEY = "user_id";
	private static final String[] KEYS = {};
	private final String DATABASE_TABLE_WISHLIST = "wishlist";
	private final String DATABASE_TABLE_PRODUCT = "products";

	public WishlistHandler( Map< String, String[] > parameters ) {
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

		params.put( WISHLIST_USER_ID_KEY, params.get( USER_ID_KEY ) );

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

		if ( adapter.doesExist( DATABASE_TABLE_WISHLIST, params ) ) {
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

		Map< Integer, Object[] > productResult;
		Map< Integer, Object[] > userResult;
		DatabaseAdapter adapter;
		String[] wanted;
		String[] sellerWanted;
		String[] productWanted;
		String productId;
		String sellerId;
		Object[] objectList;
		ResultCode result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		jsonSqlResult = new JSONObject();
		result = checkParams();

		// Wanted columns from the database
		wanted = new String[ 2 ];
		wanted[ 0 ] = "product_id";
		wanted[ 1 ] = "creation_date";

		productWanted = new String[ 6 ];
		productWanted[ 0 ] = "picture_url";
		productWanted[ 1 ] = "title";
		productWanted[ 2 ] = "description";
		productWanted[ 3 ] = "price";
		productWanted[ 4 ] = "seller_id";
		productWanted[ 5 ] = "category_id";

		sellerWanted = new String[ 2 ];
		sellerWanted[ 0 ] = "avatar_url";
		sellerWanted[ 1 ] = "name";

		if ( result.isSuccess() ) {
			sqlResult = adapter.select( DATABASE_TABLE_WISHLIST, wanted,
					params );
			if ( sqlResult != null && sqlResult.size() > 0 ) {
				for ( int key : sqlResult.keySet() ) {
					tempJson = new JSONObject();
					productId = ( String ) sqlResult.get( key )[ 0 ];
					params = new HashMap< String, String >();
					params.put( "id", productId );
					productResult = adapter.select( DATABASE_TABLE_PRODUCT,
							productWanted, params );
					sellerId = ( String ) productResult.get( 0 )[ 4 ];
					params = new HashMap< String, String >();
					params.put( USER_ID_KEY, sellerId );
					userResult = adapter.select( DATABASE_TABLE_USERS,
							sellerWanted, params );
					objectList = sqlResult.get( key );
					for ( int i = 0; i < wanted.length; i++ ) {
						tempJson.put( wanted[ i ], objectList[ i ] );
					}
					for ( int i = 0; i < productWanted.length; i++ ) {
						tempJson.put( productWanted[ i ],
								productResult.get( 0 )[ i ] );
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
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}