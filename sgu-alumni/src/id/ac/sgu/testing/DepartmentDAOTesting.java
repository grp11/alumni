package id.ac.sgu.testing;

import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.dao.base.DepartmentDAO;

import org.apache.log4j.Logger;

public class DepartmentDAOTesting {
	private static Logger logger = Logger.getLogger(DepartmentDAOTesting.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long binitTime = System.currentTimeMillis();	
		long e1 = System.currentTimeMillis();
		long e0 = System.currentTimeMillis();
	
		DepartmentDAO depDAO = new DepartmentDAO();
		System.out.println("Find all: " + depDAO.findAll());

//		System.out.println("Last id from department: " + depDAO.getLastId());
		
		DepartmentBean departmentBean = new DepartmentBean();
		departmentBean.setDepartmentName("Information Tech");
		departmentBean.setDepartmentAlias("IT");
		
		DepartmentBean beanCondition = new DepartmentBean();
		beanCondition.setDepartmentName("Information Technology");
		
	//	logger.info("delete status: " + depDAO.deleteRecent(departmentBean));
	//	logger.info("findByName, the id is " + depDAO.findBy("name", "Information Technology").getDepartmentId());
	//	logger.info("update status: " + depDAO.updateRecent(departmentBean, beanCondition));
		departmentBean.clear();
		beanCondition.clear();
		/*		for (int i = 0 ; i < 1001 ; i++) {
			PostgresDB pdb = new PostgresDB();
			
			pdb.getConnection();
			try {
				pdb.closeConnection();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
*/		e1 = System.currentTimeMillis();
		logger.info("init() -- until finish authenticationManager time is " + (e1-e0)+" | accumulated: "+(e1-binitTime));
		
		
		
	}

}
