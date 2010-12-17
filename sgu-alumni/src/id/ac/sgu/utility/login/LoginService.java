package id.ac.sgu.utility.login;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.bl.login.LoginBL;
import id.ac.sgu.dao.login.LoginDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.security.HashValues;

import org.apache.log4j.Logger;

public class LoginService {

	private static Logger logger = Logger.getLogger(LoginService.class);

	private LoginBL loginBL;
	private LoginDAO loginDAO;

	public UserBean findMatchUser(String username, String password) {

		HashValues hv = new HashValues();

		String salt = loginDAO.getSalt(username);


		logger.info("Username: " + username);
		logger.info("password: " + password);
		logger.info("HASHED PASSWORD: " + hv.hashValue(password+salt));


		int result = this.loginBL.signin(username, hv.hashValue(password+salt));

		UserBean userBean =  null;


		switch(result) {
		case Cons.LOGIN_FAILED:
			logger.warn("LoginService.findMatchUser.LOGIN_FAILED");
			break;
		case Cons.LOGIN_SUCCESS:
			logger.info("LoginService.findMatchUser.LOGIN_SUCCESS");
			userBean = loginDAO.userBean(username, hv.hashValue(password+salt));
			break;
		}
		result = 0;

		return userBean;
	}

	/**
	 * @return the loginBL
	 */
	public LoginBL getLoginBL() {
		return loginBL;
	}

	/**
	 * @param loginBL the loginBL to set
	 */
	public void setLoginBL(LoginBL loginBL) {
		this.loginBL = loginBL;
	}

	/**
	 * @return the loginDAO
	 */
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	/**
	 * @param loginDAO the loginDAO to set
	 */
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

}
