package id.ac.sgu.ui.admin.mapping;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AdminDeleteAlumniUserConfPage extends BasePage {

	private UserBean bean;

	public AdminDeleteAlumniUserConfPage(PageParameters pageParameters)
	{
		if (null != pageParameters)
		{
			bean = (UserBean) pageParameters.get("user");
		}

		if (null == bean)
		{
			bean = new UserBean();
		}

		AdminDeleteAlumniUserConfForm adminDeleteAlumniUserConfForm = new AdminDeleteAlumniUserConfForm(
				"adminDeleteAlumniUserConfForm", pageParameters);

		add(initNavigationBorder(adminDeleteAlumniUserConfForm));
	}


	private class AdminDeleteAlumniUserConfForm extends Form<UserBean>
	{
		private String username;
		private Label lblUsername;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		private FeedbackPanel feedbackPanel;

		public AdminDeleteAlumniUserConfForm(String id, PageParameters pageParameters)
		{
			super(id);

			bean = (UserBean) pageParameters.get("user");

			if (null != bean)
			{
				username = bean.getUsername();
			}

			lblUsername = new Label("username", username);

			btnConfirm = new Button("btnConfirm")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						if (userService.deleteUser(bean) == Cons.DELETE_USR_FAILURE)
						{
							getWebSession().info("Sorry, cannot delete user. Please check your input");
						}
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminDeleteAlumniUserPage.class);
							setResponsePage(new ResultPage(param));
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						if (e.getMessage() != null)
						{
							getWebSession().error(e.getMessage());
						}
					}
				}
			};

			btnCancel = new Link<Object>("btnCancel")
			{
				@Override
				public void onClick()
				{
					PageParameters param = new PageParameters();
					param.put("user", bean);
					setResponsePage(new AdminDeleteAlumniUserPage(param));
				}
			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(lblUsername);


			add(btnConfirm);
			add(btnCancel);
			add(feedbackPanel);

		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent component)
		{
			if (component != null)
			{
				component.onSubmit();
			}
		}
	}

}
