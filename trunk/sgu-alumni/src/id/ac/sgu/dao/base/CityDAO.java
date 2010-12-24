package id.ac.sgu.dao.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import id.ac.sgu.bean.base.CityBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

public class CityDAO extends BaseDAO<CityBean> {

	public CityDAO() {}

	@Override
	protected List<CityBean> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CityBean> findAllInMapping()
	{

		Vector<CityBean> vCity = null;

		CityBean departmentBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.LOCATION_VIEW);

		boxDB.addColumn("cityid");
		boxDB.addColumn("cityname");

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vCity = new Vector<CityBean>(10);

			while (res.next()) {

				departmentBean = new CityBean();

				departmentBean.setCityId(res.getInt("cityid"));

				departmentBean.setCityName(res.getString("cityname"));

				vCity.addElement(departmentBean);

			}

			vCity.trimToSize();

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

		return vCity;
	}

	public List<CityBean> findInMappingBy(String countryName) {

		if (countryName.isEmpty())
			throw new NullPointerException("Ops, received null input on country name");

		Vector<CityBean> vCity = null;

		CityBean departmentBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.LOCATION_VIEW);

		boxDB.addColumn("cityid");
		boxDB.addColumn("cityname");

		boxDB.addCondition(0);
		boxDB.addCondition("countryname", BoxDB.EQUALS, countryName);

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vCity = new Vector<CityBean>(10);

			while (res.next()) {

				departmentBean = new CityBean();

				departmentBean.setCityId(res.getInt("cityid"));

				departmentBean.setCityName(res.getString("cityname"));

				vCity.addElement(departmentBean);

			}

			vCity.trimToSize();

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

		return vCity;
	}



	@Override
	protected int CreateNew(CityBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected CityBean findBy(String key, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int updateRecent(CityBean t1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int deleteRecent(CityBean t) {
		// TODO Auto-generated method stub
		return 0;
	}


}
