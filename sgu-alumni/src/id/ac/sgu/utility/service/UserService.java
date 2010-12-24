package id.ac.sgu.utility.service;

import id.ac.sgu.bean.base.CityBean;
import id.ac.sgu.bean.base.CommentBean;
import id.ac.sgu.bean.base.CountryBean;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.bl.base.CityBL;
import id.ac.sgu.bl.base.CommentBL;
import id.ac.sgu.bl.base.CountryBL;
import id.ac.sgu.bl.base.UserBL;
import id.ac.sgu.dao.base.CityDAO;
import id.ac.sgu.dao.base.CommentDAO;
import id.ac.sgu.dao.base.CountryDAO;
import id.ac.sgu.dao.base.UserDAO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class UserService {

	private CityDAO cityDAO;
	private CityBL cityBL;

	private CountryDAO countryDAO;
	private CountryBL countryBL;

	private UserDAO userDAO;
	private UserBL userBL;

	private CommentDAO commentDAO;
	private CommentBL commentBL;

	public Map<String, List<String>> getLocationList()
	{
		Map<String, List<String>> modelMaps = new HashMap<String, List<String>>();

		List<String> countryList = getAllCountriesName(true);

		if (null != countryList)
		{
			while (!countryList.isEmpty())
			{
				String temp = countryList.remove(0);
				modelMaps.put(temp, getAllCitiesName(temp));
				temp = null;
			}
		}

		return modelMaps;
	}

	public List<String> getAllCountriesName(boolean inMapping)
	{
		List<String> countryList = new Vector<String>();

		List<CountryBean> listTemp = findAllCountriesInMapping();


		if (null != listTemp)
		{
			while (!listTemp.isEmpty())
			{
				CountryBean temp = listTemp.remove(0);
				countryList.add(temp.getCountryName());

			}
			Collections.sort(countryList);
		}

		return countryList;
	}

	public List<String> getAllCitiesName(String countryName)
	{
		List<String> cityList = new Vector<String>();

		List<CityBean> listTemp = findAllCitiesInMappingBy(countryName);

		if (listTemp != null)
		{
			while (!listTemp.isEmpty())
			{
				CityBean temp = listTemp.remove(0);
				cityList.add(temp.getCityName());
			}
			Collections.sort(cityList);
		}

		return cityList;
	}

	public List<UserBean> findAllUsers()
	{
		return userDAO.findAll();
	}

	public List<UserBean> findAllUsers(UserBean userBean)
	{
		return userDAO.findAll(userBean);
	}


	public List<CountryBean> findAllCountriesInMapping()
	{
		return countryDAO.findAllInMapping();
	}

	public List<CityBean> findAllCitiesInMapping()
	{
		return cityDAO.findAllInMapping();
	}

	public List<CityBean> findAllCitiesInMappingBy(String countryName)
	{
		return cityDAO.findInMappingBy(countryName);
	}

	public int existingUserName(String username)
	{
		if (username == null)
			throw new NullPointerException("Ops, username cannot be null");

		return userBL.isExistingUsername(username);
	}

	public List<CommentBean> findAllComments()
	{
		return commentDAO.findAll();
	}

	public List<CommentBean> findAllComments(CommentBean commentBean)
	{
		if (commentBean == null)
			throw new NullPointerException("Ops, received null bean on commentbean");

		int toId = userDAO.getBatchMappingIdForComment(String.valueOf(commentBean.getBatchYear()),
				commentBean.getDepartmentName(), commentBean.getFacultyName());

		commentBean.setTo(toId);

		return commentDAO.findAll(commentBean);

	}

	public int createPostNewComment(CommentBean commentBean) throws IllegalAccessException
	{
		int userId = userDAO.checkUsername(commentBean.getFromName());
		if (userId == 0)
			throw new IllegalAccessException("ERROR: userid cannot be found in our database");

		commentBean.setFrom(userId);

		int toId = userDAO.getBatchMappingIdForComment(String.valueOf(commentBean.getBatchYear()),
				commentBean.getDepartmentName(), commentBean.getFacultyName());

		if (toId == 0)
			throw new IllegalAccessException("ERROR: toId cannot be found in our database");

		commentBean.setTo(toId);

		return commentBL.createNew(commentBean);
	}

	public int createNewUser(UserBean userBean)
	{
		if (userBean == null)
			throw new NullPointerException("Received null bean");

		return userBL.createNewUser(userBean);
	}

	public int deleteUser(UserBean userBean)
	{
		if (userBean == null)
			throw new NullPointerException("Received null bean");

		// Convert
		UserBean theBean = userDAO.findBy("username", userBean.getUsername());
		userBean.clear();
		return userBL.deleteUser(theBean);
	}


	public int updateUser(UserBean userBean)
	{
		if (userBean == null)
			throw new NullPointerException("Received null bean");
		return userBL.updateUser(userBean);
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
	 * @return the commentBL
	 */
	public CommentBL getCommentBL() {
		return commentBL;
	}

	/**
	 * @param commentBL the commentBL to set
	 */
	public void setCommentBL(CommentBL commentBL) {
		this.commentBL = commentBL;
	}

}
