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

public class ReviewsHandler extends ProcessHandler {

	private static final String SENDER_ID_KEY = "sender_id";
	private static final String REPORTED_ID_KEY = "receiver_id";
	private static final String CONTENT_KEY = "content";
	private static final String[] KEYS = { SENDER_ID_KEY, REPORTED_ID_KEY,
			CONTENT_KEY };
	private static final String[] VERIFICATION_KEYS = { SENDER_ID_KEY, CONTENT_KEY };
	private final String DATABASE_TABLE_REVIEWS = "reviews";
	private final String DATABASE_TABLE_USERS = "users";

	public ReviewsHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
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
		checkParams = cloneMapWithKeys( VERIFICATION_KEYS, params );
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

		return ResultCode.ADD_PRODUCT_OK;
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
		doesExist = adapter.doesExist( DATABASE_TABLE_REVIEWS, mapReportId );
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
			adapter.create( DATABASE_TABLE_REVIEWS, params );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}
}
