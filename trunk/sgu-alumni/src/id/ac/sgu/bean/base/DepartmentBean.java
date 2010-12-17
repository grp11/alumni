package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

public class DepartmentBean implements IBaseBean, Serializable {

	private String departmentName;
	private int departmentId;
	private String departmentAlias;
	
	private String newName;
	private String newAlias;
	
	private String facultyName;
	
	private Date created;	
	private int createdBy;
	
	private Date modified;
	private int modifiedBy;
	
	
	public DepartmentBean() {
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
	 * @param departmentAlias the departmentAlias to set
	 */
	public void setDepartmentAlias(String departmentAlias) {
		this.departmentAlias = departmentAlias;
	}

	/**
	 * @return the departmentAlias
	 */
	public String getDepartmentAlias() {
		return departmentAlias;
	}

	/**
	 * @param newName the newName to set
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}

	/**
	 * @return the newName
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * @param newAlias the newAlias to set
	 */
	public void setNewAlias(String newAlias) {
		this.newAlias = newAlias;
	}

	/**
	 * @return the newAlias
	 */
	public String getNewAlias() {
		return newAlias;
	}

	/**
	 * @param facultyName the facultyName to set
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	/**
	 * @return the facultyName
	 */
	public String getFacultyName() {
		return facultyName;
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
		
		this.departmentId = 0;
		this.departmentName = null;
		this.departmentAlias = null;
		
		this.departmentName = null;
		
		this.created = null;
		this.createdBy = 0;
		
		this.modified = null;
		this.modifiedBy = 0;
		
	}

}
