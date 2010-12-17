package id.ac.sgu.bl.login;

import id.ac.sgu.bl.BaseLoginBL;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

public class LoginBL implements BaseLoginBL {

	public DAOLocator daoLocator;
	
	public int signin(String username, String password) {
		int result = Cons.LOGIN_FAILED;
		
		if (daoLocator.getLoginDAO().userVerify(username, password))
			result = Cons.LOGIN_SUCCESS;
		
		return result;
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
