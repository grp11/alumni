package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class BatchDAO extends BaseDAO<BatchBean> {

	public BatchDAO() {}

	@Override
	public List<BatchBean> findAll() {
		Vector<BatchBean> vBatch = null;

		BatchBean batchBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("batch");

		boxDB.addColumn("batch_id");
		boxDB.addColumn("batch_year");
		boxDB.addColumn("created");
		boxDB.addColumn("modified");
		boxDB.addColumn("created_by");
		boxDB.addColumn("modified_by");

		try {

			res = pdb.returnedMultipleQuery(boxDB);

			vBatch = new Vector<BatchBean>();

			while (res.next()) {

				batchBean = new BatchBean();

				batchBean.setBatchId(res.getInt("batch_id"));
				batchBean.setBatchYear(res.getInt("batch_year"));
				batchBean.setCreatedBy(res.getInt("created_by"));
				batchBean.setModifiedBy(res.getInt("modified_by"));

				vBatch.addElement(batchBean);

			}

			vBatch.trimToSize();
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

		return vBatch;
	}

	public List<BatchBean> findAllInMapping() {
		Vector<BatchBean> vBatch = null;

		BatchBean batchBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("batchmappingid");

		boxDB.addColumn("batchid");
		boxDB.addColumn("batchyear");
		boxDB.addColumn("batchcreated");
		boxDB.addColumn("batchmodified");
		boxDB.addColumn("batchcreatedby");
		boxDB.addColumn("batchmodifiedby");

		boxDB.addColumn("departmentname");
		boxDB.addColumn("facultyname");

		boxDB.addColumn("departmentid");
		boxDB.addColumn("facultyid");

//		boxDB.addCondition(0);

		try {

			res = pdb.returnedMultipleQuery(boxDB);

			vBatch = new Vector<BatchBean>();

			while (res.next()) {

				batchBean = new BatchBean();

				batchBean.setBatchMappingId(res.getInt("batchmappingid"));

				batchBean.setBatchId(res.getInt("batchid"));
				batchBean.setBatchYear(res.getInt("batchyear"));
				batchBean.setCreatedBy(res.getInt("batchcreatedby"));
				batchBean.setModifiedBy(res.getInt("batchmodifiedby"));

				batchBean.setDepartmentName(res.getString("departmentname"));
				batchBean.setFacultyName(res.getString("facultyname"));

				vBatch.addElement(batchBean);

			}

			vBatch.trimToSize();
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

		return vBatch;

	}

	@Override
	public int CreateNew(BatchBean bean) {

		if (bean == null) {
			throw new NullPointerException("Received null bean");
		}

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("batch");

		int newId = getLastId() + 1 ;

		boxDB.addColumn("batch_id", newId);
		boxDB.addColumn("batch_year", bean.getBatchYear());

		try {
			result = pdb.insert(boxDB);
			newId = 0;
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

	// Single Query
	public BatchBean findInMappingBy(String key, String[] val) {

		if (key == null || val == null)
			throw new NullPointerException("Received null parameters");

		if (key.isEmpty())
			throw new IllegalArgumentException("Received empty strings");

		if (val.length >= 3 && val.length < 0)
			throw new IllegalArgumentException("Received invalid val length, should be between 0 and 3");

		if (val[0].isEmpty())
			throw new IllegalArgumentException("Received empty on array value number zero");

		BatchBean batchBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("batchmappingid");

		boxDB.addColumn("batchid");
		boxDB.addColumn("batchyear");
		boxDB.addColumn("batchcreated");
		boxDB.addColumn("batchmodified");
		boxDB.addColumn("batchcreatedby");
		boxDB.addColumn("batchmodifiedby");

		boxDB.addColumn("departmentname");
		boxDB.addColumn("facultyname");

		boxDB.addColumn("departmentid");
		boxDB.addColumn("facultyid");

		boxDB.addCondition(0);
		if (key.equalsIgnoreCase("batchid"))
			boxDB.addCondition("batchid", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("batchyear"))
			boxDB.addCondition("batchyear", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("departmentname"))
			boxDB.addCondition("departmentname", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("departmentalias"))
			boxDB.addCondition("departmentalias", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("facultyname"))
			boxDB.addCondition("facultyname", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("facultyalias"))
			boxDB.addCondition("facultyalias", BoxDB.EQUALS, val[0]);
		else if (key.equalsIgnoreCase("mapping")) {
			if (val.length != 3)
				throw new NullPointerException("Invalid registered values");
			boxDB.addCondition("batchyear", BoxDB.EQUALS, val[0]);
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("departmentname", BoxDB.EQUALS, val[1]);
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("facultyname", BoxDB.EQUALS, val[2]);
		}
		else return null;

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {

				batchBean = new BatchBean();

				batchBean.setBatchMappingId(res.getInt("batchmappingid"));

				batchBean.setBatchId(res.getInt("batchid"));
				batchBean.setBatchYear(res.getInt("batchyear"));

				batchBean.setDepartmentId(res.getInt("departmentid"));
				batchBean.setDepartmentName(res.getString("departmentname"));

				batchBean.setFacultyId(res.getInt("facultyid"));
				batchBean.setFacultyName(res.getString("facultyname"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				key = null;

				for (int i = 0; i < val.length; i++)
					val[i] = null;

				pdb.closeStatement();
				pdb.closeResultSet();
				this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return batchBean;
	}

	/**
	 * batch table
	 * */
	@Override
	public BatchBean findBy(String key, String val) {

		if (key == null || val == null)
			throw new NullPointerException("Received null parameters");

		if (key.isEmpty() || val.isEmpty())
			throw new IllegalArgumentException("Received empty strings");

		BatchBean batchBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("batch");

		boxDB.addColumn("batch_id");
		boxDB.addColumn("batch_year");

		boxDB.addCondition(0);
		if (key.equalsIgnoreCase("id"))
			boxDB.addCondition("batch_id", BoxDB.EQUALS, val);
		else if (key.equalsIgnoreCase("year"))
			boxDB.addCondition("batch_year", BoxDB.EQUALS, val);
		else return null;

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {

				batchBean = new BatchBean();

				batchBean.setBatchId(res.getInt("batch_id"));
				batchBean.setBatchYear(res.getInt("batch_year"));

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

		return batchBean;
	}

	@Override
	public int deleteRecent(BatchBean bean) {

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("batch");

		boxDB.addCondition(0);

		try {
			boxDB.addCondition("batch_year", BoxDB.EQUALS, Integer.toString(bean.getBatchYear()));
			result = pdb.delete(boxDB);
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
	public int updateRecent(BatchBean bean) {

		if (bean == null)
			throw new NullPointerException("Received null input on batchBean");

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("batch");

		boxDB.addColumn("batch_year", Integer.toString(bean.getBatchYear()));
		boxDB.addColumn("modified", Cons.NOW);

		boxDB.addCondition(0);
		boxDB.addCondition("batch_year", BoxDB.EQUALS, Integer.toString(bean.getBatchYear()));

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

	public int createBDFMapping(BatchBean bean) {

		if (bean == null)
			throw new NullPointerException("Received null input on batchBean");

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		int newId = getBDFLastId() + 1;

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("batch_mapping");
		boxDB.addColumn("batch_mapping_id", newId);
		boxDB.addColumn("batch_id", Integer.toString(bean.getBatchId()));
		boxDB.addColumn("department_id", Integer.toString(bean.getDepartmentId()));
		boxDB.addColumn("faculty_id", Integer.toString(bean.getFacultyId()));

		try {
			result = pdb.insert(boxDB);

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

	public int updateBDFMapping(BatchBean bean) {

		if (bean == null)
			throw new NullPointerException("Received null input on bean");

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("batch_mapping");

		boxDB.addColumn("batch_id", Integer.toString(bean.getBatchId()));
		boxDB.addColumn("department_id", Integer.toString(bean.getDepartmentId()));
		boxDB.addColumn("faculty_id", Integer.toString(bean.getFacultyId()));

		boxDB.addCondition(0);
		boxDB.addCondition("batch_mapping_id", BoxDB.EQUALS, Integer.toString(bean.getBatchMappingId()));

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

	/**
	 * batch_mapping_view -> find all batches under mapping
	 *
	 * @param year
	 * 		batch year in mapping
	 * @return
	 * 		list<BatchBean of batch mappings
	 * */
	public List<BatchBean> findAllInMapping(int year) {

		if (year <= 0 || year > 9999)
			return null;

		Vector<BatchBean> vBatch = null;

		BatchBean batchBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addColumn("batchid");
		boxDB.addColumn("batchyear");
		boxDB.addColumn("departmentname");
		boxDB.addColumn("facultyname");

		boxDB.addCondition(0);
		boxDB.addCondition("batchyear", BoxDB.EQUALS, String.valueOf(year));

		try {

			res = pdb.returnedMultipleQuery(boxDB);

			vBatch = new Vector<BatchBean>();

			while (res.next()) {

				batchBean = new BatchBean();

				batchBean.setBatchId(res.getInt("batchid"));
				batchBean.setBatchYear(res.getInt("batchyear"));
				batchBean.setDepartmentName(res.getString("departmentname"));
				batchBean.setFacultyName(res.getString("facultyname"));

				vBatch.addElement(batchBean);

			}

			vBatch.trimToSize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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

		return vBatch;
	}

	public boolean isReferenced(BatchBean bean)
	{
		if (bean == null)
			throw new NullPointerException("Received null parameter on batch bean");

		int mappingId = getBDFMappingId(bean);

		boolean result = true;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.USERS_VIEW);

		boxDB.addColumn("userid");

		boxDB.addCondition(0);
		boxDB.addCondition("batchmappingid", BoxDB.EQUALS, String.valueOf(mappingId));

		try {
			if (pdb.querySize(boxDB) != 0)
			{
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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

		return result;
	}

	public int deleteBDFMapping(BatchBean bean) {

		if (bean == null)
			throw new NullPointerException("Received null parameter on batch bean");

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable("batch_mapping");

		int mappingId = getBDFMappingId(bean);

		boxDB.addCondition(0);

		try {
			boxDB.addCondition("batch_mapping_id", BoxDB.EQUALS, String.valueOf(mappingId));
			result = pdb.delete(boxDB);
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

	// To check the existing mapping in batch_mapping_view
	public boolean isBatchMapping(BatchBean bean) {
		boolean result = false;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);

		boxDB.addCondition(0);

		try {
			boxDB.addCondition("batch_mapping_id", BoxDB.EQUALS, Integer.toString(bean.getBatchMappingId()));
			int query_size = pdb.querySize(boxDB);
			result = (query_size == 0) ? result : true;
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

	public int getBDFMappingId(BatchBean bean) {
		int bdfMappingId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();
		boxDB.setTable(Cons.BATCH_MAPPING_VIEW);
		boxDB.addColumn("batchmappingid");

		boxDB.addCondition(0);
		boxDB.addCondition("batchyear", BoxDB.EQUALS, Integer.toString(bean.getBatchYear()));
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("departmentname", BoxDB.EQUALS, bean.getDepartmentName());
		boxDB.addConditionConjunction(BoxDB.AND);
		boxDB.addCondition("facultyname", BoxDB.EQUALS, bean.getFacultyName());

		try {
			res = pdb.returnedSingleQuery(boxDB);

			if (res.next()) {
				bdfMappingId = res.getInt("batchmappingid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bdfMappingId;
	}




	private int getBDFLastId() {
		int batchId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			batchId = pdb.getLastId("batch_mapping", "batch_mapping_id");
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

		return batchId;
	}

	public int existingBatchId(BatchBean bean) {
		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("batch");

		boxDB.addColumn("batch_id");

		boxDB.addCondition(0);
		boxDB.addCondition("batch_year", BoxDB.EQUALS, Integer.toString(bean.getBatchYear()));

		try {
			result = pdb.querySize(boxDB);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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

		return result;
	}


	private int getLastId() {
		int batchId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			batchId = pdb.getLastId("batch", "batch_id");
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

		return batchId;
	}

}
