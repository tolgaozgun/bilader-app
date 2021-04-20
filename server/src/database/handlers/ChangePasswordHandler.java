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
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class ChangePasswordHandler extends ProcessHandler {

	private final static String DATABASE_TABLE_VERIFICATION = "pwverifications";
	private final static String DATABASE_TABLE_USER = "users";
	private final static String DATABASE_TABLE_SESSIONS = "sessions";
	private final static String CODE_KEY = "code";
	private final static String CODE_HASH_KEY = "verification_hash";
	private final static String CODE_SALT_KEY = "verification_salt";
	private final static String PASSWORD_KEY = "password";
	private final static String PASSWORD_HASH_KEY = "password_hash";
	private final static String PASSWORD_SALT_KEY = "password_salt";
	private final static String REQUESTED_DATE_KEY = "requested_date";
	private final static String EMAIL_KEY = "email";
	private static final String[] KEYS = { EMAIL_KEY, CODE_KEY, PASSWORD_KEY };
	private static final String[] CHECK_KEYS = { EMAIL_KEY };
	private final long EXPIRE_TIME_IN_MS = 1800000;

	public ChangePasswordHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS, false ) );
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;
		String newPassword;
		String[] wanted;
		Timestamp timeCreated;
		Timestamp timeNow;
		long diff;

		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return ResultCode.INVALID_REQUEST;
		}

		adapter = new DatabaseAdapter();
		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( CHECK_KEYS, params );
		if ( !adapter.doesExist( DATABASE_TABLE_USER, checkParams ) ) {
			return ResultCode.ACCOUNT_DOES_NOT_EXIST;
		}

		// Use of new password parameter is not needed, and current
		// methods should not check for this new password parameter.
		newPassword = params.get( PASSWORD_KEY );
		params.remove( PASSWORD_KEY );

		// Checks if the current user is already verified.
		if ( !isVerified() ) {
			return ResultCode.NOT_VERIFIED;
		}

		// Check if there is a pending request linked to the current user.
		if ( !adapter.doesExist( DATABASE_TABLE_VERIFICATION, checkParams ) ) {
			return ResultCode.NO_PENDING_REQUEST;
		}

		params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
				DATABASE_TABLE_VERIFICATION, CODE_KEY, CODE_HASH_KEY,
				CODE_SALT_KEY, EMAIL_KEY );

		// Check if the password is correct..
		if ( !adapter.doesExist( DATABASE_TABLE_VERIFICATION, params ) ) {
			return ResultCode.WRONG_VERIFICATION_CODE;
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
			return ResultCode.VERIFICATION_EXPIRED;
		}

		// Checks if the current password is true.
		if ( adapter.doesExist( DATABASE_TABLE_VERIFICATION, params ) ) {
			// We need to use the password from now on.
			params.put( PASSWORD_KEY, newPassword );
			return ResultCode.CHANGE_PASS_OK;
		}

		return ResultCode.WRONG_VERIFICATION_CODE;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		String[] idColumn;
		String userId;
		ResultCode result;
		Map< String, Object > updateList;

		json = new JSONObject();
		adapter = new DatabaseAdapter();

		result = checkParams();

		// Updates the account to verified status.
		if ( result.isSuccess() ) {

			params.remove( CODE_SALT_KEY );
			params.remove( CODE_HASH_KEY );

			params = PasscodeAdapter.hashPasswordWithSalt( params, adapter,
					DATABASE_TABLE_USER, PASSWORD_KEY, PASSWORD_HASH_KEY,
					PASSWORD_SALT_KEY, EMAIL_KEY );

			updateList = new HashMap< String, Object >();
			updateList.put( PASSWORD_HASH_KEY,
					params.get( PASSWORD_HASH_KEY ) );
			params.remove( PASSWORD_HASH_KEY );
			params.remove( PASSWORD_SALT_KEY );

			adapter.update( DATABASE_TABLE_USER, params, updateList );

			// Delete current verification code.
			adapter.delete( DATABASE_TABLE_VERIFICATION, params );

			// Delete email's session token if exists.
			idColumn = new String[ 1 ];
			idColumn[ 0 ] = USER_ID_KEY;
			userId = ( String ) adapter
					.select( DATABASE_TABLE_USER, idColumn, params )
					.get( 0 )[ 0 ];
			params.remove( EMAIL_KEY );
			params.put( USER_ID_KEY, userId );
			adapter.delete( DATABASE_TABLE_SESSIONS, params );
		}

		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;

	}
}
