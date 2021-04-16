package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class ProductHandler extends ProcessHandler {

	private static final String[] KEYS = {};
	private static final String[] OPTIONAL_KEYS = { "id", "price", "seller_id",
			"category_id" };
	private final String DATABASE_TABLE = "products";
	private final String SUCCESS_MESSAGE = "Retrieve successful.";
	private final String FAIL_MESSAGE = "No products found.";

	public ProductHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS,
				OPTIONAL_KEYS ) );
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		JSONObject tempJson;
		JSONObject jsonSqlResult;
		Map< Integer, Object[] > sqlResult;
		DatabaseAdapter adapter;
		String[] wanted;
		Object[] objectList;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		jsonSqlResult = new JSONObject();
		
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

		sqlResult = adapter.select( DATABASE_TABLE, wanted, params );
		if ( sqlResult != null && sqlResult.size() > 0 ) {
			for ( int key : sqlResult.keySet() ) {
				tempJson = new JSONObject();
				objectList = sqlResult.get( key );
				for ( int i = 0; i < 8; i++ ) {
					tempJson.put( wanted[ i ], objectList[ i ] );
				}
				jsonSqlResult.put( String.valueOf( key ), tempJson );
			}
			json.put( "result", jsonSqlResult );
			json.put( "success", true );
			json.put( "message", SUCCESS_MESSAGE );
			return json;
		}
		json.put( "result", "" );
		json.put( "success", true );
		json.put( "message", FAIL_MESSAGE );
		return json;
	}

}
