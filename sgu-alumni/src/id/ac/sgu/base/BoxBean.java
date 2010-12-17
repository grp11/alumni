package id.ac.sgu.base;

import id.ac.sgu.bean.IBaseBean;

public class BoxBean implements IBaseBean {

	private String boxId;
	private Object boxName;
	private Object boxValue;
	private Integer boxType;	
	
	public BoxBean() {
	}
	
	public BoxBean(String boxName, Object boxValue, Integer boxType) {
		this.boxName = boxName;
		this.boxValue = boxValue;
		this.boxType = boxType;
	}	
	
	/**
	 * @return the boxId
	 */
	public String getBoxId() {
		return boxId;
	}

	/**
	 * @param boxId the boxId to set
	 */
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	/**
	 * @return the boxName
	 */
	public Object getBoxName() {
		return boxName;
	}

	/**
	 * @param boxName the boxName to set
	 */
	public void setBoxName(Object boxName) {
		this.boxName = boxName;
	}

	/**
	 * @return the boxValue
	 */
	public Object getBoxValue() {
		return boxValue;
	}

	/**
	 * @param boxValue the boxValue to set
	 */
	public void setBoxValue(Object boxValue) {
		this.boxValue = boxValue;
	}

	/**
	 * @return the boxType
	 */
	public Integer getBoxType() {
		return boxType;
	}

	/**
	 * @param boxType the boxType to set
	 */
	public void setBoxType(Integer boxType) {
		this.boxType = boxType;
	}

	@Override
	public void clear() {
		this.boxId = "";
		this.boxName = null;
		this.boxValue = null;
		this.boxType = null;
	}

}
