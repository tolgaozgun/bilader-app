package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.PasscodeAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class LoginHandler extends ProcessHandler {

	private static String[] keys = { "email", "password" };
	private final String DATABASE_TABLE = "users";
	private final String PASSWORD_KEY = "password";
	private final String PASSWORD_HASH_KEY = "password_hash";
	private final String PASSWORD_SALT_KEY = "password_salt";
	private final String EMAIL_KEY = "email";

	public LoginHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys, false ) );
	}

	private boolean isVerified( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		String[] wanted;
		Map< Integer, Object[] > map;

		map = new HashMap< Integer, Object[] >();
		wanted = new String[ 1 ];
		wanted[ 0 ] = "verified";
		map = adapter.select( DATABASE_TABLE, wanted, params );
		return ( boolean ) map.get( 0 )[ 0 ];
	}

	private ResultCode checkParams( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {

		Map< String, String > checkParams;

		if ( params == null || params.size() == 0 ) {
			return ResultCode.INVALID_REQUEST;
		}

		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
		if ( !adapter.doesExist( DATABASE_TABLE, checkParams ) ) {
			return ResultCode.ACCOUNT_DOES_NOT_EXIST;
		}

		if ( !isVerified( adapter ) ) {
			return ResultCode.NOT_VERIFIED;
		}

		if ( adapter.doesExist( DATABASE_TABLE, params ) ) {
			return ResultCode.LOGIN_OK;
		}

		return ResultCode.WRONG_PASSWORD;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		String token;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
				DATABASE_TABLE, PASSWORD_KEY, PASSWORD_HASH_KEY,
				PASSWORD_SALT_KEY, EMAIL_KEY );
		result = checkParams( adapter );

		if ( result.isSuccess() ) {
			token = UUID.randomUUID().toString();
		} else {
			token = "";
		}

		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		json.put( "token", token );
		return json;

	}
}
