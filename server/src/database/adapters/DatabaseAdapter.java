package database.adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter {

	private Connection connection;
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/bilader_app";
	private final String MYSQL_USERNAME = "biladeradmin";
	private final String MYSQL_PASSWORD = "BILADERapp123#";
	
	private void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
	}

	private void disconnect() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public boolean doesExist(String tableName, Map<String, String> params) throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		int count;

		if(tableName == null | params == null) {
			return false;
		}
		
		count = 0;
		sql = new StringBuffer("SELECT COUNT(*) AS COUNT FROM " + tableName + " ");
		sql.append(createWhere(params));

		connect();
		//out.println(sql.toString());
		statement = connection.prepareStatement(sql.toString());
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
		String keys;
		String values;
		PreparedStatement statement;


		if(tableName == null | params == null) {
			return;
		}
		
		keySet = new StringBuffer("(");
		valueSet = new StringBuffer("(");

		for (String param : params.keySet()) {
			keySet.append(param + ", ");
			valueSet.append("'" + params.get(param) + "', ");
		}

		// removing the unnecessary comma and space.
		keys = keySet.substring(0, keySet.length() - 2);
		values = valueSet.substring(0, valueSet.length() - 2);
		

		// close parentheses
		keys = keys + ")";
		values = values + ")";

		sql = new StringBuffer("INSERT INTO " + tableName + " " + keys + " VALUES " + values);

		connect();
		statement = connection.prepareStatement(sql.toString());
		statement.executeUpdate();
		disconnect();
	}

	private String createWhere(Map<String, String> params) {
		StringBuffer sql;
		String request;
		

		if( params == null) {
			return null;
		}

		sql = new StringBuffer("WHERE (");
		for (String param : params.keySet()) {
			sql.append(param + "='" + params.get(param) + "' AND ");
		}

		if (params.keySet().size() > 0) {
			request = sql.substring(0, sql.length() - 5) + ")";
		} else {
			request = sql.substring(0, sql.length() - 8);
		}
		return request;
	}

	private String createSeperatedString(String[] wanted) {
		StringBuffer buffer;
		buffer = new StringBuffer();

		if (wanted.length == 0) {
			return "*";
		}

		for (String key : wanted) {
			buffer.append("`" + key + "`,");
		}
		return buffer.substring(0, buffer.length() - 1);

	}

	public Map<Integer, Object[]> select(String tableName, String[] wanted, Map<String, String> params)
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		Map<Integer, Object[]> result;
		Object[] iteration;
		int j;

		result = new HashMap<Integer, Object[]>();
		sql = new StringBuffer("SELECT ");
		sql.append(createSeperatedString(wanted) + " ");
		sql.append("FROM " + tableName + " ");
		sql.append(createWhere(params));
		connect();
		statement = connection.prepareStatement(sql.toString());
		resultSet = statement.executeQuery();

		j = 0;
		if (resultSet.next()) {
			iteration = new Object[wanted.length];
			for (int i = 0; i < wanted.length; i++) {
				iteration[i] = resultSet.getObject(wanted[i]);
			}
			result.put(j, iteration);
			j++;
		}

		resultSet.close();
		disconnect();
		
		
		return result;
	}

}
