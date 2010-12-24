package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.CityBean;
import id.ac.sgu.bean.base.CountryBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class CountryDAO extends BaseDAO<CountryBean> {

	public CountryDAO() {}

	@Override
	protected List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CountryBean> findAllInMapping()
	{

		Vector<CountryBean> vCountry = null;

		CountryBean countryBean = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.LOCATION_VIEW);

		boxDB.addColumn("countryid");
		boxDB.addColumn("countryname");

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vCountry = new Vector<CountryBean>(10);

			while (res.next()) {

				countryBean = new CountryBean();

				countryBean.setCountryId(res.getInt("countryid"));

				countryBean.setCountryName(res.getString("countryname"));

				vCountry.addElement(countryBean);

			}

			vCountry.trimToSize();

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

		return vCountry;
	}


	@Override
	protected int CreateNew(CountryBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected CountryBean findBy(String key, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int deleteRecent(CountryBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateRecent(CountryBean t1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
