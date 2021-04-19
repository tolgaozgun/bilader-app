package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.PasscodeAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.AddVerificationCode;
import database.handlers.codes.VerificationCode;
import jakarta.servlet.ServletException;

public class VerificationHandler extends ProcessHandler {

	private final static String DATABASE_TABLE_VERIFICATION = "verifications";
	private final static String DATABASE_TABLE_USER = "users";
	private final static String PASSWORD_KEY = "code";
	private final static String PASSWORD_HASH_KEY = "verification_hash";
	private final static String PASSWORD_SALT_KEY = "verification_salt";
	private final static String EMAIL_KEY = "email";
	private static String[] keys = { EMAIL_KEY, PASSWORD_KEY };
	private static final String[] VERIFICATION_KEYS = { EMAIL_KEY };
	private final static String VERIFIED_KEY = "verified";
	private final long EXPIRE_TIME_IN_MS = 1800000;

	public VerificationHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, keys ) );
	}

	private boolean isVerified( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		String[] wanted;
		Map< Integer, Object[] > map;
		Map< String, String > verificationParams;

		map = new HashMap< Integer, Object[] >();
		verificationParams = cloneMapWithKeys( VERIFICATION_KEYS, params );

		wanted = new String[ 1 ];
		wanted[ 0 ] = VERIFIED_KEY;
		map = adapter.select( DATABASE_TABLE_USER, wanted, verificationParams );
		return ( boolean ) map.get( 0 )[ 0 ];
	}

	private VerificationCode checkParams( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {

		String[] wanted;
		Timestamp timeCreated;
		Timestamp timeNow;
		Map< String, String > checkParams;
		long diff;

		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return VerificationCode.INVALID_REQUEST;
		}

		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
		if ( !adapter.doesExist( DATABASE_TABLE_USER, checkParams ) ) {
			return VerificationCode.ACCOUNT_DOES_NOT_EXIST;
		}
		
		// Checks if the current user is already verified.
		if ( isVerified( adapter ) ) {
			return VerificationCode.ALREADY_VERIFIED;
		}

		if ( !adapter.doesExist( DATABASE_TABLE_VERIFICATION, checkParams ) ) {
			return VerificationCode.NO_PENDING_REQUEST;
		}

		// Hash the current password with the salt in the database.
		params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
				DATABASE_TABLE_VERIFICATION, PASSWORD_KEY, PASSWORD_HASH_KEY,
				PASSWORD_SALT_KEY, EMAIL_KEY );

		// Checks if the current password is true.
		if ( adapter.doesExist( DATABASE_TABLE_VERIFICATION, params ) ) {

			// Gets Timestamp object of the verification request creation time.
			wanted = new String[ 1 ];
			wanted[ 0 ] = "requested_date";
			timeCreated = ( Timestamp ) adapter
					.select( DATABASE_TABLE_VERIFICATION, wanted, params )
					.get( 0 )[ 0 ];

			// Compares the request time to now to check if it has expired.
			timeNow = new Timestamp( System.currentTimeMillis() );
			diff = timeNow.getTime() - timeCreated.getTime();
			if ( diff > EXPIRE_TIME_IN_MS ) {
				return VerificationCode.EXPIRED;
			}

			return VerificationCode.OK;
		}

		return VerificationCode.WRONG_PASSWORD;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		VerificationCode result;
		Map< String, Object > updateList;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		result = checkParams( adapter );

		// Updates the account to verified status.
		if ( result == VerificationCode.OK ) {
			// Remove the current verification code.
			adapter.delete( DATABASE_TABLE_VERIFICATION, params );

			// Remove password from map as it is no longer needed.
			// Authentication is done.
			params.remove( PASSWORD_HASH_KEY );
			params.remove( PASSWORD_SALT_KEY );

			// Update users database to set the user verified.
			updateList = new HashMap< String, Object >();
			updateList.put( "verified", 1 );
			adapter.update( DATABASE_TABLE_USER, params, updateList );

		}

		// TODO: Delete the current verification code.

		json.put( "success", result == VerificationCode.OK );
		json.put( "message", result.getMessage() );
		return json;

	}
}
