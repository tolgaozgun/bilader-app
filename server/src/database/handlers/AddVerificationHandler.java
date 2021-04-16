package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.MailAdapter;
import database.adapters.MailType;
import database.adapters.PasscodeAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.AddVerificationCode;
import jakarta.servlet.ServletException;

public class AddVerificationHandler extends ProcessHandler {
	
	private final static String DATABASE_TABLE_VERIFICATION = "verifications";
	private final static String DATABASE_TABLE_USERS = "users";
	private final static String PASSWORD_KEY = "code";
	private final static String PASSWORD_HASH_KEY = "verification_hash";
	private final static String PASSWORD_SALT_KEY = "verification_salt";
	private final static String DATE_KEY = "requested_date";
	private final static String EMAIL_KEY = "email";
	private final int CODE_LENGTH = 6;
	private static String[] keys = { EMAIL_KEY };

	public AddVerificationHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys ) );
	}

	private boolean isVerified( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		String[] wanted;
		Map< Integer, Object[] > map;

		map = new HashMap< Integer, Object[] >();
		wanted = new String[ 1 ];
		wanted[ 0 ] = "verified";
		map = adapter.select( DATABASE_TABLE_USERS, wanted, params );
		return ( boolean ) map.get( 0 )[ 0 ];
	}

	private AddVerificationCode checkParams( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {

		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return AddVerificationCode.INVALID_REQUEST;
		}

		// Checks if the current user is already verified.
		if ( isVerified( adapter ) ) {
			return AddVerificationCode.ALREADY_VERIFIED;
		}

		// Checks if the current password is true.
		if ( adapter.doesExist( DATABASE_TABLE_USERS, params ) ) {
			return AddVerificationCode.OK;
		}

		return AddVerificationCode.ACCOUNT_DOES_NOT_EXIST;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		AddVerificationCode result;
		String code;
		Map< String, String > emailParam;
		Map< String, Object > paramsObj;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams( adapter );

		// Updates the account to verified status.
		if ( result == AddVerificationCode.OK ) {
			code = PasscodeAdapter.codeGenerator( CODE_LENGTH );
			params.put( PASSWORD_KEY, code );
			params = PasscodeAdapter.hashPasswordWithoutSalt( params,
					PASSWORD_KEY, PASSWORD_HASH_KEY, PASSWORD_SALT_KEY );

			// Check if the current mail has verification data in the database.
			// If so update the existing code.
			emailParam = new HashMap< String, String >();
			paramsObj = new HashMap< String, Object >();
			for ( String key : params.keySet() ) {
				paramsObj.put( key, params.get( key ) );
			}
			paramsObj.put( DATE_KEY,
					new Timestamp( System.currentTimeMillis() ) );
			emailParam.put( EMAIL_KEY, params.get( EMAIL_KEY ) );

			if ( adapter.doesExist( DATABASE_TABLE_VERIFICATION,
					emailParam ) ) {

				adapter.update( DATABASE_TABLE_VERIFICATION, emailParam,
						paramsObj );
			} else {
				adapter.create( DATABASE_TABLE_VERIFICATION, params );
			}
			MailAdapter.sendMail( MailType.VERIFICATION, code, params.get( EMAIL_KEY ) );
		}

		json.put( "success", result == AddVerificationCode.OK );
		json.put( "message", result.getMessage() );
		return json;

	}

}
