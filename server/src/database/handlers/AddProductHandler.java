package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class AddProductHandler extends ProcessHandler {

	private static final String[] KEYS = { "picture_url", "title",
			"description", "price", "seller_id", "category_id" };
	private static final String DATABASE_TABLE = "products";
	private static final String DATABASE_TABLE_USERS = "users";

	public AddProductHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, true ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;

		adapter = new DatabaseAdapter();
		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return ResultCode.INVALID_REQUEST;
		}

		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
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

		return ResultCode.ADD_PRODUCT_OK;
	}

	private void createProductId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapProductId;
		UUID productId;
		DatabaseAdapter adapter;
		boolean doesExist;

		mapProductId = new HashMap< String, String >();
		productId = UUID.randomUUID();
		mapProductId.put( "id", productId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE, mapProductId );
		while ( doesExist ) {
			productId = UUID.randomUUID();
			mapProductId.put( "id", productId.toString() );
		}
		params.put( "id", productId.toString() );

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

		if ( result.isSuccess()) {
			createProductId();
			// Adds the new product to database.
			adapter.create( DATABASE_TABLE, params );
		}

		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}

}
