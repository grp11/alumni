package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class FacultyDAO extends BaseDAO<FacultyBean> {


	public FacultyDAO() {

	}

	@Override
	public List<FacultyBean> findAll() {
		Vector<FacultyBean> vFaculty = null;

		FacultyBean facultyBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("faculty");

		boxDB.addColumn("faculty_id");
		boxDB.addColumn("faculty_name");
		boxDB.addColumn("faculty_alias");
		boxDB.addColumn("created");
		boxDB.addColumn("modified");
		boxDB.addColumn("created_by");
		boxDB.addColumn("modified_by");

		boxDB.addCondition(0);

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vFaculty = new Vector<FacultyBean>(10);

			while (res.next()) {

				facultyBean = new FacultyBean();

				facultyBean.setFacultyId(res.getInt("faculty_id"));

				facultyBean.setFacultyName(res.getString("faculty_name"));

				facultyBean.setFacultyAlias(res.getString("faculty_alias"));

				facultyBean.setCreatedBy(res.getInt("created_by"));

				facultyBean.setModifiedBy(res.getInt("modified_by"));

				vFaculty.addElement(facultyBean);

			}

			vFaculty.trimToSize();

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

		return vFaculty;
	}

	public List<FacultyBean> findAllInMapping(String batchyear, String departmentName) {

		if (batchyear.isEmpty())
			throw new NullPointerException("Received empty string on batchYear");

		if (departmentName.isEmpty())
			throw new NullPointerException("Received empty string on departmentName");

		Vector<FacultyBean> vFaculty = null;

		FacultyBean facultyBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("facultyid");
		boxDB.addColumn("facultyname");
		boxDB.addColumn("facultyalias");

		boxDB.addCondition(0);
		boxDB.addCondition("departmentname", BoxDB.EQUALS, departmentName);
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("batchyear", BoxDB.EQUALS, batchyear);


		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vFaculty = new Vector<FacultyBean>(10);

			while (res.next()) {

				facultyBean = new FacultyBean();

				facultyBean.setFacultyId(res.getInt("facultyid"));

				facultyBean.setFacultyName(res.getString("facultyname"));

				facultyBean.setFacultyAlias(res.getString("facultyalias"));

				vFaculty.addElement(facultyBean);

			}
			vFaculty.trimToSize();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();

				if (res!=null)
					this.closeResultSet();

				if (pdb.isResultSetNull()) {
					System.out.println("MASUKK KESINI");
					pdb.closeResultSet();
				}

				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vFaculty;
	}

	public List<FacultyBean> findAllInMapping(String departmentName) {

		if (departmentName.isEmpty())
			throw new NullPointerException("Received empty string on departmentName");

		Vector<FacultyBean> vFaculty = null;

		FacultyBean facultyBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("facultyid");
		boxDB.addColumn("facultyname");
		boxDB.addColumn("facultyalias");

		boxDB.addCondition(0);
		boxDB.addCondition("departmentname", BoxDB.EQUALS, departmentName);

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vFaculty = new Vector<FacultyBean>(10);

			while (res.next()) {

				facultyBean = new FacultyBean();

				facultyBean.setFacultyId(res.getInt("facultyid"));

				facultyBean.setFacultyName(res.getString("facultyname"));

				facultyBean.setFacultyAlias(res.getString("facultyalias"));

				vFaculty.addElement(facultyBean);

			}
			vFaculty.trimToSize();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();

				if (res!=null)
					this.closeResultSet();

				if (pdb.isResultSetNull()) {
					System.out.println("MASUKK KESINI");
					pdb.closeResultSet();
				}

				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vFaculty;
	}


	@Override
	protected int CreateNew(FacultyBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FacultyBean findBy(String key, String val) {

		if (key == null || val == null)
			throw new NullPointerException("Received null parameters");

		if (key.isEmpty() || val.isEmpty())
			throw new IllegalArgumentException("Received empty strings");

		FacultyBean facultyBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("faculty");

		boxDB.addColumn("faculty_id");
		boxDB.addColumn("faculty_name");
		boxDB.addColumn("faculty_alias");
		boxDB.addColumn("created");
		boxDB.addColumn("modified");
		boxDB.addColumn("created_by");
		boxDB.addColumn("modified_by");

		boxDB.addCondition(0);
		if (key.equalsIgnoreCase("name"))
			boxDB.addCondition("faculty_name", BoxDB.EQUALS, val);
		else if (key.equalsIgnoreCase("id"))
			boxDB.addCondition("facultu_id", BoxDB.EQUALS, val);
		else if (key.equalsIgnoreCase("alias"))
			boxDB.addCondition("faculty_alias", BoxDB.EQUALS, val);
		else return null;

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {
				facultyBean = new FacultyBean();

				facultyBean.setFacultyId(res.getInt("faculty_id"));

				facultyBean.setFacultyName(res.getString("faculty_name"));

				facultyBean.setFacultyAlias(res.getString("faculty_alias"));

				facultyBean.setCreatedBy(res.getInt("created_by"));

				facultyBean.setModifiedBy(res.getInt("modified_by"));

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

		return facultyBean;
	}

	@Override
	protected int deleteRecent(FacultyBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateRecent(FacultyBean t1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
