package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;
import id.ac.sgu.utility.security.HashValues;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

public class UserDAO extends BaseDAO<UserBean> {

	private static Logger logger = Logger.getLogger(UserDAO.class);

	public UserDAO(){}

	protected int profileId = 0;
	protected int userId = 0;

	/**
	 * Selective selects
	 *
	 * */
	@Override
	public List<UserBean> findAll() {

		Vector<UserBean> vUser = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		//	boxDB.addColumn("statsuserid");
		//	boxDB.addColumn("statsprofileid");
		//	boxDB.addColumn("usercurrentsession");
		//	boxDB.addColumn("userlastsession");
		//	boxDB.addColumn("statscreted");
		//	boxDB.addColumn("statscreatedby");
		//	boxDB.addColumn("statsmodified");
		//	boxDB.addColumn("statsmodifiedby");

		boxDB.addColumn("userid");
		boxDB.addColumn("username");
		boxDB.addColumn("userpass");

		//	boxDB.addColumn("authsalt");
		boxDB.addColumn("authlastlogin");
		boxDB.addColumn("authroleid");

		boxDB.addColumn("authrolename");

		boxDB.addColumn("authcreated");
		//	boxDB.addColumn("authcreatedby");
		boxDB.addColumn("authmodified");
		//	boxDB.addColumn("authmodifiedby");

		//	boxDB.addColumn("profileid");
		boxDB.addColumn("userfirstname");
		boxDB.addColumn("userlastname");
		boxDB.addColumn("userdob");
		boxDB.addColumn("useraddress");
		boxDB.addColumn("usergender");
		boxDB.addColumn("userphone");
		boxDB.addColumn("userhandphone");

		boxDB.addColumn("useremail");
		boxDB.addColumn("userzip");
		boxDB.addColumn("usercityid");
		boxDB.addColumn("userbatchmappingid");
		boxDB.addColumn("usermainpics");

		//	boxDB.addColumn("profilecreated");
		//	boxDB.addColumn("profilecreatedby");
		//	boxDB.addColumn("profilemodified");
		//	boxDB.addColumn("profilemodifiedby");

		boxDB.addColumn("cityid");
		boxDB.addColumn("cityname");
		//	boxDB.addColumn("citycreated");
		//	boxDB.addColumn("citycreatedby");

		boxDB.addColumn("countryid");
		boxDB.addColumn("countryname");
		//	boxDB.addColumn("countrycreated");
		//	boxDB.addColumn("countrycreatedby");
		//	boxDB.addColumn("countrymodified");
		//	boxDB.addColumn("countrymodifiedby");

		boxDB.addColumn("facultyid");
		boxDB.addColumn("facultyname");
		boxDB.addColumn("facultyalias");
		//	boxDB.addColumn("facultycreated");
		//	boxDB.addColumn("facultycreatedby");
		//	boxDB.addColumn("facultymodified");
		//	boxDB.addColumn("facultymodifiedby");

		boxDB.addColumn("departmentid");
		boxDB.addColumn("departmentname");
		boxDB.addColumn("departmentalias");
		//	boxDB.addColumn("departmentcreated");
		//	boxDB.addColumn("departmentcreatedby");
		//	boxDB.addColumn("departmentmodified");
		//	boxDB.addColumn("departmentmodifiedby");

		boxDB.addColumn("batchid");
		boxDB.addColumn("batchyear");
		//	boxDB.addColumn("batchcreated");
		//	boxDB.addColumn("batchcreatedby");
		//	boxDB.addColumn("batchmodified");
		//	boxDB.addColumn("batchmodifiedby");
		boxDB.addColumn("batchmappingid");

		// Only regular user
		boxDB.addCondition(0);
		boxDB.addCondition("authroleid", BoxDB.EQUALS, "1");

		try {

			res = pdb.returnedMultipleQuery(boxDB);

			vUser = new Vector<UserBean>(3);

			while (res.next())
			{
				UserBean userBean = new UserBean();

				userBean.setUserid(res.getInt("userid"));

				userBean.setUsername(res.getString("username"));

				userBean.setFirstName(res.getString("userfirstname"));
				userBean.setLastName(res.getString("userlastname"));
			//	userBean.setLastLogin(res.getString("authlastlogin"));

				userBean.setFacultyId(res.getInt("facultyid"));
				userBean.setFacultyName(res.getString("facultyname"));

				userBean.setDepartmentId(res.getInt("departmentid"));
				userBean.setDepartmentName(res.getString("departmentname"));

				userBean.setModified(res.getDate("authmodified"));
				userBean.setCreated(res.getDate("authcreated"));

				userBean.setDOB(res.getDate("userdob"));

				userBean.setRoleId(res.getInt("authroleid"));
				userBean.setRoleName(res.getString("authrolename"));

				userBean.setCityId(res.getInt("cityid"));
				userBean.setCityName(res.getString("cityname"));
				userBean.setCountryId(res.getInt("countryid"));
				userBean.setCountryName(res.getString("countryname"));

				userBean.setBatchId(res.getInt("batchid"));
				userBean.setBatchYear(res.getInt("batchyear"));
				userBean.setBatchMappingId(res.getInt("batchmappingid"));

				userBean.setGender(res.getString("usergender"));

				userBean.setAddress(res.getString("useraddress"));
				userBean.setZIP(res.getString("userzip"));

				userBean.setTelephone(res.getString("userphone"));
				userBean.setHandphone(res.getString("userhandphone"));
				userBean.setEmail(res.getString("useremail"));

				userBean.setMainpics(res.getString("usermainpics"));

				vUser.addElement(userBean);
			}

			vUser.trimToSize();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				boxDB.clear();
				if (!pdb.isClosedStatement())
					pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vUser;
	}

	public List<UserBean> findAll(UserBean bean)
	{
		if (bean == null)
			throw new NullPointerException("Received null parameter on findAll userbean");

		Vector<UserBean> vUser = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		//	boxDB.addColumn("statsuserid");
		//	boxDB.addColumn("statsprofileid");
		//	boxDB.addColumn("usercurrentsession");
		//	boxDB.addColumn("userlastsession");
		//	boxDB.addColumn("statscreted");
		//	boxDB.addColumn("statscreatedby");
		//	boxDB.addColumn("statsmodified");
		//	boxDB.addColumn("statsmodifiedby");

		boxDB.addColumn("userid");
		boxDB.addColumn("username");
		boxDB.addColumn("userpass");

		//	boxDB.addColumn("authsalt");
		boxDB.addColumn("authlastlogin");
		boxDB.addColumn("authroleid");

		boxDB.addColumn("authrolename");

		boxDB.addColumn("authcreated");
		//	boxDB.addColumn("authcreatedby");
		boxDB.addColumn("authmodified");
		//	boxDB.addColumn("authmodifiedby");

		//	boxDB.addColumn("profileid");
		boxDB.addColumn("userfirstname");
		boxDB.addColumn("userlastname");
		boxDB.addColumn("userdob");
		boxDB.addColumn("useraddress");
		boxDB.addColumn("usergender");
		boxDB.addColumn("userphone");
		boxDB.addColumn("userhandphone");

		boxDB.addColumn("useremail");
		boxDB.addColumn("userzip");
		boxDB.addColumn("usercityid");
		boxDB.addColumn("userbatchmappingid");
		boxDB.addColumn("usermainpics");

		//	boxDB.addColumn("profilecreated");
		//	boxDB.addColumn("profilecreatedby");
		//	boxDB.addColumn("profilemodified");
		//	boxDB.addColumn("profilemodifiedby");

		boxDB.addColumn("cityid");
		boxDB.addColumn("cityname");
		//	boxDB.addColumn("citycreated");
		//	boxDB.addColumn("citycreatedby");

		boxDB.addColumn("countryid");
		boxDB.addColumn("countryname");
		//	boxDB.addColumn("countrycreated");
		//	boxDB.addColumn("countrycreatedby");
		//	boxDB.addColumn("countrymodified");
		//	boxDB.addColumn("countrymodifiedby");

		boxDB.addColumn("facultyid");
		boxDB.addColumn("facultyname");
		boxDB.addColumn("facultyalias");
		//	boxDB.addColumn("facultycreated");
		//	boxDB.addColumn("facultycreatedby");
		//	boxDB.addColumn("facultymodified");
		//	boxDB.addColumn("facultymodifiedby");

		boxDB.addColumn("departmentid");
		boxDB.addColumn("departmentname");
		boxDB.addColumn("departmentalias");
		//	boxDB.addColumn("departmentcreated");
		//	boxDB.addColumn("departmentcreatedby");
		//	boxDB.addColumn("departmentmodified");
		//	boxDB.addColumn("departmentmodifiedby");

		boxDB.addColumn("batchid");
		boxDB.addColumn("batchyear");
		//	boxDB.addColumn("batchcreated");
		//	boxDB.addColumn("batchcreatedby");
		//	boxDB.addColumn("batchmodified");
		//	boxDB.addColumn("batchmodifiedby");
		boxDB.addColumn("batchmappingid");

		boxDB.addCondition(0);

		int counter = 0;

		if (bean.getDepartmentName() != null)
		{
			boxDB.addCondition("departmentname", BoxDB.EQUALS, bean.getDepartmentName());
			counter = counter + 1;
		}

		if (bean.getBatchYear() != 0)
		{
			if (counter != 0)
				boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("batchyear", BoxDB.EQUALS, String.valueOf(bean.getBatchYear()));
			counter = counter + 1;
		}

		if (bean.getFacultyName() != null)
		{
			if (counter != 0)
				boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("facultyname", BoxDB.EQUALS, bean.getFacultyName());
			counter = counter + 1;
		}

		try
		{
			if (counter != 0)
			{
				res = pdb.returnedMultipleQuery(boxDB);

				vUser = new Vector<UserBean>(3);

				while (res.next())
				{
					UserBean userBean = new UserBean();

					userBean.setUserid(res.getInt("userid"));

					userBean.setUsername(res.getString("username"));

					userBean.setFirstName(res.getString("userfirstname"));
					userBean.setLastName(res.getString("userlastname"));
					//	userBean.setLastLogin(res.getString("authlastlogin"));

					userBean.setFacultyId(res.getInt("facultyid"));
					userBean.setFacultyName(res.getString("facultyname"));

					userBean.setDepartmentId(res.getInt("departmentid"));
					userBean.setDepartmentName(res.getString("departmentname"));

					userBean.setModified(res.getDate("authmodified"));
					userBean.setCreated(res.getDate("authcreated"));

					userBean.setDOB(res.getDate("userdob"));

					userBean.setRoleId(res.getInt("authroleid"));
					userBean.setRoleName(res.getString("authrolename"));

					userBean.setCityId(res.getInt("cityid"));
					userBean.setCityName(res.getString("cityname"));
					userBean.setCountryId(res.getInt("countryid"));
					userBean.setCountryName(res.getString("countryname"));

					userBean.setBatchId(res.getInt("batchid"));
					userBean.setBatchYear(res.getInt("batchyear"));
					userBean.setBatchMappingId(res.getInt("batchmappingid"));

					userBean.setGender(res.getString("usergender"));

					userBean.setAddress(res.getString("useraddress"));
					userBean.setZIP(res.getString("userzip"));

					userBean.setTelephone(res.getString("userphone"));
					userBean.setHandphone(res.getString("userhandphone"));
					userBean.setEmail(res.getString("useremail"));

					userBean.setMainpics(res.getString("usermainpics"));

					vUser.addElement(userBean);
				}

				vUser.trimToSize();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				boxDB.clear();
				if (!pdb.isClosedStatement())
					pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vUser;
	}


	public int checkUsername(String username)
	{
		if (username == null)
			throw new NullPointerException("Received null username when checking existing username");

		int userId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("userid");

		boxDB.addCondition(0);
		boxDB.addCondition("username", BoxDB.EQUALS, username);

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next())
			{
				userId = res.getInt("userid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				username = null;
				pdb.closeStatement();
				if (pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userId;
	}


	@Override
	public int CreateNew(UserBean bean)
	{
		if (bean == null)
			throw new NullPointerException("Received null on user bean");

		int res1 = CreateNewProfile(bean);
		int res2 = CreateNewAuth(bean);
		int res3 = createMapping();

		int resFinal = res1 + res2 + res3;
		logger.info("resFinal - should be returned 3: " + resFinal);

		return resFinal;
	}

	private int CreateNewAuth(UserBean bean)
	{
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("users_auth");

		HashValues hv = new HashValues();

		int newAuthId = getLastAuthId() + 1;

		userId = newAuthId;

		boxDB.addColumn("user_id", newAuthId);
		boxDB.addColumn("user_name", bean.getUsername());

		String salt = hv.newSaltValues();
		String combine = bean.getPassword() + hv.newSaltValues();
		String hashPassword  = hv.hashValue(combine);
		boxDB.addColumn("user_password", hashPassword);
		boxDB.addColumn("salt_hash", salt);

		try {
			result = pdb.insert(boxDB);
			newAuthId = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				salt = null;
				combine = null;
				hashPassword = null;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private int CreateNewProfile(UserBean bean)
	{
		if (bean  == null)
			throw new NullPointerException("Received null bean");

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("users_profile");

		// Reflects parser
		if (bean.getGender() != null)
		{
			if (bean.getGender().equalsIgnoreCase("female"))
				boxDB.addColumn("gender", String.valueOf(1));
			else if (bean.getGender().equalsIgnoreCase("male"))
				boxDB.addColumn("gender", String.valueOf(2));
			else
				throw new IllegalArgumentException("Ops sorry the gender value that you input is wrong");
		}

		int newAuthId = getLastProfileId() + 1;
		profileId = newAuthId;
		boxDB.addColumn("profile_id", newAuthId);

		int cityId = getMappingCityId(bean.getCityName(), bean.getCountryName());
		if (cityId != 0)
		{
			boxDB.addColumn("city_id", cityId);
		}
		else
		{
			throw new IllegalArgumentException("ops, city mapping is not provided in locations, city_name: "
					+ bean.getCityName() + " and country_name: " + bean.getCountryName());
		}

		int batchMappingId = getBatchMappingId(String.valueOf(bean.getBatchYear()), bean.getDepartmentName(), bean.getFacultyName());
		if (batchMappingId != 0)
		{
			boxDB.addColumn("batch_mapping_id", batchMappingId);
		}
		else
		{
			throw new IllegalArgumentException("ops, cannot find batch_mapping_id in batch_mapping_view, batchyear: "
					+ bean.getBatchYear() + " , department name: " + bean.getDepartmentName() + " , faculty name: "
					+ bean.getFacultyName());
		}

		boxDB.addColumn("first_name", bean.getFirstName());
		boxDB.addColumn("last_name", bean.getLastName());
		boxDB.addColumn("dob", String.valueOf(bean.getDOB()));
		boxDB.addColumn("address", bean.getAddress());

		boxDB.addColumn("telephone_home", bean.getTelephone());
		boxDB.addColumn("handphone", bean.getHandphone());
		boxDB.addColumn("email", bean.getEmail());
		boxDB.addColumn("zip", String.valueOf(bean.getZIP()));

		try {
			result = pdb.insert(boxDB);
			newAuthId = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return result;
	}

	private int getMappingCityId(String city, String country)
	{
		int cityId = 0;
		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.LOCATION_VIEW);

		boxDB.addColumn("cityid");

		boxDB.addCondition(0);
		boxDB.addCondition("cityname", BoxDB.EQUALS, city);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("countryname", BoxDB.EQUALS, country);

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next())
			{
				cityId = res.getInt("cityid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				city = null;
				country = null;
				pdb.closeStatement();
				if (pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cityId;
	}

	private int getBatchMappingId(String batchYear, String departmentName, String facultyName)
	{
		if (batchYear == null)
			throw new NullPointerException("Received null on batch year parameter");

		if (departmentName== null)
			throw new NullPointerException("Received null on department name parameter");

		if (facultyName == null)
			throw new NullPointerException("Received null on faculty name parameter");

		int batchMappingId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("batchmappingid");

		boxDB.addCondition(0);
		boxDB.addCondition("batchyear", BoxDB.EQUALS, batchYear);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("departmentname", BoxDB.EQUALS, departmentName);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("facultyname", BoxDB.EQUALS, facultyName);

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next())
			{
				batchMappingId = res.getInt("batchmappingid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchYear = null;
				departmentName = null;
				facultyName = null;
				pdb.closeStatement();
				if (pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return batchMappingId;

	}


	public int getBatchMappingIdForComment(String batchYear, String departmentName, String facultyName)
	{
		if (batchYear == null)
		{
			batchYear = "";
		}

		if (departmentName == null)
		{
			departmentName = "";
		}

		if (facultyName == null)
		{
			facultyName = "";
		}

		int batchMappingId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("batchmappingid");

		boxDB.addCondition(0);
		boxDB.addCondition("batchyear", BoxDB.EQUALS, batchYear);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("departmentname", BoxDB.EQUALS, departmentName);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("facultyname", BoxDB.EQUALS, facultyName);

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next())
			{
				batchMappingId = res.getInt("batchmappingid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchYear = null;
				departmentName = null;
				facultyName = null;
				pdb.closeStatement();
				if (pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return batchMappingId;

	}
	private int createMapping()
	{
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("users_stats");

		boxDB.addColumn("profile_id", profileId);
		boxDB.addColumn("user_id", userId);

		try {
			result = pdb.insert(boxDB);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				profileId = 0;
				userId = 0;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	private int getLastAuthId()
	{
		int userId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			userId = pdb.getLastId("users_auth", "user_id");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userId;
	}

	private int getLastProfileId()
	{
		int profileId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			profileId = pdb.getLastId("users_profile", "profile_id");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return profileId;
	}


	@Override
	public UserBean findBy(String key, String val) {

		if (key == null || val == null)
			throw new NullPointerException("Received null on parameters");

		if (key.isEmpty() || val.isEmpty())
			throw new IllegalArgumentException("Received empty on parameters");

		UserBean userBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn(Cons.ALL_COLUMNS);
		boxDB.addCondition(0);

		if (key.equalsIgnoreCase("username"))
		{
			boxDB.addCondition("username", BoxDB.EQUALS, val);
		}
		else if (key.equalsIgnoreCase("profile_id"))
		{
			boxDB.addCondition("profileid", BoxDB.EQUALS, val);
		}
		else if (key.equalsIgnoreCase("user_id"))
		{
			boxDB.addCondition("userid", BoxDB.EQUALS, val);
		}
		else
			return null;

		try {
			res = pdb.returnedSingleQuery(boxDB);
			if (res.next())
			{
				userBean = new UserBean();

				userBean.setUserid(res.getInt("userid"));
				userBean.setProfileId(res.getInt("profileid"));
				userBean.setStatsUserId(res.getInt("statsuserid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				key = null;
				val = null;
				pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userBean;
	}

	@Override
	public int updateRecent(UserBean bean) {

		if (bean == null) {
			throw new NullPointerException("Received null beanUpdated");
		}

		int batch_mapping_id = getBatchMappingId(String.valueOf(bean.getBatchYear()),
				bean.getDepartmentName(), bean.getFacultyName());

		if (batch_mapping_id == 0)
			throw new IllegalArgumentException("Ops, you cannot find mapping for this batch/department/faculty");

		int result = 0;
		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("users_profile");

		boxDB.addColumn("first_name", bean.getFirstName());
		boxDB.addColumn("last_name", bean.getLastName());
		boxDB.addColumn("dob", String.valueOf(bean.getDOB()));
		boxDB.addColumn("ZIP", String.valueOf(bean.getZIP()));
		boxDB.addColumn("address", bean.getAddress());
		boxDB.addColumn("telephone_home", bean.getTelephone());
		boxDB.addColumn("handphone", bean.getHandphone());
		boxDB.addColumn("email", bean.getEmail());
		boxDB.addColumn("batch_mapping_id", batch_mapping_id);
		boxDB.addColumn("city_id", bean.getCityId());
		boxDB.addColumn("gender", bean.getGender());

		boxDB.addColumn("modified", Cons.NOW);

		boxDB.addCondition(0);
		boxDB.addCondition("profile_id", BoxDB.EQUALS, String.valueOf(bean.getProfileId()));

		try {
			result = pdb.update(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bean.clear();
				bean = null;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int deleteRecent(UserBean bean) {

		int res1 = deleteUserMapping(bean);
		int res2 = deleteProfile(bean);
		int res3 = deleteAuth(bean);

		bean.clear();

		int result = res1 + res2 + res3;

		return result;
	}

	private int deleteAuth(UserBean bean)
	{
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("users_auth");

		boxDB.addCondition(0);
		boxDB.addCondition("user_id", BoxDB.EQUALS, String.valueOf(bean.getUserid()));

		try {
			result = pdb.delete(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private int deleteProfile(UserBean bean)
	{
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("users_profile");

		boxDB.addCondition(0);
		boxDB.addCondition("profile_id", BoxDB.EQUALS, String.valueOf(bean.getProfileId()));

		try {
			result = pdb.delete(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private int deleteUserMapping(UserBean bean)
	{
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("users_stats");

		boxDB.addCondition(0);
		boxDB.addCondition("user_id", BoxDB.EQUALS, String.valueOf(bean.getUserid()));
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("profile_id", BoxDB.EQUALS, String.valueOf(bean.getProfileId()));

		try {
			result = pdb.delete(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}


}
