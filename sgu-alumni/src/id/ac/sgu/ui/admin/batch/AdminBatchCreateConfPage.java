package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.ui.admin.department.AdminDepartmentEditPage;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.utility.Cons;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AdminBatchCreateConfPage extends BasePage {

	private BatchBean bean;

	private AdminBatchCreateConfPage() {
		// Not allowed
	}

	public AdminBatchCreateConfPage(PageParameters pageParameters) {

		AdminBatchCreateConfForm adminBatchCreateConfForm = new AdminBatchCreateConfForm("adminBatchCreateConfForm", pageParameters);
		add(initNavigationBorder(adminBatchCreateConfForm));

	}

	private class AdminBatchCreateConfForm extends Form<BatchBean> {

		private String departmentName;
		private String facultyName;
		private String newBatchYear;

		private Label lblNewBatchYear;

		private Label lblDepartmentName;
		private Label lblFacultyName;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		public AdminBatchCreateConfForm(String id, PageParameters param)
		{
			super(id);

			bean = (BatchBean) param.get("batch");

			if (bean != null) {

				departmentName = bean.getDepartmentName();
				facultyName = bean.getFacultyName();
				newBatchYear = Integer.toString(bean.getBatchYear());

			}

			lblNewBatchYear = new Label("newBatchYear" , newBatchYear);
			lblDepartmentName = new Label("departmentName" , departmentName);
			lblFacultyName = new Label("facultyName", facultyName);

			btnConfirm = new Button("btnConfirm") {

				@Override
				public void onSubmit() {

					try {
						if (alumniService.createNewBatch(bean) == Cons.CREATE_BATCH_FAILURE)
							throw new Exception("Ops sorry, you cannot add the batch");
						setResponsePage(MainPage.class);
					} catch(Exception e) {
						e.printStackTrace();
						if (e.getMessage() != null) {
							getWebSession().error(e.getMessage());
						}
					}

				}

			};

			btnCancel = new Link<Object>("btnCancel") {

				@Override
				public void onClick() {
					setResponsePage(AdminBatchCreatePage.class);
				}

			};

			add(new FeedbackPanel("feedbackPanel"));
			add(lblNewBatchYear);
			add(lblDepartmentName);
			add(lblFacultyName);
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
