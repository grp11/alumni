package id.ac.sgu.utility.database;

import id.ac.sgu.utility.Cons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseResources {

	public static final int ERROR = 1;
	
	public static final int SUCCESS = 0;
	
	public static final int OFFSET = -1;
	
	public static final int LIMIT = 50;

	protected Connection connection = null;
	
	public abstract Connection getConnection();
	
	public abstract Connection getConnection(String driver, String url, String username, String password);
		
	public abstract void closeConnection() throws SQLException;
	
	public abstract void closeResultSet() throws SQLException;
	
	public abstract void closeStatement() throws SQLException;

		
/*	public abstract int insertQuerySingle();
	
	public abstract int insertQueryMultiple();
	
	public abstract int updateQuerySingle();
	
	public abstract int updateQueryMultiple();
	
	public abstract int selectQuerySingle();
	
	public abstract int selectQueryMultiple();
*/	
	
	public abstract void commit() throws SQLException;
	
	protected Statement statement = null;
	
	protected ResultSet resultSet = null;
		
	protected String driver = Cons.DRIVER;
	
	protected String url = Cons.URL;
	
	protected String password = Cons.PASSWORD;
	
	protected String username = Cons.USER_NAME;
	
	protected String dbName = Cons.DB_NAME;

	//protected String methodToConnect = Cons.DB_METHOD_CONNECT;
	
	protected boolean isConnected;
	
	public boolean getIsConnected() {
		return isConnected;
	}
}
