package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import database.handlers.codes.ResultCode;
import jakarta.servlet.ServletException;

public class AddReportHandler extends ProcessHandler {

	private static final String REPORT_TYPE_KEY = "report_type";
	private static final String DESC_KEY = "description";
	private static final String REPORTER_ID_KEY = "reporter_id";
	private static final String REPORTED_ID_KEY = "reported_id";
	private static final String[] KEYS = { REPORT_TYPE_KEY, DESC_KEY,
			REPORTED_ID_KEY };
	private final String DATABASE_TABLE_REPORTS = "reports";
	private final String DATABASE_TABLE_USERS = "users";

	public AddReportHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
		if ( params != null ) {
			String id;
			id = parameters.get( USER_ID_KEY )[ 0 ];
			params.put( REPORTER_ID_KEY, id );
		}
	}

	private ResultCode checkParams()
			throws ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		Map< String, String > checkParams;

		adapter = new DatabaseAdapter();
		// Checks if the parameters are non-existent.
		if ( params == null || params.size() == 0 ) {
			return ResultCode.INVALID_REQUEST;
		}
		// Check if the current user exists in the database.
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS_ID, params );
		if ( !adapter.doesExist( DATABASE_TABLE_USERS, checkParams ) ) {
			return ResultCode.ACCOUNT_DOES_NOT_EXIST;
		}

		// Checks if the current user is not verified.
		if ( !isVerified() ) {
			return ResultCode.NOT_VERIFIED;
		}

		if ( !checkToken() ) {
			return ResultCode.INVALID_SESSION;
		}

		return ResultCode.ADD_REPORT_OK;
	}

	private void createReportId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapReportId;
		UUID productId;
		DatabaseAdapter adapter;
		boolean doesExist;

		mapReportId = new HashMap< String, String >();
		productId = UUID.randomUUID();
		mapReportId.put( "id", productId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE_REPORTS, mapReportId );
		while ( doesExist ) {
			productId = UUID.randomUUID();
			mapReportId.put( "id", productId.toString() );
		}
		params.put( "id", productId.toString() );

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		result = checkParams();

		if ( result.isSuccess() ) {
			createReportId();
			adapter.create( DATABASE_TABLE_REPORTS, params );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}
}
