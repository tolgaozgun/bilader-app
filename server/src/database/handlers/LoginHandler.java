package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.JsonAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class LoginHandler extends ProcessHandler{

	private static String[] keys = { "email", "password_hash" };
	private final String DATABASE_TABLE = "users";
	private final String SUCCESS_MESSAGE = "Login correct.";
	private final String FAIL_MESSAGE = "Login details incorrect.";

	public LoginHandler(Map<String, String[]> params) {
		super(RequestAdapter.convertParameters(params, keys));
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException, ClassNotFoundException, SQLException {
		DatabaseAdapter adapter;
		boolean result;

		adapter = new DatabaseAdapter();
		result = adapter.doesExist(DATABASE_TABLE, params);
		return getJSON(result);

	}

	private String createToken() {
		return UUID.randomUUID().toString();
	}

	private JSONObject getJSON(boolean success) {
		Map<String, Object> values;

		values = new HashMap<String, Object>();

		values.put("success", success);
		values.put("token", "");
		if (success) {
			values.put("message", SUCCESS_MESSAGE);
			values.put("token", createToken());
		} else {
			values.put("message", FAIL_MESSAGE);
		}
		return JsonAdapter.createJSON(values);
	}

}
