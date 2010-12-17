package id.ac.sgu.dao.base;

import java.util.List;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.dao.BaseDAO;

public class UserDAO extends BaseDAO<UserBean> {

	public UserDAO(){}

	@Override
	protected List<UserBean> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int CreateNew(UserBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected UserBean findBy(String key, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int updateRecent(UserBean t1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int deleteRecent(UserBean t) {
		// TODO Auto-generated method stub
		return 0;
	}



}
