package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AdminBatchCreateConfPage extends BasePage {

//	private static Logger logger = Logger.getLogger(AdminBatchCreateConfPage.class);

	private BatchBean bean;

	public AdminBatchCreateConfPage(PageParameters pageParameters)
	{
		AdminBatchCreateConfForm adminBatchCreateConfForm = new AdminBatchCreateConfForm("adminBatchCreateConfForm", pageParameters);
		add(initNavigationBorder(adminBatchCreateConfForm));
	}

	private class AdminBatchCreateConfForm extends Form<BatchBean> {

		private static final long serialVersionUID = 1L;

		private String departmentName;
		private String facultyName;
		private String newBatchYear;

		private Label lblNewBatchYear;

		private Label lblDepartmentName;
		private Label lblFacultyName;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		private FeedbackPanel feedbackPanel;

		public AdminBatchCreateConfForm(String id, PageParameters param)
		{
			super(id);

			bean = (BatchBean) param.get("batch");

			if (bean != null) {
				newBatchYear = Integer.toString(bean.getBatchYear());
			}

			lblNewBatchYear = new Label("newBatchYear" , newBatchYear);

			btnConfirm = new Button("btnConfirm") {

				@Override
				public void onSubmit()
				{
					try
					{
						if (alumniService.createNewBatch(bean) == Cons.CREATE_BATCH_FAILURE)
						{
							getWebSession().info("Ops sorry, you cannot add this batch. It has already been existed in database");
						}
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminBatchCreateConfPage.class);

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
					param.put("batch", bean);
					setResponsePage(new AdminBatchCreatePage(param));
				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(lblNewBatchYear);
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
