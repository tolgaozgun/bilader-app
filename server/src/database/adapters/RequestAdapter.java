package database.adapters;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.handlers.ProcessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestAdapter {


	public static Map< String, String > convertParameters(
			Map< String, String[] > map, String[] keys,
			String[] optionalKeys ) {

		Map< String, String > params;

		String paramValue;

		params = new HashMap< String, String >();

		if ( map == null ) {
			return null;
		}

		if ( keys != null && keys.length > 0 ) {
			// Only accepted keys and their existing values are taking into
			// consideration.
			for ( String key : keys ) {
				// Since our arrays will only contain one String, we can cast it
				// into a String
				// by taking the first element.
				if ( map.containsKey( key ) ) {
					paramValue = map.get( key )[ 0 ];
					params.put( key, paramValue );
				} else {
					return null;
				}
			}
		}

		if ( optionalKeys != null && optionalKeys.length > 0 ) {
			for ( String key : optionalKeys ) {
				if ( map.containsKey( key ) ) {
					paramValue = map.get( key )[ 0 ];
					params.put( key, paramValue );
				}
			}
		}

		return params;
	}

	public static Map< String, String > convertParameters(
			Map< String, String[] > map, String[] keys ) {
		return convertParameters( map, keys, null );
	}

	public static void handleRequest( HttpServletRequest request,
			HttpServletResponse response, ProcessHandler handler )
			throws ServletException, IOException {
		JSONObject json;
		PrintWriter out;

		response.setContentType( "application/json" );
		out = response.getWriter();
		try {
			json = handler.getResult();
		} catch ( ClassNotFoundException | ServletException | IOException
				| SQLException e ) {
			json = new JSONObject();
			json.put( "success", false );
			json.put( "message", e.getMessage() );
		}
		out.print( json );
		out.flush();
	}
}
