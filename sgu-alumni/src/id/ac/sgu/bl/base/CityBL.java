package id.ac.sgu.bl.base;

import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.factory.DAOLocator;

public class CityBL extends BaseBL{

	public DAOLocator daoLocator;
	
	public int createCity() {
		
		return 0;
	}
	
	public int deleteCity() {
		
		return 0;
	}
	
	public boolean validateCity() {
		boolean valid = false;
		
		
		
		return valid;
	}
	
	public boolean searchCity( ) {
		boolean valid = false;
		
		
		return valid;
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
