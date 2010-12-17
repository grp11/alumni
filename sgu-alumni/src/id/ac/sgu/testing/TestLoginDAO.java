package id.ac.sgu.testing;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.bl.login.LoginBL;
import id.ac.sgu.dao.login.LoginDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.security.HashValues;

public class TestLoginDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LoginDAO loginDAO = new LoginDAO();

		HashValues hv = new HashValues();

		String username = "admin";
		String password = "pwPassword";

		// System.out.println("loginDAO -- salt is: " +
		// loginDAO.getSalt("admin"));
		String salt = loginDAO.getSalt(username);

		String combination = password + salt;
		String hashValue = hv.hashValue(combination);


//		System.out.println("loginDAO: "+loginDAO.userVerify(username, hashValue));

		
		LoginBL loginBL = new LoginBL();
//		System.out.println("loginBL: " + loginBL.signin(username, hashValue));
		int loginStat = loginBL.signin(username, hashValue);
		
		UserBean userBean = null;
		
		switch(loginStat) {
		case Cons.LOGIN_FAILED:
			break;
		case Cons.LOGIN_SUCCESS:
			System.out.println("SUCCESSFULLY LOGIN");
			
			userBean = loginDAO.userBean(username, hashValue);
			
			if (userBean == null) {
				System.out.println("FAILURE TO GET USER BEAN");
				
				
			}
			else {
				System.out.println("SUCCESS TO GET USER BEAN");
				
				
			}
			
			break;
		}
		
	}

}
