package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;

import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AdminDepartmentDeleteConfPage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminDepartmentDeleteConfPage.class);

	private DepartmentBean bean;

	public AdminDepartmentDeleteConfPage(PageParameters pageParameters)
	{
		if (null != pageParameters)
		{
			bean = (DepartmentBean) pageParameters.get("department");
		}

		if (null == bean)
		{
			bean = new DepartmentBean();
		}

		AdminDepartmentDeleteConfForm adminDepartmentDeleteConfForm = new AdminDepartmentDeleteConfForm("adminDepartmentDeleteConfForm", pageParameters);

		add(initNavigationBorder(adminDepartmentDeleteConfForm));

	}

	private class AdminDepartmentDeleteConfForm extends Form<DepartmentBean>
	{
		private String delDepartmentName;
		private Label lblDepartmentName;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		private FeedbackPanel feedbackPanel;

		public AdminDepartmentDeleteConfForm(String id, PageParameters pageParameters)
		{
			super(id);

			bean = (DepartmentBean) pageParameters.get("department");

			if (null != bean)
			{
				delDepartmentName = bean.getDepartmentName();
			}

			lblDepartmentName = new Label("departmentName", delDepartmentName);
			add(lblDepartmentName);

			btnConfirm = new Button("btnConfirm")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						if (alumniService.deleteDepartment(bean) == Cons.DELETE_DEP_FAILURE)
						{
							getWebSession().info("Sorry, you cannot delete this department. Because this department was used by batch year / or user");
							PageParameters param = new PageParameters();
							param.put("department", bean);
							setResponsePage(new AdminDepartmentDeletePage(param));
						}
						else {
							PageParameters param = new PageParameters();
							param.put("again", AdminDepartmentDeletePage.class);
							setResponsePage(new ResultPage(param));
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						if (null != e.getMessage())
						{
							getWebSession().error(e.getMessage());
						}
					}
				}
			};

			btnCancel = new Link<Object>("btnCancel")
			{
				@Override
				public void onClick() {
					PageParameters param = new PageParameters();
					param.put("department", bean);
					setResponsePage(new AdminDepartmentDeletePage(param));
				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(btnCancel);
			add(btnConfirm);
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
