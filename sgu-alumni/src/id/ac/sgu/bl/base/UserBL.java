package id.ac.sgu.bl.base;

import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.factory.DAOLocator;

public class UserBL extends BaseBL {

	public DAOLocator daoLocator;
	
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
