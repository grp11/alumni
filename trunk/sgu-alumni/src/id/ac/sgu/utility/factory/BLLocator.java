package id.ac.sgu.utility.factory;

import id.ac.sgu.bl.base.BatchBL;
import id.ac.sgu.bl.base.CityBL;
import id.ac.sgu.bl.base.CommentBL;
import id.ac.sgu.bl.base.CountryBL;
import id.ac.sgu.bl.base.DepartmentBL;
import id.ac.sgu.bl.base.FacultyBL;
import id.ac.sgu.bl.base.UserBL;
import id.ac.sgu.bl.login.LoginBL;

public class BLLocator {
	
	private BatchBL batchBL;
	private CommentBL commentBL;
	private CityBL cityBL;
	private CountryBL countryBL;
	private DepartmentBL departmentBL;
	private FacultyBL facultyBL;
	private UserBL userBL;
	private LoginBL loginBL;
	
	public BLLocator(){
	}

	/**
	 * @return the batchBL
	 */
	public BatchBL getBatchBL() {
		return batchBL;
	}

	/**
	 * @param batchBL the batchBL to set
	 */
	public void setBatchBL(BatchBL batchBL) {
		this.batchBL = batchBL;
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
	 * @return the departmentBL
	 */
	public DepartmentBL getDepartmentBL() {
		return departmentBL;
	}

	/**
	 * @param departmentBL the departmentBL to set
	 */
	public void setDepartmentBL(DepartmentBL departmentBL) {
		this.departmentBL = departmentBL;
	}

	/**
	 * @return the facultyBL
	 */
	public FacultyBL getFacultyBL() {
		return facultyBL;
	}

	/**
	 * @param facultyBL the facultyBL to set
	 */
	public void setFacultyBL(FacultyBL facultyBL) {
		this.facultyBL = facultyBL;
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
	 * @return the loginBL
	 */
	public LoginBL getLoginBL() {
		return loginBL;
	}

	/**
	 * @param loginBL the loginBL to set
	 */
	public void setLoginBL(LoginBL loginBL) {
		this.loginBL = loginBL;
	}
	

}
