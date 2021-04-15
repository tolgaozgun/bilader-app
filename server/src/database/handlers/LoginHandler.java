package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.PasswordAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class LoginHandler extends ProcessHandler {

	private static String[] keys = { "email", "password" };
	private final String DATABASE_TABLE = "users";
	private final String SUCCESS_MESSAGE = "Login correct.";
	private final String FAIL_MESSAGE = "Login details incorrect.";

	public LoginHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys ) );
	}

	private void hashPassword( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		Map< String, String > requestParams;
		String[] wanted;
		String password;
		String salt;
		String hash;
		Encoder encoder;
		Decoder decoder;
		char[] passwordArray;
		byte[] saltArray;
		byte[] hashArray;

		if ( params == null ) {
			return;
		}

		encoder = Base64.getUrlEncoder().withoutPadding();
		decoder = Base64.getUrlDecoder();
		requestParams = new HashMap< String, String >();
		requestParams.put( "email", params.get( "email" ) );
		wanted = new String[ 1 ];
		wanted[ 0 ] = "password_salt";

		salt = ( String ) adapter
				.select( DATABASE_TABLE, wanted, requestParams ).get( 0 )[ 0 ];
		saltArray = decoder.decode( salt );
		password = params.get( "password" );
		passwordArray = password.toCharArray();

		hashArray = PasswordAdapter.hash( passwordArray, saltArray );
		hash = encoder.encodeToString( hashArray );
		salt = encoder.encodeToString( saltArray );

		params.put( "password_hash", hash );
		params.put( "password_salt", salt );
		params.remove( "password" );

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		boolean result;

		adapter = new DatabaseAdapter();
		hashPassword( adapter );
		json = new JSONObject();

		result = adapter.doesExist( DATABASE_TABLE, params );
		json.put( "success", result );
		json.put( "token", "" );
		if ( result ) {
			json.put( "message", SUCCESS_MESSAGE );
			json.put( "token", createToken() );
		} else {
			json.put( "message", FAIL_MESSAGE );
		}
		return json;

	}

	private String createToken() {
		return UUID.randomUUID().toString();
	}
}
