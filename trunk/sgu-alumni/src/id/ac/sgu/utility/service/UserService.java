package id.ac.sgu.utility.service;

import id.ac.sgu.bl.base.CityBL;
import id.ac.sgu.bl.base.CountryBL;
import id.ac.sgu.bl.base.UserBL;
import id.ac.sgu.dao.base.CityDAO;
import id.ac.sgu.dao.base.CountryDAO;
import id.ac.sgu.dao.base.UserDAO;

public class UserService {

	private CityDAO cityDAO;
	private CityBL cityBL;

	private CountryDAO countryDAO;
	private CountryBL countryBL;

	private UserDAO userDAO;
	private UserBL userBL;

	/**
	 * @return the cityDAO
	 */
	public CityDAO getCityDAO() {
		return cityDAO;
	}

	/**
	 * @param cityDAO the cityDAO to set
	 */
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * @return the cityBL
	 */
	public CityBL getCityBL() {
		return cityBL;
	}

	/**
	 * @param cityBL the cityBL to set
	 */
	public void setCityBL(CityBL cityBL) {
		this.cityBL = cityBL;
	}

	/**
	 * @return the countryDAO
	 */
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	/**
	 * @param countryDAO the countryDAO to set
	 */
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	/**
	 * @return the countryBL
	 */
	public CountryBL getCountryBL() {
		return countryBL;
	}

	/**
	 * @param countryBL the countryBL to set
	 */
	public void setCountryBL(CountryBL countryBL) {
		this.countryBL = countryBL;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @return the userBL
	 */
	public UserBL getUserBL() {
		return userBL;
	}

	/**
	 * @param userBL the userBL to set
	 */
	public void setUserBL(UserBL userBL) {
		this.userBL = userBL;
	}


}
