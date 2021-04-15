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

public class AddReportHandler extends ProcessHandler {

	private static final String[] KEYS = { "report_type", "description",
			"reporter_id", "reported_id" };
	private final String DATABASE_TABLE = "reports";

	public AddReportHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS ) );
	}

	private void createReportId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapReportId;
		UUID productId;
		DatabaseAdapter adapter;
		boolean doesExist;

		if ( params == null ) {
			return;
		}

		mapReportId = new HashMap< String, String >();
		productId = UUID.randomUUID();
		mapReportId.put( "id", productId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE, mapReportId );
		while ( doesExist ) {
			productId = UUID.randomUUID();
			mapReportId.put( "id", productId.toString() );
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
			createReportId();
			adapter.create( DATABASE_TABLE, params );
			json.put( "success", true );
			json.put( "message", "Successful" );
			return json;
		}

		json.put( "success", false );
		json.put( "message", "Invalid requests." );
		return json;
	}
}
