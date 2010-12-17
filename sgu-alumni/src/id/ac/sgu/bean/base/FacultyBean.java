package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author exodream
 * */
public class FacultyBean implements IBaseBean, Serializable {

	private static final long serialVersionUID = 3504559414204034996L;

	private String facultyName;
	private int facultyId;
	private String facultyAlias;
	
	private Date created;
	private int createdBy;
	
	private Date modified;
	private int modifiedBy;
	
	public FacultyBean() {
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
	 * @param facultyAlias the facultyAlias to set
	 */
	public void setFacultyAlias(String facultyAlias) {
		this.facultyAlias = facultyAlias;
	}

	/**
	 * @return the facultyAlias
	 */
	public String getFacultyAlias() {
		return facultyAlias;
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
	
		this.facultyId = 0;
		this.facultyName = "";
		this.facultyAlias = "";
		
		this.facultyName = null;
		this.facultyAlias = null;
		
		this.created = null;
		this.createdBy = 0;
		
		this.modified = null;
		this.modifiedBy = 0;
		
	}	
	
}
