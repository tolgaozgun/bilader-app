package database.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.JsonAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class TokenHandler extends ProcessHandler {

	private static String[] keys = { "user_id", "session_token" };
	private static String EXPIRE_DATE_KEY = "session_expire";
	private final String DATABASE_TABLE = "sessions";
	private final String SUCCESS_MESSAGE = "Session resumed.";
	private final String FAIL_MESSAGE = "Session expired.";

	public TokenHandler(Map<String, String[]> params) {
		super(RequestAdapter.convertParameters(params, keys));
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException, ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		String[] wanted;
		Timestamp expirationDate;
		Timestamp dateNow;
		Map<Integer, Object[]> result;

		dateNow = new Timestamp(System.currentTimeMillis());
		adapter = new DatabaseAdapter();
		wanted = new String[1];
		wanted[0] = EXPIRE_DATE_KEY;
		result = adapter.select(DATABASE_TABLE, wanted, params);
		if( result == null | result.size() == 0 ) {
			return getJSON(false);
		}
		expirationDate = (Timestamp) result.get(0)[0];
		return getJSON(expirationDate.after(dateNow));
	}

	private JSONObject getJSON(boolean success) {
		Map<String, Object> values;

		values = new HashMap<String, Object>();

		values.put("success", success);
		if (success) {
			values.put("message", SUCCESS_MESSAGE);
		} else {
			values.put("message", FAIL_MESSAGE);
		}
		return JsonAdapter.createJSON(values);
	}

}
