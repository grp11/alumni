package id.ac.sgu.utility.exception.alumni;

import id.ac.sgu.bean.base.DepartmentBean;

public class InvalidDepartmentException extends Exception {

	private static final long serialVersionUID = -1694847148634870405L;
	
	private DepartmentBean departmentBean;
	
	public InvalidDepartmentException(){
	}
	
	public InvalidDepartmentException(DepartmentBean departmentBean) {
		super("Invalid department bean");
		this.setDepartmentBean(departmentBean);
	}
	
	public InvalidDepartmentException(String message) {
		super(message);		
	}

	
	public InvalidDepartmentException(Throwable cause) {
		super(cause);
	}
	
	public InvalidDepartmentException(String message, Throwable cause) {
		super(message,cause);
	}
	
	/**
	 * @param departmentBean the departmentBean to set
	 */
	public void setDepartmentBean(DepartmentBean departmentBean) {
		this.departmentBean = departmentBean;
	}

	/**
	 * @return the departmentBean
	 */
	public DepartmentBean getDepartmentBean() {
		return departmentBean;
	}
	
}
