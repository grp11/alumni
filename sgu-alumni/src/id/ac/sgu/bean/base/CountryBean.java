package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.util.Date;
import java.util.List;

public class CountryBean implements IBaseBean {

	private int countryId;
	private String countryName;
	private Date created;
	private int createdBy;
	private Date modified;
	private int modifiedBy;
	
	private List<CityBean> cityList;
	
	public CountryBean() {
	}

	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	/**
	 * @return the cityList
	 */
	public List<CityBean> getCityList() {
		return cityList;
	}

	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(List<CityBean> cityList) {
		this.cityList = cityList;
	}

	@Override
	public void clear() {
		
		this.countryId = 0;
		this.countryName = null;
		this.created = null;
		this.createdBy = 0;
		this.modified = null;
		this.modifiedBy = 0;
		
		this.cityList.clear();
		
	}

	public boolean equals(Object countryBean) {
		
		if (this.countryId == ((CountryBean) countryBean).getCountryId() )
			return true;

		if (this.countryName == ((CountryBean) countryBean).getCountryName())
			return true;
		
		return false;

	}
	
}
