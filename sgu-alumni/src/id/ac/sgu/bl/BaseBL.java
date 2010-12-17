package id.ac.sgu.bl;

import id.ac.sgu.base.BaseApplication;
import id.ac.sgu.utility.database.PostgresDB;
import id.ac.sgu.utility.factory.DAOLocator;

public abstract class BaseBL {
	
	protected PostgresDB dbConnect = BaseApplication.dbConnect;
	
}
