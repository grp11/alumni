package id.ac.sgu.utility.service;

import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.bl.base.BatchBL;
import id.ac.sgu.bl.base.CityBL;
import id.ac.sgu.bl.base.CountryBL;
import id.ac.sgu.bl.base.DepartmentBL;
import id.ac.sgu.bl.base.FacultyBL;
import id.ac.sgu.bl.base.UserBL;
import id.ac.sgu.dao.base.BatchDAO;
import id.ac.sgu.dao.base.CityDAO;
import id.ac.sgu.dao.base.CountryDAO;
import id.ac.sgu.dao.base.DepartmentDAO;
import id.ac.sgu.dao.base.FacultyDAO;
import id.ac.sgu.dao.base.UserDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.exception.alumni.InvalidDepartmentException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

public class AlumniService {

	private DepartmentDAO departmentDAO;
	private FacultyDAO facultyDAO;
	private BatchDAO batchDAO;

	private DepartmentBL departmentBL;
	private FacultyBL facultyBL;
	private BatchBL batchBL;

	private static Logger logger = Logger.getLogger(AlumniService.class);

	public int createNewDepartment(DepartmentBean departmentBean) throws InvalidDepartmentException {

		if (departmentBean == null)
			throw new InvalidDepartmentException("Department bean is null");

		if (departmentDAO.findBy("name", departmentBean.getDepartmentName()) != null)
			throw new InvalidDepartmentException("Nama sudah ada");

		if (departmentDAO.findBy("alias", departmentBean.getDepartmentAlias()) != null)
			throw new InvalidDepartmentException("Alias sudah ada");

		return departmentBL.createDepartment(departmentBean);
	}

	public int updateDepartment(DepartmentBean toBeUpdated) throws InvalidDepartmentException {

		if (toBeUpdated == null)
			throw new InvalidDepartmentException("ToBeUpdated bean is null");

		return departmentBL.updateDepartment(toBeUpdated);
	}

	public int createNewBatch(BatchBean batchBean) throws Exception {

		if (batchBean == null)
			throw new Exception("Received null on batchBean");

		if (!batchBL.validateBatch(batchBean))
			throw new Exception("Received invalid validation on batchBean");

		return batchBL.createBatch(batchBean);
	}

	public int createGDFMapping(BatchBean batchBean) throws Exception {

		String[] val = {Integer.toString(batchBean.getBatchYear()),
						batchBean.getDepartmentName(),
						batchBean.getFacultyName()};

		if (batchDAO.findInMappingBy("mapping", val) != null )
			throw new Exception("There is an existing mapping in batch_mapping");

		return batchBL.createBDFMapping(batchBean);
	}

	public int deleteBatch(BatchBean batchBean) {

		return batchBL.deleteBatch(batchBean);
	}

	public boolean validateBatch(BatchBean batchBean) {

		return validateBatch(batchBean);
	}

	public int updateBatch(BatchBean batchBean) {

		return batchBL.updateBatch(batchBean);
	}

	public int updateBDFMapping(BatchBean batchBean) {

		return batchBL.updateBDFMapping(batchBean);
	}

	public int createBDFMapping(BatchBean batchBean) {

		return batchBL.deleteBDFMapping(batchBean);
	}

	public Map<String, List<String>> getDepartmentMapping() {
		Map<String, List<String>> modelMaps = new HashMap<String, List<String>>();

		List<String> departmentList = getDepartmentName();

		int size = departmentList.size();
		for (int i = 0; i < size; i++) {
			String name = departmentList.get(i);
			modelMaps.put(name, getFacultyName(name));
			name = null;
		}
		departmentList.clear();

		return modelMaps;
	}

	// For ddc
	public List<String> getDepartmentName() {
		List<String> departmentList = new Vector<String>();

		List<DepartmentBean> listTemp = departmentDAO.findAll();

		if (null != listTemp) {
			while (!listTemp.isEmpty()) {

				DepartmentBean temp = listTemp.remove(0);
				departmentList.add(temp.getDepartmentName());

			}
			Collections.sort(departmentList);
		}

		return departmentList;
	}

	// For ddc-automation
	public List<String> getFacultyName(String departmentName) {

		List<String> facultyList = new Vector<String>();

		List<FacultyBean> listTemp = facultyDAO.findAllInMapping(departmentName);

		if (null != listTemp) {
			while (!listTemp.isEmpty()) {

				FacultyBean temp = listTemp.remove(0);
				facultyList.add(temp.getFacultyName());

			}
			Collections.sort(facultyList);
		}

		return facultyList;
	}

	public List<DepartmentBean> findAllDepartmentsInMapping() {

		return null;
	}

	public List<FacultyBean> findAllFacultiesInMapping() {

		return null;
	}

	public List<BatchBean> findAllBatchesInMapping() {

		return batchDAO.findAllInMapping();
	}

	public List<BatchBean> findAllBatchesInMapping(int batchYear) {
		return batchDAO.findAllInMapping(batchYear);
	}

	public List<DepartmentBean> findAllDepartments() {
		return departmentDAO.findAll();
	}

	public List<FacultyBean> findAllFaculties() {
		return facultyDAO.findAll();
	}

	public List<BatchBean> findAllBatches(){
		return batchDAO.findAll();
	}

	/**
	 * @return the departmentDAO
	 */
	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	/**
	 * @param departmentDAO the departmentDAO to set
	 */
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * @return the facultyDAO
	 */
	public FacultyDAO getFacultyDAO() {
		return facultyDAO;
	}

	/**
	 * @param facultyDAO the facultyDAO to set
	 */
	public void setFacultyDAO(FacultyDAO facultyDAO) {
		this.facultyDAO = facultyDAO;
	}

	/**
	 * @return the batchDAO
	 */
	public BatchDAO getBatchDAO() {
		return batchDAO;
	}

	/**
	 * @param batchDAO the batchDAO to set
	 */
	public void setBatchDAO(BatchDAO batchDAO) {
		this.batchDAO = batchDAO;
	}

	/**
	 * @return the departmentBL
	 */
	public DepartmentBL getDepartmentBL() {
		return departmentBL;
	}

	/**
	 * @param departmentBL the departmentBL to set
	 */
	public void setDepartmentBL(DepartmentBL departmentBL) {
		this.departmentBL = departmentBL;
	}

	/**
	 * @return the facultyBL
	 */
	public FacultyBL getFacultyBL() {
		return facultyBL;
	}

	/**
	 * @param facultyBL the facultyBL to set
	 */
	public void setFacultyBL(FacultyBL facultyBL) {
		this.facultyBL = facultyBL;
	}

	/**
	 * @return the batchBL
	 */
	public BatchBL getBatchBL() {
		return batchBL;
	}

	/**
	 * @param batchBL the batchBL to set
	 */
	public void setBatchBL(BatchBL batchBL) {
		this.batchBL = batchBL;
	}

/*
	private CityDAO cityDAO;
	private CityBL cityBL;

	private CountryDAO countryDAO;
	private CountryBL countryBL;

	private UserDAO userDAO;
	private UserBL userBL;

	*//**
	 * @return the cityDAO
	 *//*
	public CityDAO getCityDAO() {
		return cityDAO;
	}

	*//**
	 * @param cityDAO the cityDAO to set
	 *//*
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	*//**
	 * @return the cityBL
	 *//*
	public CityBL getCityBL() {
		return cityBL;
	}

	*//**
	 * @param cityBL the cityBL to set
	 *//*
	public void setCityBL(CityBL cityBL) {
		this.cityBL = cityBL;
	}

	*//**
	 * @return the countryDAO
	 *//*
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	*//**
	 * @param countryDAO the countryDAO to set
	 *//*
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	*//**
	 * @return the countryBL
	 *//*
	public CountryBL getCountryBL() {
		return countryBL;
	}

	*//**
	 * @param countryBL the countryBL to set
	 *//*
	public void setCountryBL(CountryBL countryBL) {
		this.countryBL = countryBL;
	}

	*//**
	 * @return the userDAO
	 *//*
	public UserDAO getUserDAO() {
		return userDAO;
	}

	*//**
	 * @param userDAO the userDAO to set
	 *//*
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	*//**
	 * @return the userBL
	 *//*
	public UserBL getUserBL() {
		return userBL;
	}

	*//**
	 * @param userBL the userBL to set
	 *//*
	public void setUserBL(UserBL userBL) {
		this.userBL = userBL;
	}*/


}
