package database.adapters;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * An adapter class that maintains the connection between MySQL Server and this
 * JSP Server.
 * 
 * @author Tolga Özgün
 * 
 *
 */

public class DatabaseAdapter {

	private Connection connection;
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/bilader_app";
	private final String MYSQL_USERNAME = "biladeradmin";
	private final String MYSQL_PASSWORD = "BILADERapp123#";

	/**
	 * Connects to the MySQL Server using credentials.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void connect() throws SQLException, ClassNotFoundException {
		Class.forName( "com.mysql.jdbc.Driver" );
		connection = DriverManager.getConnection( MYSQL_URL, MYSQL_USERNAME,
				MYSQL_PASSWORD );
	}

	/**
	 * Ends the connection with MySQL Server if exists.
	 * 
	 * @throws SQLException
	 */
	private void disconnect() throws SQLException {
		if ( connection != null ) {
			connection.close();
		}
	}

	public boolean doesExist( String tableName, Map< String, String > params )
			throws SQLException, ClassNotFoundException {
		return doesExist( tableName, params, "AND" );
	}

	public boolean doesExist( String tableName, Map< String, String > params,
			String additionParam ) throws SQLException, ClassNotFoundException {
		return doesExist( tableName, params, additionParam, "AND", null, null );
	}

	public boolean doesExist( String tableName, Map< String, String > params,
			String additionParam, Map< String, String > anotherParams,
			String secondAddition )
			throws ClassNotFoundException, SQLException {
		return doesExist( tableName, params, additionParam, "AND",
				anotherParams, secondAddition );

	}

	public boolean doesExist( String tableName, Map< String, String > params,
			String additionParam, String connectionParam,
			Map< String, String > anotherParams, String secondAddition )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		String secondWhere;
		int count;

		if ( tableName == null ) {
			return false;
		}

		count = 0;
		sql = new StringBuffer(
				"SELECT COUNT(*) AS COUNT FROM " + tableName + " " );
		sql.append( createWhereString( params, additionParam ) + " " );
		if ( anotherParams != null && secondAddition != null ) {
			secondWhere = createWhereString( anotherParams, secondAddition );
			secondWhere = secondWhere.replaceAll( "WHERE", connectionParam );
			sql.append( secondWhere );
		}
		connect();
		statement = connection.prepareStatement( sql.toString() );
		resultSet = statement.executeQuery();

		if ( resultSet.next() ) {
			count = resultSet.getInt( "count" );
		}

		resultSet.close();
		disconnect();

		return count != 0;
	}

	public int count( String tableName, Map< String, String > params )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		int count;

		if ( tableName == null ) {
			return 0;
		}

		count = 0;
		sql = new StringBuffer(
				"SELECT COUNT(*) AS COUNT FROM " + tableName + " " );
		sql.append( createWhereString( params, "AND" ) );
		connect();
		statement = connection.prepareStatement( sql.toString() );
		resultSet = statement.executeQuery();

		if ( resultSet.next() ) {
			count = resultSet.getInt( "count" );
		}

		resultSet.close();
		disconnect();

		return count;
	}

	/**
	 * Update the rows which contain the given parameters, in the provided table
	 * with the new parameters in the MySQL Server.
	 * 
	 * In other words, updates the data with given values to a new map of
	 * values.
	 * 
	 * @param tableName  String value of table name.
	 * @param params     String of column names mapped with their values.
	 * @param updateList String of column names mapped with their new values.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void update( String tableName, Map< String, String > params,
			Map< String, Object > updateList )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;

		sql = new StringBuffer( "UPDATE " + tableName + " " );
		sql.append( createSetString( updateList ) + " " );
		sql.append( createWhereString( params ) );
		connect();
		statement = connection.prepareStatement( sql.toString() );
		statement.executeUpdate();
		disconnect();

	}

	/**
	 * Delete rows with matching values in the MySQL Server.
	 * 
	 * @param tableName String value of table name.
	 * @param params    String of column names mapped with their values.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void delete( String tableName, Map< String, String > params )
			throws ClassNotFoundException, SQLException {
		StringBuffer sql;
		PreparedStatement statement;

		sql = new StringBuffer( "DELETE FROM " + tableName + " " );
		sql.append( createWhereString( params ) );
		connect();
		statement = connection.prepareStatement( sql.toString() );
		statement.executeUpdate();
		disconnect();

	}

	/**
	 * Creates the String with the "SET" syntax for SQL requests. Uses given Map
	 * for appending the values.
	 * 
	 * @param wantedList String of column names mapped with their values.
	 * @return
	 */
	private String createSetString( Map< String, Object > wantedList ) {
		StringBuffer bufferString;

		if ( wantedList == null || wantedList.size() == 0 ) {
			return "";
		}

		bufferString = new StringBuffer( "SET " );
		for ( String key : wantedList.keySet() ) {
			bufferString.append( key + "='" + wantedList.get( key ) + "'," );
		}
		bufferString = bufferString.deleteCharAt( bufferString.length() - 1 );
		return bufferString.toString();

	}

	/**
	 * Creates the String with the "SELECT" syntax for SQL requests. Uses given
	 * array for appending the values.
	 * 
	 * @param wanted String array of values.
	 * @return
	 */
	private String createSelectString( String[] wanted ) {
		StringBuffer buffer;
		buffer = new StringBuffer( "SELECT " );

		if ( wanted == null || wanted.length == 0 ) {
			return "*";
		}

		for ( String key : wanted ) {
			buffer.append( "`" + key + "`," );
		}
		return buffer.substring( 0, buffer.length() - 1 );

	}

	private String createWhereString( Map< String, String > params ) {
		return createWhereString( params, null, null, "AND" );
	}

	private String createWhereString( Map< String, String > params,
			String additionParam ) {
		return createWhereString( params, null, null, additionParam );
	}

	private String createWhereString( Map< String, String > params,
			Map< String, String > compare, CompareType type ) {
		return createWhereString( params, compare, type, "AND" );
	}

	private String createWhereString( Map< String, String > params,
			Map< String, String > compare, CompareType type,
			String additionParam ) {
		StringBuffer sql;
		String request;

		if ( params == null || params.size() == 0 ) {
			return "";
		}

		sql = new StringBuffer( "WHERE (" );
		for ( String param : params.keySet() ) {
			sql.append( param + "='" + params.get( param ) + "' "
					+ additionParam + " " );
		}

		if ( compare != null && type != null ) {
			for ( String param : compare.keySet() ) {
				sql.append( param + type.getOperator() + "'"
						+ compare.get( param ) + "' " + additionParam + " " );
			}
		}

		if ( params.keySet().size() > 0
				|| ( compare != null && compare.keySet().size() > 0 ) ) {
			request = sql.substring( 0,
					sql.length() - ( 2 + additionParam.length() ) ) + ")";
		} else {
			request = sql.substring( 0,
					sql.length() - ( 4 + additionParam.length() ) );
		}
		return request;
	}

	/**
	 * Creates a new row in the MySQL database in the given table name, using
	 * the values provided.
	 * 
	 * @param tableName String value of table name.
	 * @param params    String of column names mapped with their values.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void create( String tableName, Map< String, String > params )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		StringBuffer keySet;
		StringBuffer valueSet;
		String keys;
		String values;
		PreparedStatement statement;

		if ( tableName == null | params == null ) {
			return;
		}

		keySet = new StringBuffer( "(" );
		valueSet = new StringBuffer( "(" );

		for ( String param : params.keySet() ) {
			keySet.append( param + ", " );
			valueSet.append( "'" + params.get( param ) + "', " );
		}

		// removing the unnecessary comma and space.
		keys = keySet.substring( 0, keySet.length() - 2 );
		values = valueSet.substring( 0, valueSet.length() - 2 );

		// close parentheses
		keys = keys + ")";
		values = values + ")";

		sql = new StringBuffer(
				"INSERT INTO " + tableName + " " + keys + " VALUES " + values );

		// if ( overwrite ) {
		// sql.append( "OVERWRITE " );
		// }
		connect();
		statement = connection.prepareStatement( sql.toString() );
		statement.executeUpdate();
		disconnect();
	}

	public Map< Integer, Object[] > select( String tableName, String[] wanted,
			Map< String, String > params )
			throws SQLException, ClassNotFoundException {
		return select( tableName, wanted, params, "AND" );
	}

	public Map< Integer, Object[] > select( String tableName, String[] wanted,
			Map< String, String > params, String additionParam )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		Map< Integer, Object[] > result;
		Object[] iteration;
		int j;

		result = new HashMap< Integer, Object[] >();
		sql = new StringBuffer();
		sql.append( createSelectString( wanted ) + " " );
		sql.append( "FROM " + tableName + " " );
		sql.append( createWhereString( params, additionParam ) );
		connect();
		statement = connection.prepareStatement( sql.toString() );
		resultSet = statement.executeQuery();
		j = 0;
		while ( resultSet.next() ) {
			iteration = new Object[ wanted.length ];
			for ( int i = 0; i < wanted.length; i++ ) {
				iteration[ i ] = resultSet.getObject( wanted[ i ] );
			}
			result.put( j, iteration );
			j++;
		}

		resultSet.close();
		disconnect();

		return result;
	}

	public Map< Integer, Object[] > select( String tableName, String[] wanted,
			Map< String, String > params, Map< String, String > compare,
			CompareType type ) throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		Map< Integer, Object[] > result;
		Object[] iteration;
		int j;

		result = new HashMap< Integer, Object[] >();
		sql = new StringBuffer();
		sql.append( createSelectString( wanted ) + " " );
		sql.append( "FROM " + tableName + " " );
		sql.append( createWhereString( params, compare, type ) );
		connect();
		statement = connection.prepareStatement( sql.toString() );
		resultSet = statement.executeQuery();
		j = 0;
		while ( resultSet.next() ) {
			iteration = new Object[ wanted.length ];
			for ( int i = 0; i < wanted.length; i++ ) {
				iteration[ i ] = resultSet.getObject( wanted[ i ] );
			}
			result.put( j, iteration );
			j++;
		}

		resultSet.close();
		disconnect();

		return result;
	}

	public Map< Integer, Object[] > select( String tableName, String[] wanted,
			Map< String, String > params, String additionParam,
			String connectionParam, Map< String, String > anotherParams,
			String secondAddition )
			throws SQLException, ClassNotFoundException {
		StringBuffer sql;
		PreparedStatement statement;
		ResultSet resultSet;
		Map< Integer, Object[] > result;
		String secondWhere;
		Object[] iteration;
		int j;

		result = new HashMap< Integer, Object[] >();
		sql = new StringBuffer();
		sql.append( createSelectString( wanted ) + " " );
		sql.append( "FROM " + tableName + " " );
		sql.append( createWhereString( params, additionParam ) + " " );
		if ( anotherParams != null && secondAddition != null ) {
			secondWhere = createWhereString( anotherParams, secondAddition );
			secondWhere = secondWhere.replaceAll( "WHERE", connectionParam );
			sql.append( secondWhere );
		}
		connect();
		statement = connection.prepareStatement( sql.toString() );
		resultSet = statement.executeQuery();
		j = 0;
		while ( resultSet.next() ) {
			iteration = new Object[ wanted.length ];
			for ( int i = 0; i < wanted.length; i++ ) {
				iteration[ i ] = resultSet.getObject( wanted[ i ] );
			}
			result.put( j, iteration );
			j++;
		}

		resultSet.close();
		disconnect();

		return result;
	}

}
