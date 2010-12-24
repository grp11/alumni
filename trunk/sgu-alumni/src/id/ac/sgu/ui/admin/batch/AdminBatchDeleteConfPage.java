package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.ui.main.ResultPage;
import id.ac.sgu.utility.Cons;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class AdminBatchDeleteConfPage extends BasePage
{

//	private static Logger logger = Logger.getLogger(AdminBatchDeleteConfPage.class);

	private BatchBean bean;

	public AdminBatchDeleteConfPage(PageParameters pageParameters)
	{
		if (null != pageParameters)
		{
			bean = (BatchBean) pageParameters.get("batch");
		}

		if (null == bean)
		{
			bean = new BatchBean();
		}

		AdminBatchDeleteConfForm adminDeleteBatchConfForm = new AdminBatchDeleteConfForm("adminBatchDeleteConfForm", pageParameters);

		add(initNavigationBorder(adminDeleteBatchConfForm));

	}

	private class AdminBatchDeleteConfForm extends Form<BatchBean>
	{
		private String batchYear;

		private Label lblBatchYear;
		private Button btnConfirm;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;

		public AdminBatchDeleteConfForm(String id, PageParameters param)
		{
			super(id);

			bean = (BatchBean) param.get("batch");

			if (null != bean)
			{
				batchYear = String.valueOf(bean.getBatchYear());
			}

			lblBatchYear = new Label("batchYear", batchYear);

			btnConfirm = new Button("btnConfirm")
			{
				@Override
				public void onSubmit() {
					try
					{
						if (alumniService.deleteBatch(bean) == Cons.CREATE_BATCH_FAILURE)
						{
							getWebSession().info("Ops sorry, you cannot add this batch. Because it has already been existed in the database");
						}
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminBatchDeletePage.class);
							setResponsePage(new ResultPage(param));
						}
					}
					catch(Exception e)
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
					setResponsePage(new AdminBatchDeletePage(param));
				}
			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(lblBatchYear);
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
