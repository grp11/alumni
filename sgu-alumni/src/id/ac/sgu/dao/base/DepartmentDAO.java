package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class DepartmentDAO extends BaseDAO<DepartmentBean> {

//	private static Logger logger = Logger.getLogger(DepartmentDAO.class);

	public DepartmentDAO() {

	}

	public int CreateNew(DepartmentBean departmentBean) {

		if (departmentBean == null) {
			throw new NullPointerException("Received null departmentBean");
		}

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("department");

		int newId = getLastId() + 1;

		boxDB.addColumn("department_id", newId);
		boxDB.addColumn("department_name", departmentBean.getDepartmentName());
		boxDB.addColumn("department_alias", departmentBean.getDepartmentAlias());

		try {
			result = pdb.insert(boxDB);
			newId = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				departmentBean.clear();
				departmentBean = null;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private int getLastId() {
		int depId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			depId = pdb.getLastId("department", "department_id");
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
		return depId;
	}

	public List<DepartmentBean> findAll() {

		Vector<DepartmentBean> vDepartment = null;

		DepartmentBean departmentBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("department");

		boxDB.addColumn("department_id");
		boxDB.addColumn("department_name");
		boxDB.addColumn("department_alias");
		boxDB.addColumn("created");
		boxDB.addColumn("modified");
		boxDB.addColumn("created_by");
		boxDB.addColumn("modified_by");

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vDepartment = new Vector<DepartmentBean>(10);

			while (res.next()) {

				departmentBean = new DepartmentBean();

				departmentBean.setDepartmentId(res.getInt("department_id"));

				departmentBean.setDepartmentName(res.getString("department_name"));

				departmentBean.setDepartmentAlias(res.getString("department_alias"));

				departmentBean.setCreatedBy(res.getInt("created_by"));

				departmentBean.setModifiedBy(res.getInt("modified_by"));

				vDepartment.addElement(departmentBean);

			}

			vDepartment.trimToSize();

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

		return vDepartment;
	}

	/**
	 *
	 * @param key
	 * 		Input department column name
	 * 			There are three options:
	 * 			1. name
	 * 			2. id
	 * 			3. alias
	 *
	 * */
	public DepartmentBean findBy(String key, String val) {

		if (key == null || val == null)
			throw new NullPointerException("Received null parameters");

		if (key.isEmpty() || val.isEmpty())
			throw new IllegalArgumentException("Received empty strings");

		DepartmentBean departmentBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("department");

		boxDB.addColumn("department_id");
		boxDB.addColumn("department_name");
		boxDB.addColumn("department_alias");
		boxDB.addColumn("created");
		boxDB.addColumn("modified");
		boxDB.addColumn("created_by");
		boxDB.addColumn("modified_by");

		boxDB.addCondition(0);
		if (key.equalsIgnoreCase("name"))
			boxDB.addCondition("department_name", BoxDB.EQUALS, val);
		else if (key.equalsIgnoreCase("id"))
			boxDB.addCondition("department_id", BoxDB.EQUALS, val);
		else if (key.equalsIgnoreCase("alias"))
			boxDB.addCondition("department_alias", BoxDB.EQUALS, val);
		else return null;

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {
				departmentBean = new DepartmentBean();

				departmentBean.setDepartmentId(res.getInt("department_id"));

				departmentBean.setDepartmentName(res.getString("department_name"));

				departmentBean.setDepartmentAlias(res.getString("department_alias"));

				departmentBean.setCreatedBy(res.getInt("created_by"));

				departmentBean.setModifiedBy(res.getInt("modified_by"));

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
				pdb.closeResultSet();
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return departmentBean;
	}

	public int updateRecent(DepartmentBean beanUpdated) {

		if (beanUpdated == null) {
			throw new NullPointerException("Received null beanUpdated");
		}

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("department");

		boxDB.addColumn("department_name", beanUpdated.getNewName());
		boxDB.addColumn("department_alias", beanUpdated.getNewAlias());
		boxDB.addColumn("modified", Cons.NOW);

		boxDB.addCondition(0);
		boxDB.addCondition("department_name", BoxDB.EQUALS, beanUpdated.getDepartmentName());

		try {
			result = pdb.update(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				beanUpdated.clear();
				beanUpdated = null;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteRecent(DepartmentBean departmentBean) {

		int result = 0;
		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("department");

		boxDB.addCondition(0);
		boxDB.addCondition("department_name", BoxDB.EQUALS, departmentBean.getDepartmentName());

		try {
			result = pdb.delete(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				departmentBean.clear();
				departmentBean = null;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
