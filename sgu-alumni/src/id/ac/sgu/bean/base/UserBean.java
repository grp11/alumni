package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

public class UserBean implements IBaseBean, Serializable {

	private static final long serialVersionUID = -7235981319532593559L;

	private int userid;	
	
	private String mainpics;
	
	private String username;
	private String firstName;
	private String lastName;

	private String password;
	private String repeatPassword;
	
	private String salt;
	private Date DOB;
	
	private int roleId;
	private String roleName;
	
	private int facultyId;
	private String facultyName;
	
	private int departmentId;
	private String departmentName;
	
	private int batchId;
	private int batchYear;
	private int batchMappingId; 
	
	private Date lastLogin;
	private Date created;
	private Date modified;
	
	private String oldPass;
	private String newPass;
	
	private String address;
	private int ZIP;
	
	private int cityId;
	private String cityName;
	
	private int countryId;
	private String countryName;
	
	private String email;
	private String telephone;
	private String handphone;
	
	private int gender;
	
	private String currSession;
	private String lastSession;
	
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}


	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param repeatPassword the repeatPassword to set
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}


	/**
	 * @return the repeatPassword
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}


	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}


	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}


	/**
	 * @param dOB the dOB to set
	 */
	public void setDOB(Date dOB) {
		DOB = dOB;
	}


	/**
	 * @return the dOB
	 */
	public Date getDOB() {
		return DOB;
	}


	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	/**
	 * @return the facultyId
	 */
	public int getFacultyId() {
		return facultyId;
	}


	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}


	/**
	 * @return the facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}


	/**
	 * @param facultyName the facultyName to set
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}


	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}


	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}


	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}


	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	/**
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}


	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}


	/**
	 * @return the batchYear
	 */
	public int getBatchYear() {
		return batchYear;
	}


	/**
	 * @param batchYear the batchYear to set
	 */
	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
	}


	/**
	 * @return the batchMappingId
	 */
	public int getBatchMappingId() {
		return batchMappingId;
	}


	/**
	 * @param batchMappingId the batchMappingId to set
	 */
	public void setBatchMappingId(int batchMappingId) {
		this.batchMappingId = batchMappingId;
	}


	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}


	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}


	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}


	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}


	/**
	 * @return the oldPass
	 */
	public String getOldPass() {
		return oldPass;
	}


	/**
	 * @param oldPass the oldPass to set
	 */
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}


	/**
	 * @return the newPass
	 */
	public String getNewPass() {
		return newPass;
	}


	/**
	 * @param newPass the newPass to set
	 */
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}


	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}


	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}


	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 * @return the handphone
	 */
	public String getHandphone() {
		return handphone;
	}


	/**
	 * @param handphone the handphone to set
	 */
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the zIP
	 */
	public int getZIP() {
		return ZIP;
	}


	/**
	 * @param zIP the zIP to set
	 */
	public void setZIP(int zIP) {
		ZIP = zIP;
	}


	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}


	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}


	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}


	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}


	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}


	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	/**
	 * @param userGender the userGender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}


	/**
	 * @return the userGender
	 */
	public int getGender() {
		return gender;
	}


	/**
	 * @param mainpics the mainpics to set
	 */
	public void setMainpics(String mainpics) {
		this.mainpics = mainpics;
	}


	/**
	 * @return the mainpics
	 */
	public String getMainpics() {
		return mainpics;
	}


	public UserBean() {
	}


	@Override
	public void clear() {
		this.userid = 0;
		this.address = null;
		this.password = null;
		this.repeatPassword = null;

		this.batchId = 0;
		this.batchMappingId = 0;
		this.batchYear = 0;
		this.cityId = 0;
		this.cityName = null;
		this.countryId = 0;
		this.countryName = null;
		this.departmentId = 0;
		this.departmentName = null;
		this.email = null;
		this.facultyId = 0;
		this.facultyName = null;
		this.username = null;
		this.ZIP = 0;
		this.roleId = 0;
		this.roleName = null;
		this.facultyId = 0;
		this.facultyName = null;
		this.batchId = 0;
		this.batchYear = 0;
		this.telephone = null;
		this.handphone = null;		
		this.modified = null;
		this.lastLogin = null;
		this.created = null;
		this.password = null;
		this.salt = null;
		this.DOB = null;
	}
	
		
}
