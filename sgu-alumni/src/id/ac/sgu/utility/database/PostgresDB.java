package id.ac.sgu.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class PostgresDB extends DatabaseResources {

	private static Logger logger = Logger.getLogger(PostgresDB.class);

	public PostgresDB() {
	}

	@Override
	public Connection getConnection() {
		return getConnection(driver, url, username, password);
	}

	@Override
	public Connection getConnection(String driver, String url, String username,
			String password) {
		//logger.info("PostgresDB.CreateDatabaseConnection -- enter");

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			isConnected = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("ClassNotFound Exception = ", e);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SqlException = ", e);
		}
//		logger.info("PostgresDB.CreateDatabaseConnection -- exit");

		return connection;
	}

	@Override
	public void closeConnection() throws SQLException {
		connection.close();
		isConnected = false;
	}

	@Override
	public void closeStatement() throws SQLException {
		statement.close();
	}

	@Override
	public void closeResultSet() throws SQLException {
		resultSet.close();
	}

	public int querySize(BoxDB boxDB) throws SQLException {
		int result = 0;

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		statement = connection.createStatement();

		resultSet = statement.executeQuery(boxDB.finalizeSQL(BoxDB.SELECT));

		if (resultSet != null)
			while (resultSet.next())
				result++;

		boxDB.clear();

		return result;
	}

	public ResultSet returnedSingleQuery(BoxDB boxDB) throws SQLException {

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


		resultSet = statement.executeQuery(boxDB.finalizeSQL(BoxDB.SELECT).toString());

		boxDB.clear();

		return resultSet;
	}

	public ResultSet returnedMultipleQuery(BoxDB boxDB) throws SQLException {

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		statement = connection.createStatement();
		resultSet = statement.executeQuery(boxDB.finalizeSQL(BoxDB.SELECT));

		boxDB.clear();

		return resultSet;
	}

	public boolean isResultSetNull() {
		if (resultSet == null)
			return true;

		return false;
	}

	public int insert(BoxDB boxDB) throws SQLException {
		int result = 0;

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		connection.setAutoCommit(false);

		statement = connection.createStatement();

		result = statement.executeUpdate(boxDB.finalizeSQL(BoxDB.INSERT));

		connection.commit();

		boxDB.clear();

		return result;
	}

	public int getLastId(String tableName, String columnIdName) throws SQLException {
		if (!isConnected)
			throw new SQLException("Not yet connected");

		BoxDB boxDB = new BoxDB();
		boxDB.setTable(tableName);
		boxDB.addColumn(columnIdName);

		int id = 0;

		connection.setAutoCommit(false);

		statement = connection.createStatement();

		resultSet = statement.executeQuery(	boxDB.getLastIdFrom());

		if (resultSet.next())
			id = resultSet.getInt(columnIdName);

		connection.commit();

		boxDB.clear();

		return id;

	}

	public int update(BoxDB boxDB) throws SQLException {

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		int result = 0;

		statement = connection.createStatement();

		connection.setAutoCommit(false);
		statement = connection.createStatement();

		result = statement.executeUpdate(boxDB.finalizeSQL(BoxDB.UPDATE));

		connection.commit();

		boxDB.clear();

		return result;
	}

	public void printResourceProperty() {
		logger.info("DB DRIVER = " + this.driver);
		logger.info("DB URL = " + this.url);
		logger.info("DB USERNAME = " + this.username);
		logger.info("DB PASSWORD= " + this.password);
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public int delete(BoxDB boxDB) throws SQLException {

		if (!isConnected)
			throw new SQLException("Not yet connected");

		if (boxDB == null)
			throw new NullPointerException("Null on Box DB");

		int result = 0;

		connection.setAutoCommit(false);
		statement = connection.createStatement();

		result = statement.executeUpdate(boxDB.finalizeSQL(BoxDB.DELETE));

		connection.commit();

		boxDB.clear();

		return result;
	}

}
