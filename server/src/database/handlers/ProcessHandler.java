package database.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import jakarta.servlet.ServletException;

public abstract class ProcessHandler {
	
	protected Map<String, String> params;
	
	public ProcessHandler(Map<String, String> params) {
		this.params = params;
	}
	
	public abstract JSONObject getResult() throws ServletException, IOException, ClassNotFoundException, SQLException;

}
