package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.util.Date;

public class CityBean implements IBaseBean {

	private int cityId;
	private String cityName;
	
	private Date created;
	private int createdBy;
	
	private Date modified;
	private int modifiedBy;
	
	public CityBean() {
	}
	
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
		this.cityId = 0;
		this.cityName = "";
		this.cityName = null;
		this.created = null;
		this.createdBy = 0;
		this.modified = null;
		this.modifiedBy = 0;
	}
	
	@Override
	public boolean equals(Object cityBean) {

		if (this.cityId == ((CityBean) cityBean).getCityId())
			return true;
		
		if (this.cityName == ((CityBean) cityBean).getCityName()) 
			return true;
		
		return false;
		
	}
	
}
