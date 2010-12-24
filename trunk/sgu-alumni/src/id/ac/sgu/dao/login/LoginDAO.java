package id.ac.sgu.dao.login;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class LoginDAO extends BaseDAO implements Serializable {

	private static final long serialVersionUID = -5493749749565504546L;

	private static Logger logger = Logger.getLogger(LoginDAO.class);

	public boolean userVerify(String username, String password) {
		boolean result = false;

		logger.info("LoginDAO.userVerify -- ENTER --");

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("username");
		boxDB.addColumn("userpass");

		boxDB.addCondition(0);
		boxDB.addCondition("userpass", BoxDB.EQUALS, password);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("username", BoxDB.EQUALS, username);

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		try {

			if (pdb.querySize(boxDB) == 1)
				result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		logger.info("LoginDAO.userVerify -- EXIT --");

		return result;
	}

	public String getSalt(String username) {

		String salt = null;

		PostgresDB pdb = new PostgresDB();

		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("username");
		boxDB.addColumn("authsalt");

		boxDB.addCondition(0);
		boxDB.addCondition("username", BoxDB.EQUALS, username);

		try {

			res = pdb.returnedSingleQuery(boxDB);

			if (res.next())
				salt = res.getString("authsalt");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return salt;
	}

	public boolean checkExistingUsername(String username) {
		boolean result = false;

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("username");

		boxDB.addCondition(0);
		boxDB.addCondition("username", BoxDB.EQUALS, username);

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		try {

			if (pdb.querySize(boxDB) == 0)
				result = true;

		} catch (SQLException e) {
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

		return result;
	}

	public boolean checkExistingEmail(String emailAddress) {
		boolean result = false;

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("useremail");

		boxDB.addCondition(0);
		boxDB.addCondition("useremail", BoxDB.EQUALS, emailAddress);


		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		try {

			if (pdb.querySize(boxDB) == 0)
				result = true;

		} catch (SQLException e) {
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

		return result;
	}

	public UserBean userBean(String username, String password) {
		UserBean userBean = null;

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

		// Add 6 December 2010
		boxDB.addColumn("authrolename");

		boxDB.addColumn("authcreated");
	//	boxDB.addColumn("authcreatedby");
		boxDB.addColumn("authmodified");
	//	boxDB.addColumn("authmodifiedby");
		boxDB.addColumn("profileid");
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
		boxDB.addCondition("userpass", BoxDB.EQUALS, password);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("username", BoxDB.EQUALS, username);

		try {

			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {
				userBean = new UserBean();

				userBean.setUserid(res.getInt("userid"));

				userBean.setUsername(username);

				userBean.setProfileId(res.getInt("profileid"));

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

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userBean;
	}

	@Override
	protected List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int CreateNew(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Object findBy(String key, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int updateRecent(Object t1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int deleteRecent(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

/*	@Override
	protected List findAll() {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
