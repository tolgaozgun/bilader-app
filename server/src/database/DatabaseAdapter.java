package database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseAdapter {

	private Connection connection;
	private PrintWriter out;
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/bilader_app"; 
	private final String MYSQL_USERNAME = "biladeradmin";
	private final String MYSQL_PASSWORD = "BILADERapp123#";
	
	public DatabaseAdapter(PrintWriter out) {
		this.out = out;
	}
	
	
	private void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");  
		connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
	}
	
	private void disconnect() throws SQLException {
		if ( connection != null ) {
			connection.close();
		}
	}
	

	public boolean doesExist(String tableName, Map<String, String> params) throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		String request;
		PreparedStatement statement;
		ResultSet resultSet;
		int count;

		count = 0;
		sql = new StringBuffer("SELECT COUNT(*) AS COUNT FROM " + tableName + " WHERE (");

		for (String param : params.keySet()) {
			sql.append(param + "='" + params.get(param) + "' AND ");
		}

		if(params.keySet().size() > 0) {
			request = sql.substring(0, sql.length() - 5) + ")";
		} else {
			request = sql.substring(0, sql.length() - 8);
		}
		connect();
		statement = connection.prepareStatement(request);
		out.println(request);
		resultSet = statement.executeQuery();

		if (resultSet.next()) {
			count = resultSet.getInt("count");
		}

		resultSet.close();
		disconnect();

		return count != 0;
	}

	public void create(String tableName, Map<String, String> params) throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		StringBuffer keySet;
		StringBuffer valueSet;
		PreparedStatement statement;

		keySet = new StringBuffer("(");
		valueSet = new StringBuffer("(");

		for (String param : params.keySet()) {
			keySet.append(param + ", ");
			valueSet.append(params.get(param) + ", ");
		}
		
		// removing the unnecessary comma and space.
		keySet.substring(0, keySet.length() - 3);
		valueSet.substring(0, valueSet.length() - 3);
		
		// close parentheses 
		keySet.append(")");
		valueSet.append(")");

		sql = new StringBuffer("INSERT INTO " + tableName + " " + keySet + " VALUES " + valueSet);

		connect();
		statement = connection.prepareStatement(sql.toString());
		statement.executeUpdate();
		disconnect();
	}

}
