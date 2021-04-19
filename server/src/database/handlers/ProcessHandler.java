package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import jakarta.servlet.ServletException;

public abstract class ProcessHandler {

	private final String TOKENS_TABLE_NAME = "sessions";
	private final String USERS_TABLE_NAME = "users";
	protected final static String TOKEN_KEY = "session_token";
	protected final static String USER_ID_KEY = "id";
	protected Map< String, String > params;

	public ProcessHandler( Map< String, String > params ) {
		this.params = params;
	}

	public abstract JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException;

	protected boolean checkToken() throws ClassNotFoundException, SQLException {
		Map< String, String > mailParam;
		DatabaseAdapter adapter;

		adapter = new DatabaseAdapter();
		mailParam = new HashMap< String, String >();
		mailParam.put( TOKEN_KEY, params.get( TOKEN_KEY ) );
		mailParam.put( USER_ID_KEY, params.get( USER_ID_KEY ) );
		params.remove( TOKEN_KEY );
		params.remove( USER_ID_KEY );
		return adapter.doesExist( TOKENS_TABLE_NAME, params );
	}

	protected JSONObject idToUserList( final String[] ids )
			throws ClassNotFoundException, SQLException {
		JSONObject json;
		JSONObject temp;
		int i;
		json = new JSONObject();
		if ( ids == null ) {
			return null;
		}

		i = 0;
		for ( String key : ids ) {
			if ( key != null ) {
				temp = idToUser( key );
				json.put( String.valueOf( i ), temp );
				i++;
			}
		}
		return json;

	}

	protected JSONObject idToUser( final String id )
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > idParam;
		Map< Integer, Object[] > userObj;
		JSONObject json;
		String[] wanted;
		Object[] objectArray;

		userObj = new HashMap< Integer, Object[] >();
		json = new JSONObject();
		adapter = new DatabaseAdapter();
		wanted = new String[ 4 ];
		wanted[ 0 ] = "id";
		wanted[ 1 ] = "email";
		wanted[ 2 ] = "name";
		wanted[ 3 ] = "avatar_url";

		idParam = new HashMap< String, String >();
		idParam.put( USER_ID_KEY, id );

		userObj = adapter.select( USERS_TABLE_NAME, wanted, idParam );
		objectArray = userObj.get( 0 );
		for ( int i = 0; i < wanted.length; i++ ) {
			json.put( wanted[ i ], objectArray[ i ] );
		}

		return json;

	}
	


	protected Map< String, String > cloneMapWithKeys( String[] keys,
			Map< String, String > map ) {

		for ( String key : keys ) {
			if ( !map.containsKey( key ) ) {
				return null;
			}
			map.put( key, map.get( key ) );
		}
		return map;

	}

}
