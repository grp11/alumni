package id.ac.sgu.bl.base;

import id.ac.sgu.utility.factory.DAOLocator;

public class CountryBL {

	private DAOLocator daoLocator;

	public int createCountry() {
		int result = 0;
		
		return result;
	}
	
	public int deleteCountry() {
		int result = 0;
		
		return result;
	}
	
	public boolean validateCountry() {
		boolean valid = false;
		
		
		return valid;
	}
	
	/**
	 * @param daoLocator the daoLocator to set
	 */
	public void setDaoLocator(DAOLocator daoLocator) {
		this.daoLocator = daoLocator;
	}

	/**
	 * @return the daoLocator
	 */
	public DAOLocator getDaoLocator() {
		return daoLocator;
	}
	
}
