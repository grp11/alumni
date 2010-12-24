package id.ac.sgu.dao;

import id.ac.sgu.base.BaseApplication;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T>
{

	protected PostgresDB dbConnect = BaseApplication.dbConnect;

	protected ResultSet res;

	protected abstract List<T> findAll();

	protected abstract int CreateNew(T t);

	protected abstract T findBy(String key, String val);

	protected abstract int updateRecent(T t);

	protected abstract int deleteRecent(T t);

	protected BoxDB boxDB;

	public BaseDAO()
	{
	}

	public PostgresDB getDbConnect()
	{
		return dbConnect;
	}

	public void setDbConnect(PostgresDB dbConnect)
	{
		this.dbConnect = dbConnect;
	}

	public BoxDB getBoxDB()
	{
		return boxDB;
	}

	public void setBoxDB(BoxDB boxDB)
	{
		this.boxDB = boxDB;
	}

	public void closeResultSet() throws SQLException
	{
		res.close();
	}

}
