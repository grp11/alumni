package id.ac.sgu.ui.login;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.base.BaseSession;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.ui.main.NavigationBorder;
import id.ac.sgu.ui.registration.CreateNewUserPage;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class LoginPage extends BasePage
{
	private static Logger logger = Logger.getLogger(LoginPage.class);

	private BaseSession baseSession = (BaseSession) getSession();

	public LoginPage()
	{
		if (baseSession.getUserBean() != null)
		{
			if ( baseSession.getUserBean().getUsername() != null )
			{
				logger.info("base session: " + baseSession.getUserBean().getUsername() );
				setResponsePage(MainPage.class);
			}
		}

		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(
				new UserBean());
		LoginForm loginForm = new LoginForm("loginForm", model);

		add(loginForm);
	}

	public LoginPage(final PageParameters parameters)
	{
	}

	private class LoginForm extends Form<UserBean>
	{
		public LoginForm(String id, final IModel<UserBean> model)
		{
			super(id, model);

			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");

			TextField<UserBean> code = new TextField<UserBean>("username");
			code.clearInput();

			PasswordTextField password  = new PasswordTextField("password");

			password.clearInput();
			password.setRequired(true);

			final Button loginButton = new Button("loginButton")
			{
				@Override
				public void onSubmit()
				{
					super.onSubmit();

					String username = model.getObject().getUsername();
					String password = model.getObject().getPassword();

					boolean state = true;

					if (!validateLetter(username))
					{
						Session.get().info("invalid password or username");
						state = false;
					}

					if (state)
					{
						UserBean userBean = loginService.findMatchUser(username, password);

						if (userBean != null)
						{
							baseSession.signIn(model.getObject().getUsername(), model.getObject().getPassword());

							if (!continueToOriginalDestination() && baseSession.isSignedIn())
							{
								Form initForm = new Form("InitForm");

								NavigationBorder navigationBorder = new NavigationBorder("navigationBorder", userBean);
								navigationBorder.add(initForm);
								navigationBorder.setAttachedForm(initForm);

								baseSession.setNavigationBorder(navigationBorder);
								baseSession.setUserBean(userBean);

								setResponsePage(baseSession.getApplication().getHomePage());
							}
							else
							{
								Session.get().info("invalid password or username");
							}
						}
						else
						{
							Session.get().info("invalid password or username");
						}
					}

					setRedirect(true);

				}
			};

			final Link<Object> btnRegister = new Link<Object>("registerButton")
			{
				@Override
				public void onClick()
				{
					setResponsePage(CreateNewUserPage.class);
				}
			};

			add(feedbackPanel);
			add(code);
			add(password);
			add(loginButton);
			add(btnRegister);
		}

		private static final long serialVersionUID = -5183634891666062006L;

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent)
		{
			if (submittingComponent != null)
			{
				submittingComponent.onSubmit();
			}
		}

	}

}
