package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class TokenHandler extends ProcessHandler {

	private static String[] keys = { "id", "session_token" };
	private static String EXPIRE_DATE_KEY = "session_expire";
	private final String DATABASE_TABLE = "sessions";

	public TokenHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys, true ) );
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		String[] wanted;
		JSONObject json;
		long expirationDate;
		long dateNow;
		Map< Integer, Object[] > result;

		dateNow =  System.currentTimeMillis();
		adapter = new DatabaseAdapter();
		json = new JSONObject();

		wanted = new String[ 1 ];
		wanted[ 0 ] = EXPIRE_DATE_KEY;

		result = adapter.select( DATABASE_TABLE, wanted, params );

		if ( result != null && result.size() > 0 ) {
			expirationDate = ( long ) result.get( 0 )[ 0 ];
			if ( expirationDate > dateNow ) {
				json.put( "success", true );
				json.put( "message", "Session resumed." );
			}else {
				json.put( "success", false );
				json.put( "message", "Session expired." );
			}
		}else {
			json.put( "success", false );
			json.put( "message", "Session expired." );
		}

		return json;
	}

}
