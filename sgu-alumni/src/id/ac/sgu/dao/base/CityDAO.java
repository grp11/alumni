package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.CityBean;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

import java.util.List;

public class CityDAO {

	public CityDAO() {}

	public int createCity(CityBean cityBean) {
		int result = Cons.CREATE_CITY_FAILURE;

		return result;
	}

	public int searchCity(CityBean cityBean) {
		int result = 0;

		return result;
	}

	public int deleteCity() {
		int result = Cons.DELETE_CITY_FAILURE;

		return result;
	}

	public List<CityBean> allCities() {

		return null;
	}



}
