package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.exception.alumni.InvalidDepartmentException;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminDepartmentCreateConfPage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminDepartmentCreatePage.class);

	private DepartmentBean bean;

	public AdminDepartmentCreateConfPage(PageParameters pageParameters) {

		if (pageParameters != null)
			bean = (DepartmentBean) pageParameters.get("department");

		if (bean == null)
			bean = new DepartmentBean();

		CompoundPropertyModel<DepartmentBean> model = new CompoundPropertyModel<DepartmentBean>(bean);

		AdminDepartmentCreateConfForm adccp = new AdminDepartmentCreateConfForm("adminDepartmentCreateConfForm", model);

		add(initNavigationBorder(adccp));

	}

	private class AdminDepartmentCreateConfForm extends Form<DepartmentBean> {

		private Link<Object> btnCancel;
		private Button btnConfirm;


		public AdminDepartmentCreateConfForm(String id, final IModel<DepartmentBean> model) {
			super(id, model);

			btnCancel = new Link<Object>("btnCancel") {

				@Override
				public void onClick() {
					PageParameters param = new PageParameters();
					param.put("department", bean);
					setResponsePage(new AdminDepartmentCreatePage(param));
				}
			};

			btnConfirm = new Button("btnConfirm") {
				@Override
				public void onSubmit() {
					try {
						if (alumniService.createNewDepartment(bean) == Cons.CREATE_DEP_SUCCESS)
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminDepartmentCreatePage.class);
							setResponsePage(new ResultPage(param));
						}
					}
					catch (InvalidDepartmentException e)
					{
						if (e.getMessage() != null) {
							getWebSession().error(e.getMessage());
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						if (e.getMessage() != null) {
							getWebSession().error(e.getMessage());
						}
					}
				}
			};

			add(new FeedbackPanel("feedbackPanel"));
			add(new Label("departmentName"));
			add(new Label("departmentAlias"));

			add(btnCancel);
			add(btnConfirm);

		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent component) {
			if (component != null) {
				component.onSubmit();
			}
		}
	}
}
