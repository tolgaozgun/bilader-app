package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import database.adapters.DatabaseAdapter;
import database.adapters.RequestAdapter;
import jakarta.servlet.ServletException;

public class LoginHandler extends ProcessHandler {

	private static String[] keys = { "email", "password_hash" };
	private final String DATABASE_TABLE = "users";
	private final String SUCCESS_MESSAGE = "Login correct.";
	private final String FAIL_MESSAGE = "Login details incorrect.";

	public LoginHandler(Map<String, String[]> params) {
		super(RequestAdapter.convertParameters(params, keys));
	}

	@Override
	public JSONObject getResult() throws ServletException, IOException, ClassNotFoundException, SQLException {
		JSONObject json;
		DatabaseAdapter adapter;
		boolean result;

		adapter = new DatabaseAdapter();
		json = new JSONObject();

		result = adapter.doesExist(DATABASE_TABLE, params);
		json.put("success", result);
		json.put("token", "");
		if (result) {
			json.put("message", SUCCESS_MESSAGE);
			json.put("token", createToken());
		} else {
			json.put("message", FAIL_MESSAGE);
		}
		return json;

	}

	private String createToken() {
		return UUID.randomUUID().toString();
	}
}
