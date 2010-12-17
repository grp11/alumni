package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

public class BatchBean implements IBaseBean, Serializable {

	private static final long serialVersionUID = 8978232990989830282L;
	
	private int batchYear;
	private int batchId;
	
	private String departmentName;
	private String facultyName;

	private int departmentId;
	private int facultyId;

	private int batchMappingId;
	
	private Date created;
	private int createdBy;
	
	private Date modified;
	private int modifiedBy;
	
	
	public BatchBean(){
	}
	
	/**
	 * @return the batchYear
	 */
	public int getBatchYear() {
		return batchYear;
	}

	/**
	 * @param batchYear the batchYear to set
	 */
	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
	}

	/**
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}

	/**
	 * @param facultyName the facultyName to set
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the facultyId
	 */
	public int getFacultyId() {
		return facultyId;
	}

	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	/**
	 * @return the batchMappingId
	 */
	public int getBatchMappingId() {
		return batchMappingId;
	}

	/**
	 * @param batchMappingId the batchMappingId to set
	 */
	public void setBatchMappingId(int batchMappingId) {
		this.batchMappingId = batchMappingId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * @return the modifiedBy
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public void clear() {
		
		this.batchYear = 0;
		this.batchId = 0;
		
		this.departmentName = null;
		this.facultyName = null;
		
		this.departmentId = 0;
		this.facultyId = 0;
		
		this.created = null;
		this.createdBy = 0;
		
		this.modified = null;
		this.modifiedBy = 0;
	
		
	}
	
}
