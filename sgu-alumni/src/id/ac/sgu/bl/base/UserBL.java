package id.ac.sgu.bl.base;

import org.apache.log4j.Logger;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

public class UserBL extends BaseBL {

	public DAOLocator daoLocator;

	private static Logger logger = Logger.getLogger(UserBL.class);

	public int isExistingUsername(String username)
	{
		int result = daoLocator.getUserDAO().checkUsername(username);
	//	logger.info("result in existing username: " + result);
		return (result != 0) ? Cons.IS_EXIST : 	Cons.NOT_EXIST;
	}

	public int createNewUser(UserBean bean)
	{
		int result = daoLocator.getUserDAO().CreateNew(bean);
		return (result == 3) ? Cons.CREATE_USR_SUCCESS: Cons.CREATE_USR_FAILURE;
	}

	public int deleteUser(UserBean bean)
	{
		int result = daoLocator.getUserDAO().deleteRecent(bean);
		return (result == 3) ? Cons.DELETE_USR_SUCCESS : Cons.DELETE_USR_FAILURE;
	}

	public int updateUser(UserBean bean)
	{
		int result = daoLocator.getUserDAO().updateRecent(bean);
		return (result != 0) ? Cons.UPDATE_USR_SUCCESS : Cons.UPDATE_USR_FAILURE;
	}

	/**
	 * @return the daoLocator
	 */
	public DAOLocator getDaoLocator() {
		return daoLocator;
	}

	/**
	 * @param daoLocator the daoLocator to set
	 */
	public void setDaoLocator(DAOLocator daoLocator) {
		this.daoLocator = daoLocator;
	}

}
