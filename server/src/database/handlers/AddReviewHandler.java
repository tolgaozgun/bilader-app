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

public class AddReviewHandler extends ProcessHandler {

	private static final String TIME_KEY = "time";
	private static final String CONTENT_KEY = "content";
	private static final String SENDER_ID_KEY = "sender_id";
	private static final String RECEIVER_ID_KEY = "receiver_id";
	private static final String REVIEW_ID_KEY = "id";
	private static final String[] KEYS = { CONTENT_KEY, RECEIVER_ID_KEY };
	private final String DATABASE_TABLE_REVIEWS = "reviews";
	private final String DATABASE_TABLE_USERS = "users";

	public AddReviewHandler( Map< String, String[] > parameters ) {
		super( RequestAdapter.convertParameters( parameters, KEYS, true ) );
		if ( params != null ) {
			String id;
			id = parameters.get( USER_ID_KEY )[ 0 ];
			params.put( SENDER_ID_KEY, id );
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

	private void createReviewId() throws ClassNotFoundException, SQLException {
		Map< String, String > mapReviewId;
		UUID reviewId;
		DatabaseAdapter adapter;
		boolean doesExist;

		mapReviewId = new HashMap< String, String >();
		reviewId = UUID.randomUUID();
		mapReviewId.put( REVIEW_ID_KEY, reviewId.toString() );
		adapter = new DatabaseAdapter();
		doesExist = adapter.doesExist( DATABASE_TABLE_REVIEWS, mapReviewId );
		while ( doesExist ) {
			reviewId = UUID.randomUUID();
			mapReviewId.put( "id", reviewId.toString() );
			doesExist = adapter.doesExist( DATABASE_TABLE_REVIEWS,
					mapReviewId );
		}
		params.put( REVIEW_ID_KEY, reviewId.toString() );

	}

	@Override
	public JSONObject getResult() throws ServletException, IOException,
			ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		ResultCode result;
		long time;

		adapter = new DatabaseAdapter();
		json = new JSONObject();
		result = checkParams();

		if ( result.isSuccess() ) {
			createReviewId();
			time = System.currentTimeMillis();
			params.put( TIME_KEY, String.valueOf( time ) );
			adapter.create( DATABASE_TABLE_REVIEWS, params );
		}

		json.put( "session_error", result == ResultCode.INVALID_SESSION );
		json.put( "success", result.isSuccess() );
		json.put( "message", result.getMessage() );
		return json;
	}
}
