package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.CountryBean;
import id.ac.sgu.dao.BaseDAO;

import java.util.List;

public class CountryDAO extends BaseDAO<CountryBean> {

	public CountryDAO() {}

	@Override
	protected List findAll() {
		// TODO Auto-generated method stub
		return null;
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
