package id.ac.sgu.utility.service;

import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.bl.base.BatchBL;
import id.ac.sgu.bl.base.DepartmentBL;
import id.ac.sgu.bl.base.FacultyBL;
import id.ac.sgu.dao.base.BatchDAO;
import id.ac.sgu.dao.base.DepartmentDAO;
import id.ac.sgu.dao.base.FacultyDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.exception.alumni.InvalidDepartmentException;

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

	public int createNewDepartment(DepartmentBean departmentBean) throws InvalidDepartmentException
	{
		if (departmentBean == null)
			throw new InvalidDepartmentException("Department bean is null");

		if (departmentDAO.findBy("name", departmentBean.getDepartmentName()) != null)
			throw new InvalidDepartmentException("Nama sudah ada");

		if (departmentDAO.findBy("alias", departmentBean.getDepartmentAlias()) != null)
			throw new InvalidDepartmentException("Alias sudah ada");

		return departmentBL.createDepartment(departmentBean);
	}

	public int updateDepartment(DepartmentBean toBeUpdated) throws InvalidDepartmentException
	{
		if (toBeUpdated == null)
			throw new InvalidDepartmentException("ToBeUpdated bean is null");

		return departmentBL.updateDepartment(toBeUpdated);
	}

	public int createNewBatch(BatchBean batchBean) throws Exception
	{
		if (batchBean == null)
			throw new Exception("Received null on batchBean");

		if (batchDAO.findBy("year", String.valueOf(batchBean.getBatchYear())) != null)
			return Cons.CREATE_BATCH_FAILURE;

		return batchBL.createBatch(batchBean);
	}

	public int createGDFMapping(BatchBean batchBean) throws Exception
	{
		String[] val = {Integer.toString(batchBean.getBatchYear()),
						batchBean.getDepartmentName(),
						batchBean.getFacultyName()};

		if (batchDAO.findInMappingBy("mapping", val) != null )
			throw new Exception("There is an existing mapping in batch_mapping");

		return batchBL.createBDFMapping(batchBean);
	}

	public int deleteBatch(BatchBean batchBean)
	{
		return batchBL.deleteBatch(batchBean);
	}

	public int deleteDepartment(DepartmentBean departmentBean)
	{
		if (departmentDAO.inMapping(departmentBean) != 0)
			return Cons.DELETE_DEP_FAILURE;

		return departmentBL.deleteDepartment(departmentBean);
	}

	public int deleteFaculty(FacultyBean facultyBean)
	{
		return 0;
	}

	public int deleteMapping(BatchBean batchBean)
	{
		return batchBL.deleteBDFMapping(batchBean);
	}

	public boolean isInUserReference(BatchBean batchBean)
	{
		if (batchBean == null)
			return false;

		return batchDAO.isReferenced(batchBean);
	}

	public boolean validateBatch(BatchBean batchBean)
	{
		return validateBatch(batchBean);
	}

	public int updateBatch(BatchBean batchBean)
	{
		return batchBL.updateBatch(batchBean);
	}

	public int updateBDFMapping(BatchBean batchBean)
	{
		return batchBL.updateBDFMapping(batchBean);
	}

	public int createBDFMapping(BatchBean batchBean)
	{
		// Set batch id
		int batch_id = batchDAO.findBy("year", String.valueOf(batchBean.getBatchYear())).getBatchId();
		batchBean.setBatchId(batch_id);

		// Set department id
		int department_id = departmentDAO.findBy("name", batchBean.getDepartmentName()).getDepartmentId();
		batchBean.setDepartmentId(department_id);

		// set faculty id
		int faculty_id = facultyDAO.findBy("name", batchBean.getFacultyName()).getFacultyId();
		batchBean.setFacultyId(faculty_id);

		if (isExistingMapping(batchBean))
			return Cons.CREATE_BDF_FAILURE;

		return batchBL.createBDFMapping(batchBean);
	}

	public Map<String, Map<String, List<String>>> getBatchMapping()
	{
		Map<String, Map<String, List<String>>> modelMaps = new HashMap<String, Map<String,List<String>>>();

		List<String> batchList = getBatchYear();

		if (null != batchList)
		{
			while (!batchList.isEmpty())
			{
				String temp = batchList.remove(0);
				modelMaps.put(temp, getDepartmentMapping(Integer.parseInt(temp)));
				temp = null;
			}
		}

		return modelMaps;
	}

	public Map<String, List<String>> getDepartmentMapping(int batchYear)
	{
		Map<String, List<String>> modelMaps = new HashMap<String, List<String>>();

		List<String> departmentList = getAllDepartmentName(batchYear);

		int size = departmentList.size();
		for (int i = 0; i < size; i++)
		{
			String name = departmentList.get(i);
			modelMaps.put(name, getFacultyName(batchYear, name, true));
			name = null;
		}
		departmentList.clear();
		size = 0;

		return modelMaps;
	}

	public Map<String, List<String>> getDepartmentMapping()
	{
		Map<String, List<String>> modelMaps = new HashMap<String, List<String>>();

		List<String> departmentListTemp = getAllDepartmentName(false);

		int size = departmentListTemp.size();
		for (int i = 0; i < size; i++)
		{
			String name = departmentListTemp.get(i);
			modelMaps.put(name, getFacultyName(0, name, false));
			name = null;
		}
		departmentListTemp.clear();

		return modelMaps;
	}

	// For ddc batch
	public List<String> getBatchYear()
	{
		List<String> batchList = new Vector<String>();

		List<BatchBean> listTemp = batchDAO.findAllInMapping();

		if (null != listTemp)
		{
			while (!listTemp.isEmpty())
			{
				BatchBean temp = listTemp.remove(0);
				batchList.add(String.valueOf(temp.getBatchYear()));
			}
			Collections.sort(batchList);
		}

		return batchList;
	}

	// For ddc department
	public List<String> getAllDepartmentName(boolean inMapping)
	{
		List<String> departmentList = new Vector<String>();

		List<DepartmentBean> listTemp = (!inMapping) ?
				departmentDAO.findAll() : departmentDAO.findAllInMapping();

		if (null != listTemp)
		{
			while (!listTemp.isEmpty())
			{
				DepartmentBean temp = listTemp.remove(0);
				departmentList.add(temp.getDepartmentName());
			}
			Collections.sort(departmentList);
		}

		return departmentList;
	}


	public List<String> getAllDepartmentName(int batchYear)
	{
		List<String> departmentList = new Vector<String>();

		List<DepartmentBean> listTemp = departmentDAO.findInMappingBy(batchYear);

		if (null != listTemp)
		{
			while (!listTemp.isEmpty())
			{
				DepartmentBean temp = listTemp.remove(0);
				departmentList.add(temp.getDepartmentName());
			}
			Collections.sort(departmentList);
		}

		return departmentList;
	}

	// For ddc-automation
	public List<String> getFacultyName(int batchYear, String departmentName, boolean inMapping)
	{

		List<String> facultyList = new Vector<String>();
		List<FacultyBean> listTemp = null;

		if (inMapping)
			listTemp = facultyDAO.findAllInMapping(String.valueOf(batchYear), departmentName);
		else
			listTemp = facultyDAO.findAllInMapping(departmentName);


		if (null != listTemp)
		{
			while (!listTemp.isEmpty())
			{
				FacultyBean temp = listTemp.remove(0);
				facultyList.add(temp.getFacultyName());
			}
			Collections.sort(facultyList);
		}

		return facultyList;
	}

	public boolean isExistingMapping(BatchBean batchBean)
	{
		return (batchDAO.getBDFMappingId(batchBean) == 0) ? false : true;
	}

	public List<DepartmentBean> findAllDepartmentsInMapping()
	{

		return null;
	}

	public List<FacultyBean> findAllFacultiesInMapping()
	{

		return null;
	}

	public List<BatchBean> findAllBatchesInMapping()
	{

		return batchDAO.findAllInMapping();
	}

	public List<BatchBean> findAllBatchesInMapping(int batchYear)
	{
		return batchDAO.findAllInMapping(batchYear);
	}

	public List<DepartmentBean> findAllDepartments()
	{
		return departmentDAO.findAll();
	}

	public List<FacultyBean> findAllFaculties()
	{
		return facultyDAO.findAll();
	}

	public List<BatchBean> findAllBatches()
	{
		return batchDAO.findAll();
	}

	/**
	 * @return the departmentDAO
	 */
	public DepartmentDAO getDepartmentDAO()
	{
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

}
