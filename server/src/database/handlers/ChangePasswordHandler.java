package database.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.PasscodeAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ChangePasswordCode;
import jakarta.servlet.ServletException;

public class ChangePasswordHandler extends ProcessHandler {

	private final static String DATABASE_TABLE_VERIFICATION = "pwverifications";
	private final static String DATABASE_TABLE_USER = "users";
	private final static String CODE_KEY = "code";
	private final static String CODE_HASH_KEY = "verification_hash";
	private final static String CODE_SALT_KEY = "verification_salt";
	private final static String PASSWORD_KEY = "password";
	private final static String PASSWORD_HASH_KEY = "password_hash";
	private final static String PASSWORD_SALT_KEY = "password_salt";
	private final static String VERIFIED_KEY = "verified";
	private final static String REQUESTED_DATE_KEY = "requested_date";
	private final static String EMAIL_KEY = "email";
	private static final String[] KEYS = { EMAIL_KEY, CODE_KEY, PASSWORD_KEY };
	private static final String[] VERIFICATION_KEYS = { EMAIL_KEY };
	private final long EXPIRE_TIME_IN_MS = 1800000;

	public ChangePasswordHandler( PrintWriter out,
			Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS ) );
	}

	private boolean isVerified() throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		String[] wanted;
		Map< Integer, Object[] > map;
		Map< String, String > verificationParams;

		adapter = new DatabaseAdapter();
		map = new HashMap< Integer, Object[] >();
		verificationParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
		if ( verificationParams == null ) {
			return false;
		}

		wanted = new String[ 1 ];
		wanted[ 0 ] = VERIFIED_KEY;
		map = adapter.select( DATABASE_TABLE_USER, wanted, verificationParams );
		return ( boolean ) map.get( 0 )[ 0 ];
	}

	private ChangePasswordCode checkParams( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {

		String newPassword;
		String[] wanted;
		Timestamp timeCreated;
		Timestamp timeNow;
		long diff;

		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return ChangePasswordCode.INVALID_REQUEST;
		}

		// Use of new password parameter is not needed at the moment.
		newPassword = params.get( PASSWORD_KEY );
		params.remove( PASSWORD_KEY );
		// Checks if the current user is already verified.
		if ( !isVerified() ) {
			return ChangePasswordCode.NOT_VERIFIED;
		}

		// Gets Timestamp object of the verification request creation time.
		wanted = new String[ 1 ];
		wanted[ 0 ] = REQUESTED_DATE_KEY;
		timeCreated = ( Timestamp ) adapter
				.select( DATABASE_TABLE_VERIFICATION, wanted, params )
				.get( 0 )[ 0 ];

		// Compares the request time to now to check if it has expired.
		timeNow = new Timestamp( System.currentTimeMillis() );
		diff = timeNow.getTime() - timeCreated.getTime();
		if ( diff > EXPIRE_TIME_IN_MS ) {
			return ChangePasswordCode.EXPIRED;
		}

		// Checks if the current password is true.
		if ( adapter.doesExist( DATABASE_TABLE_VERIFICATION, params ) ) {
			params.put( PASSWORD_KEY, newPassword );
			return ChangePasswordCode.OK;
		}

		return ChangePasswordCode.WRONG_PASSWORD;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ChangePasswordCode result;
		Map< String, Object > updateList;

		json = new JSONObject();
		adapter = new DatabaseAdapter();
		params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
				DATABASE_TABLE_VERIFICATION, CODE_KEY, CODE_HASH_KEY,
				CODE_SALT_KEY, EMAIL_KEY );
		result = checkParams( adapter );

		// Updates the account to verified status.
		if ( result == ChangePasswordCode.OK ) {
			params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
					DATABASE_TABLE_USER, PASSWORD_KEY, PASSWORD_HASH_KEY,
					PASSWORD_SALT_KEY, EMAIL_KEY );
			updateList = new HashMap< String, Object >();
			updateList.put( PASSWORD_HASH_KEY,
					params.get( PASSWORD_HASH_KEY ) );
			adapter.update( DATABASE_TABLE_USER, params, updateList );

			// TODO: Delete email's session token if exists.
			// TODO: Delete the current verification code.
		}

		json.put( "success", result == ChangePasswordCode.OK );
		json.put( "message", result.getMessage() );
		return json;

	}
}
