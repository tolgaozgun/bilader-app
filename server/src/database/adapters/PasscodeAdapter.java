package database.adapters;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * An adapter to hash and salt passwords. If required, uses MySQL connection to
 * retrieve salts for users.
 * 
 * @see DatabaseAdapter
 * @see PasswordAdapter
 * 
 * @author Tolga Özgün
 *
 */

public class PasscodeAdapter {

	public static Map< String, String > hashPasswordWithSalt(
			Map< String, String > params, DatabaseAdapter adapter,
			final String DATABASE_TABLE, final String PASSWORD_KEY,
			final String PASSWORD_HASH_KEY, final String PASSWORD_SALT_KEY,
			final String EMAIL_KEY )
			throws ClassNotFoundException, SQLException {
		Map< String, String > requestParams;
		Map< Integer, Object[] > response;
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
			return null;
		}

		encoder = Base64.getUrlEncoder().withoutPadding();
		decoder = Base64.getUrlDecoder();
		response = new HashMap< Integer, Object[] >();
		requestParams = new HashMap< String, String >();
		requestParams.put( EMAIL_KEY, params.get( EMAIL_KEY ) );
		wanted = new String[ 1 ];
		wanted[ 0 ] = PASSWORD_SALT_KEY;

		response = adapter.select( DATABASE_TABLE, wanted, requestParams );
		if ( response == null || response.size() == 0
				|| response.get( 0 ).length == 0 ) {
			return null;
		}
		salt = ( String ) response.get( 0 )[ 0 ];
		saltArray = decoder.decode( salt );
		password = params.get( PASSWORD_KEY );
		passwordArray = password.toCharArray();

		hashArray = PasswordAdapter.hash( passwordArray, saltArray );
		hash = encoder.encodeToString( hashArray );
		salt = encoder.encodeToString( saltArray );

		params.put( PASSWORD_HASH_KEY, hash );
		params.put( PASSWORD_SALT_KEY, salt );
		params.remove( PASSWORD_KEY );

		return params;

	}

	public static Map< String, String > hashPasswordWithoutSalt(
			Map< String, String > params, final String PASSWORD_KEY,
			final String PASSWORD_HASH_KEY, final String PASSWORD_SALT_KEY ) {
		String password;
		String salt;
		String hash;
		Encoder encoder;
		char[] passwordArray;
		byte[] saltArray;
		byte[] hashArray;

		if ( params == null ) {
			return null;
		}

		encoder = Base64.getUrlEncoder().withoutPadding();
		password = params.get( PASSWORD_KEY );
		saltArray = PasswordAdapter.getNextSalt();
		passwordArray = password.toCharArray();
		hashArray = PasswordAdapter.hash( passwordArray, saltArray );
		hash = encoder.encodeToString( hashArray );
		salt = encoder.encodeToString( saltArray );

		params.put( PASSWORD_HASH_KEY, hash );
		params.put( PASSWORD_SALT_KEY, salt );
		params.remove( PASSWORD_KEY );

		return params;

	}

	public static String codeGenerator( int digits ) {
		Random random;
		StringBuffer buffer;
		random = new Random();
		buffer = new StringBuffer();

		for ( int i = 0; i < digits; i++ ) {
			buffer.append( random.nextInt( 10 ) );
		}

		return buffer.toString();
	}

}
