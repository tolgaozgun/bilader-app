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
import database.handlers.codes.RegisterCode;
import jakarta.servlet.ServletException;

public class RegisterHandler extends ProcessHandler {

	private static final String[] KEYS = { "email", "name", "password",
			"avatar_url" };
	private final String DATABASE_TABLE = "users";
	private final String BILKENT_DOMAIN = "bilkent.edu.tr";
	private final static String PASSWORD_KEY = "password";
	private final static String PASSWORD_HASH_KEY = "password_hash";
	private final static String PASSWORD_SALT_KEY = "password_salt";

	public RegisterHandler( Map< String, String[] > params ) {
		super( RequestAdapter.convertParameters( params, KEYS ) );
	}

	private void createUserId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapUserId;
		UUID userId;
		DatabaseAdapter adapter;
		boolean doesExist;

		if ( params == null ) {
			return;
		}

		mapUserId = new HashMap< String, String >();
		userId = UUID.randomUUID();
		mapUserId.put( "id", userId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE, mapUserId );
		while ( doesExist ) {
			userId = UUID.randomUUID();
			mapUserId.put( "id", userId.toString() );
		}
		params.put( "id", userId.toString() );

	}

	private boolean checkBilkentEmail() {
		String email;
		String domain;
		email = params.get( "email" );
		if ( email.length() < 16 ) {
			return false;
		}
		domain = email.substring( email.length() - 14, email.length() );

		if ( domain.equals( BILKENT_DOMAIN ) ) {
			return true;
		}
		return false;
	}

	private boolean doesExist( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		Map< String, String > checkParams;
		checkParams = new HashMap< String, String >();
		checkParams.put( "email", params.get( "email" ) );

		return adapter.doesExist( DATABASE_TABLE, checkParams );
	}

	private RegisterCode checkParams( DatabaseAdapter adapter )
			throws ClassNotFoundException, SQLException {
		if ( params == null ) {
			return RegisterCode.INVALID_REQUEST;
		}

		if ( !checkBilkentEmail() ) {
			// message = "You need to use your Bilkent email to use this
			// service!";
			return RegisterCode.NOT_EDU_MAIL;
		}

		if ( doesExist( adapter ) ) {
			// message = "Your email is already registered!";
			return RegisterCode.ALREADY_REGISTERED;
		}
		// message = "Account successfully registered.";
		return RegisterCode.OK;

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		String message;
		DatabaseAdapter adapter;
		RegisterCode status;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		status = checkParams( adapter );

		if ( status.equals( RegisterCode.OK ) ) {
			createUserId();
			params = PasscodeAdapter.hashPasswordWithoutSalt( params,
					PASSWORD_KEY, PASSWORD_HASH_KEY, PASSWORD_SALT_KEY );
			adapter.create( DATABASE_TABLE, params );
		}

		if ( status == RegisterCode.INVALID_REQUEST ) {
			message = "Invalid request parameters! Please contact developers.";
		} else if ( status == RegisterCode.NOT_EDU_MAIL ) {
			message = "You need to use your Bilkent email to use this service!";
		} else if ( status == RegisterCode.ALREADY_REGISTERED ) {
			message = "Your email is already registered!";
		} else {
			message = "Account successfully registered.";
		}

		json.put( "success", status == RegisterCode.OK );
		json.put( "message", message );

		return json;
	}

}
