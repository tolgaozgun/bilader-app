package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import jakarta.servlet.ServletException;

public abstract class ProcessHandler {

	private final String DATABASE_TABLE_SESSIONS = "sessions";
	private final String DATABASE_TABLE_USERS = "users";
	protected static final String TOKEN_KEY = "session_token";
	protected static final String USER_ID_KEY = "id";
	protected static final String MAIL_KEY = "email";
	protected static final String VERIFIED_KEY = "verified";
	protected static final String[] VERIFICATION_KEYS_ID = {USER_ID_KEY};
	protected static final String[] VERIFICATION_KEYS_MAIL = {MAIL_KEY};
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
		return adapter.doesExist( DATABASE_TABLE_SESSIONS, params );
	}

	protected boolean isVerified() throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		String[] wanted;
		Map< Integer, Object[] > map;
		Map< String, String > verificationParams;

		map = new HashMap< Integer, Object[] >();
		adapter = new DatabaseAdapter();
		verificationParams = cloneMapWithKeys( VERIFICATION_KEYS_ID, params );
		if ( verificationParams == null ) {
			verificationParams = cloneMapWithKeys( VERIFICATION_KEYS_MAIL, params );
			if ( verificationParams == null ) {
				return false;
			}
		}

		wanted = new String[ 1 ];
		wanted[ 0 ] = VERIFIED_KEY;
		map = adapter.select( DATABASE_TABLE_USERS, wanted,
				verificationParams );
		return ( boolean ) map.get( 0 )[ 0 ];
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

		userObj = adapter.select( DATABASE_TABLE_USERS, wanted, idParam );
		objectArray = userObj.get( 0 );
		for ( int i = 0; i < wanted.length; i++ ) {
			json.put( wanted[ i ], objectArray[ i ] );
		}
		return json;
	}

	protected Map< String, String > cloneMapWithKeys( String[] keys,
			Map< String, String > map ) {
		Map< String, String > clone;
		clone = new HashMap< String, String >();

		for ( String key : keys ) {
			if ( !map.containsKey( key ) ) {
				return null;
			}
			clone.put( key, map.get( key ) );
		}
		return clone;

	}

}
