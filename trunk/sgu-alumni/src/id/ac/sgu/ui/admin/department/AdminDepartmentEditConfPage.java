package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AdminDepartmentEditConfPage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminDepartmentEditConfPage.class);

	private DepartmentBean bean;

	public AdminDepartmentEditConfPage(PageParameters pageParameters)
	{
		AdminDepartmentEditConfForm adminDepartmentEditConfForm = new AdminDepartmentEditConfForm("adminDepartmentEditConfForm", pageParameters);
		add(initNavigationBorder(adminDepartmentEditConfForm));
	}

	private class AdminDepartmentEditConfForm extends Form<DepartmentBean>
	{

		private Label lblOldDepartmentName;
		private Label lblOldDepartmentAlias;
		private Button btnConfirm;
		private Link<Object> btnCancel;

		private Label lblNewDepartmentName;
		private Label lblNewDepartmentAlias;

		private String oldDepartmentName;
		private String oldDepartmentAlias;
		private String newDepartmentName;
		private String newDepartmentAlias;


		public AdminDepartmentEditConfForm(String id, final PageParameters pageParameters)
		{
			super(id);

			bean = (DepartmentBean) pageParameters.get("department");

			if (bean != null) {
				newDepartmentAlias = bean.getNewAlias();
				newDepartmentName = bean.getNewName();
				oldDepartmentName = bean.getDepartmentName();
				oldDepartmentAlias = bean.getDepartmentAlias();
			}

			lblNewDepartmentName = new Label("newDepartmentName", newDepartmentName);
			lblNewDepartmentAlias = new Label("newDepartmentAlias", newDepartmentAlias);
			lblOldDepartmentName = new Label("oldDepartmentName", oldDepartmentName);
			lblOldDepartmentAlias = new Label("oldDepartmentAlias", oldDepartmentAlias);

			btnConfirm = new Button("btnConfirm") {

				@Override
				public void onSubmit() {
					try {

						if (alumniService.updateDepartment(bean) == Cons.UPDATE_DEP_FAILURE)
							throw new Exception("Sorry, you cannot edit department with wrong values");
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminDepartmentEditPage.class);
							setResponsePage(new ResultPage(param));
						}
					} catch(Exception e)
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
					param.put("department", bean);
					setResponsePage(new AdminDepartmentEditPage(param));
				}

			};

		//	add(lblNewDepartmentAlias);
			add(lblNewDepartmentName);
		//	add(lblOldDepartmentAlias);
			add(lblOldDepartmentName);
			add(btnConfirm);
			add(btnCancel);
			add(new FeedbackPanel("feedbackPanel"));

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
