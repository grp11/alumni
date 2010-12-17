package id.ac.sgu.utility.factory;

import id.ac.sgu.dao.base.BatchDAO;
import id.ac.sgu.dao.base.CityDAO;
import id.ac.sgu.dao.base.CommentDAO;
import id.ac.sgu.dao.base.CountryDAO;
import id.ac.sgu.dao.base.DepartmentDAO;
import id.ac.sgu.dao.base.FacultyDAO;
import id.ac.sgu.dao.base.UserDAO;
import id.ac.sgu.dao.login.LoginDAO;

public class DAOLocator {

	private DepartmentDAO departmentDAO;
	private FacultyDAO facultyDAO;
	private BatchDAO batchDAO;
	private CityDAO cityDAO;
	private CountryDAO countryDAO;
	private CommentDAO commentDAO;
	private UserDAO userDAO;
	private LoginDAO loginDAO;
//	private UserRoleDAO userRoleDAO;
	
	public DAOLocator() {
	}

	/**
	 * @return the departmentDAO
	 */
	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	/**
	 * @param departmentDAO the departmentDAO to set
	 */
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * @return the facultyDAO
	 */
	public FacultyDAO getFacultyDAO() {
		return facultyDAO;
	}

	/**
	 * @param facultyDAO the facultyDAO to set
	 */
	public void setFacultyDAO(FacultyDAO facultyDAO) {
		this.facultyDAO = facultyDAO;
	}

	/**
	 * @return the batchDAO
	 */
	public BatchDAO getBatchDAO() {
		return batchDAO;
	}

	/**
	 * @param batchDAO the batchDAO to set
	 */
	public void setBatchDAO(BatchDAO batchDAO) {
		this.batchDAO = batchDAO;
	}

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
	 * @return the commentDAO
	 */
	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	/**
	 * @param commentDAO the commentDAO to set
	 */
	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
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
	 * @return the loginDAO
	 */
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	/**
	 * @param loginDAO the loginDAO to set
	 */
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

}
