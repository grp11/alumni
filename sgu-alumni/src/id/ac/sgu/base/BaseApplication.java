package id.ac.sgu.base;

import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.ui.registration.CreateNewUserPage;
import id.ac.sgu.utility.database.PostgresDB;
import id.ac.sgu.utility.login.LoginService;

import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.CryptedUrlWebRequestCodingStrategy;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.PackageName;

public class BaseApplication extends AuthenticatedWebApplication
{
	private static Logger logger = Logger.getLogger(BaseApplication.class);

	public static PostgresDB dbConnect = new PostgresDB();
	public LoginService loginService;

	@Override
	public Class<? extends Page> getHomePage()
	{
		BaseSession session = (BaseSession) Session.get();

		if (session != null && session.isSignedIn())
		{
			return MainPage.class;
		}

		return LoginPage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass()
	{
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass()
	{
		return BaseSession.class;
	}

	@Override
	public void init()
	{
		super.init();

		addComponentInstantiationListener(new SpringComponentInjector(this));

		mount("/login", PackageName.forPackage(LoginPage.class.getPackage()));
		mount("/registration", PackageName.forPackage(CreateNewUserPage.class.getPackage()));

		IResourceSettings resourceSettings = getResourceSettings();
		resourceSettings.addResourceFolder("id/ac/sgu/base");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/registration");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/login");

		resourceSettings.addResourceFolder("id/ac/sgu/ui/alumni");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/alumni/profile");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/alumni/comment");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/alumni/search");

		resourceSettings.addResourceFolder("id/ac/sgu/ui/admin");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/admin/batch");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/admin/department");
		resourceSettings.addResourceFolder("id/ac/sgu/ui/admin/mapping");

		resourceSettings.addResourceFolder("id/ac/sgu/ui/main");
		resourceSettings.setResourceStreamLocator(new PathStripperLocator());
	}

	public LoginService getLoginService()
	{
		return loginService;
	}

	public void setLoginService(LoginService loginService)
	{
		this.loginService = loginService;
	}

	@Override
	protected IRequestCycleProcessor newRequestCycleProcessor()
	{
		return new WebRequestCycleProcessor()
		{
			protected IRequestCodingStrategy newRequestCodingStrategy()
			{
				return new CryptedUrlWebRequestCodingStrategy(new WebRequestCodingStrategy());
			}

		};
	}

}
