package id.ac.sgu.base;

import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.bl.login.LoginBL;
import id.ac.sgu.dao.login.LoginDAO;
import id.ac.sgu.ui.main.NavigationBorder;
import id.ac.sgu.utility.factory.DAOLocator;
import id.ac.sgu.utility.login.LoginService;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;

public class BaseSession extends AuthenticatedWebSession implements HttpSessionBindingListener {
	
	private static final long serialVersionUID = 8129565886191745051L;
	
	private static Logger logger = Logger.getLogger(BaseSession.class);

	private UserBean userBean;
	
	private NavigationBorder navigationBorder;
	
	private volatile String salt;
	
	public BaseSession(Request request) {
		super(request);
		super.setLocale(Locale.UK);
	}

	@Override
	public boolean authenticate(String username, String password) {
		logger.info("BaseSession.authenticate -- ENTER");
		
		logger.info("username -- session: " + username);
		logger.info("password -- session: " + password);
		
		
		
		
		// Check username and password
		return true;
	}
	
	// acgi security
	private void setAuthentication(Authentication auth) {
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@Override
	public Roles getRoles() {
		logger.info("into here --");
		if (isSignedIn()) {
			// If the user is signed in, they have these roles
			return new Roles(Roles.ADMIN);
		}
		return null;
	}

	/**
	 * @param userBean the userBean to set
	 */
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	/**
	 * @return the userBean
	 */
	public UserBean getUserBean() {
		return userBean;
	}

	/**
	 * @param navigationBorder the navigationBorder to set
	 */
	public void setNavigationBorder(NavigationBorder navigationBorder) {
		this.navigationBorder = navigationBorder;
	}

	/**
	 * @return the navigationBorder
	 */
	public NavigationBorder getNavigationBorder() {
		return navigationBorder;
	}

	public static BaseSession getWebSession() {
		logger.info("getWebSession() is invoked");
		return (BaseSession) Session.get();
	}
	
	
	public void overrideSession() {
		logger.info("BaseSession.overrideSession() -- ENTER --");
		
		getSessionStore().invalidate(RequestCycle.get().getRequest());
		bind();
		
		logger.info("BaseSession.overrideSession() -- EXIT --");	
	}
	
	
	@Override
	public void signOut() {
	//	setAuthentication(null);
		
		super.signOut();

		// Safe
		invalidate();
		// forced
		//invalidateNow();
		
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		
		if (this.userBean != null) {
			try {
				logger.info("----------------------> " + this.userBean.getUsername());
				
				DAOLocator daoLocator = new DAOLocator();
				daoLocator.setLoginDAO(new LoginDAO());
				LoginBL userBL = new LoginBL();
				userBL.setDaoLocator(daoLocator);
				LoginService loginService = new LoginService();
				loginService.setLoginBL(userBL);
				
				userBean.setLastLogin(new Date());
				logger.info("PASS THOSE THINGS");
//				loginService.updateLastLoginDate(userBean);
//				loginService.updateUserLog(userBean.getCode(), false);
				userBean = null;
			}
			catch (Exception e)	{
				e.printStackTrace();
				logger.error("ERROR", e);
			}
		}	
	}
	
	
	
}
