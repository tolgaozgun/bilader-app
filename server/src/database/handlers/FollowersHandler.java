package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.FollowersCode;
import jakarta.servlet.ServletException;

public class FollowersHandler extends ProcessHandler {

	private final static String[] keys = { TOKEN_KEY, USER_ID_KEY, "session_token", "user_id" };
	private final String DATABASE_TABLE = "followers";
	private final String FOLLOWING_KEY = "following_id";

	public FollowersHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys ) );
	}

	private FollowersCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		adapter = new DatabaseAdapter();

		if ( params == null || params.size() == 0 ) {
			return FollowersCode.INVALID_REQUEST;
		}

		if ( !checkToken() ) {
			return FollowersCode.INVALID_SESSION;
		}

		if ( adapter.doesExist( DATABASE_TABLE, params ) ) {
			return FollowersCode.OK;
		}

		return FollowersCode.NONE_FOUND;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		FollowersCode result;
		Map< Integer, Object[] > usersMap;
		String[] usersId;
		String[] wanted;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams();
		usersMap = new HashMap< Integer, Object[] >();
		wanted = new String[ 1 ];
		wanted[ 0 ] = FOLLOWING_KEY;

		if ( result == FollowersCode.OK ) {
			usersMap = adapter.select( DATABASE_TABLE, wanted, params );
			usersId = new String[ usersMap.size() ];
			for ( int i = 0; i < usersMap.size(); i++ ) {
				usersId[ i ] = ( String ) usersMap.get( i )[ 0 ];
			}
			JSONObject js = idToUserList( usersId );
			json.put( "users", js );
		} else {
			json.put( "users", "" );
		}
		json.put( "success", result == FollowersCode.OK );
		json.put( "message", result.getMessage() );
		return json;

	}
}
