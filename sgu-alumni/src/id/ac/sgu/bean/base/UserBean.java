package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

public class UserBean implements IBaseBean, Serializable {

	private static final long serialVersionUID = -7235981319532593559L;

	private int statsUserId;
	private int userid;
	private int profileId;

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
	private String ZIP;

	private int cityId;
	private String cityName;

	private int countryId;
	private String countryName;

	private String email;
	private String telephone;
	private String handphone;

	private String gender;

	//	private String currSession;
	//	private String lastSession;

	public void setStatsUserId(int statsUserId) {
		this.statsUserId = statsUserId;
	}

	public int getStatsUserId() {
		return statsUserId;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}


	public int getProfileId() {
		return profileId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public Date getDOB() {
		return DOB;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getBatchYear() {
		return batchYear;
	}

	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
	}

	public int getBatchMappingId() {
		return batchMappingId;
	}

	public void setBatchMappingId(int batchMappingId) {
		this.batchMappingId = batchMappingId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setMainpics(String mainpics) {
		this.mainpics = mainpics;
	}

	public String getMainpics() {
		return mainpics;
	}


	public UserBean() {
	}


	public void copyProperties(UserBean src)
	{
		setUserid(src.getUserid());
		setStatsUserId(src.getStatsUserId());
		setProfileId(src.getProfileId());

		setFirstName(src.getFirstName());
		setLastName(src.getLastName());

		setUsername(src.getUsername());
		setRepeatPassword(src.getRepeatPassword());

		setRoleId(src.getRoleId());
		setRoleName(src.getRoleName());

		setCityId(src.getCityId());
		setCityName(src.getCityName());

		setCountryId(src.getCountryId());
		setCountryName(src.getCountryName());

		setDepartmentId(src.getDepartmentId());
		setDepartmentName(src.getDepartmentName());

		setEmail(src.getEmail());
		setDOB(src.getDOB());

		setFacultyId(src.getFacultyId());
		setFacultyName(src.getFacultyName());

		setBatchId(src.getBatchId());
		setBatchYear(src.getBatchYear());
		setBatchMappingId(src.getBatchMappingId());

		setGender(src.getGender());

		setHandphone(src.getHandphone());
		setTelephone(src.getTelephone());

		setModified(src.getModified());
		setLastLogin(src.getLastLogin());

		setAddress(src.getAddress());
		setZIP(src.getZIP());

	}

	@Override
	public void clear() {
		this.setStatsUserId(0);
		this.userid = 0;
		this.address = null;
		this.password = null;
		this.repeatPassword = null;
		this.profileId = 0;
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
		this.ZIP = null;
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
		this.gender = null;
		this.salt = null;
		this.DOB = null;
	}

}
