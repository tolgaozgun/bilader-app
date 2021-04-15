package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class AddProductHandler extends ProcessHandler {

	private static final String[] KEYS = { "picture_url", "title",
			"description", "price", "seller_id", "category_id" };
	private final String DATABASE_TABLE = "products";

	public AddProductHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS ) );
	}

	private void createProductId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapProductId;
		UUID productId;
		DatabaseAdapter adapter;
		boolean doesExist;

		if ( params == null ) {
			return;
		}

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

		adapter = new DatabaseAdapter();
		json = new JSONObject();

		if ( params != null ) {
			createProductId();
			adapter.create( DATABASE_TABLE, params );
			json.put( "success", true );
			json.put( "message", "Successful" );
			return json;
		}

		json.put( "success", false );
		json.put( "message", "Invalid request." );
		return json;
	}

}
