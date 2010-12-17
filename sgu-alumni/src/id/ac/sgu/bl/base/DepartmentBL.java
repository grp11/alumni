package id.ac.sgu.bl.base;

import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

public class DepartmentBL extends BaseBL {

	private DAOLocator daoLocator;

	public int createDepartment(DepartmentBean departmentBean) {
		int result = Cons.CREATE_DEP_FAILURE;	
		
		result = daoLocator.getDepartmentDAO().CreateNew(departmentBean);
		if (result == 1)
			result = Cons.CREATE_DEP_SUCCESS;
		
		return result;
	}
	
	public int deleteDepartment(DepartmentBean departmentBean) {
		int result = Cons.DELETE_DEP_FAILURE;
		
		result = daoLocator.getDepartmentDAO().deleteRecent(departmentBean);
		if (result == 1)
			result = Cons.DELETE_DEP_SUCCESS;
		
		return result;
	}

	public int updateDepartment(DepartmentBean toBeUpdated) {
		int result = Cons.UPDATE_DEP_FAILURE;
		
		result = daoLocator.getDepartmentDAO().updateRecent(toBeUpdated);
		if (result == 1)
			result = Cons.UPDATE_DEP_SUCCESS;		
		
		return result;
	}
	
	public boolean validateDepartmentLength(DepartmentBean departmentBean) {
		boolean valid = true;
		
		if (departmentBean.getDepartmentName().length() <= Cons.MAX_DEP_NAME_LENGTH) {			
			valid = true;
		}
		
		if (departmentBean.getDepartmentAlias().length() > Cons.MAX_DEP_ALIAS_LENGTH) {		
			return valid;
		}
		
		return valid;
	}
	
	public int compare(DepartmentBean bean1, DepartmentBean bean2) {
		int result = 0;

		if (bean1 == null)
			throw new NullPointerException("Received null on bean1");

		if (bean2 == null)
			throw new NullPointerException("Received null on bean2");
		
		if (bean1.getDepartmentName().equalsIgnoreCase(bean2.getDepartmentName()) ) {			
			result++;
		}

		if (bean1.getDepartmentAlias().equalsIgnoreCase(bean2.getDepartmentAlias())) {
			result++;
		} 
		
		return result;
		
	}
	
	/**
	 * @param daoLocator the daoLocator to set
	 */
	public void setDaoLocator(DAOLocator daoLocator) {
		this.daoLocator = daoLocator;
	}

	/**
	 * @return the daoLocator
	 */
	public DAOLocator getDaoLocator() {
		return daoLocator;
	}
}
