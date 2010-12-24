package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminBatchCreatePage extends BasePage
{
//	private static Logger logger = Logger.getLogger(AdminBatchCreatePage.class);

	private BatchBean bean;

	public AdminBatchCreatePage()
	{

		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminBatchCreateForm AdminBatchCreateForm = new AdminBatchCreateForm(
				"adminBatchCreateForm", model, null);

		add(initNavigationBorder(AdminBatchCreateForm));

	}

	public AdminBatchCreatePage(PageParameters pageParameters)
	{
		CompoundPropertyModel<BatchBean> model = null;

		if (pageParameters != null)
		{

			bean = (BatchBean) pageParameters.get("batch");

			if (bean != null)
			{
				model = new CompoundPropertyModel<BatchBean>(bean);
			}

		}

		AdminBatchCreateForm adminBatchCreateForm = new AdminBatchCreateForm(
				"adminBatchCreateForm", model, pageParameters);
		add(initNavigationBorder(adminBatchCreateForm));

	}

	private class AdminBatchCreateForm extends Form<BatchBean>
	{
		private TextField<Integer> tfBatchYear;

		private Button btnSubmit;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;

		public AdminBatchCreateForm(String id, final IModel<BatchBean> model, PageParameters pageParameters)
		{

			super(id, model);

			tfBatchYear = new TextField<Integer>("batchYear");


			btnSubmit = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;

						if (null == tfBatchYear.getModelObject())
						{
							getWebSession().info("Masukkan tahun ajaran");
							state = false;
						}

						if (tfBatchYear.getModelObject() >= 2100 &&
							tfBatchYear.getModelObject() <= 2000)
						{
							getWebSession().info("Masukkan tahun ajaran antara 2000 sampai 2100");
							state = false;
						}

						if (state)
						{
							PageParameters param = new PageParameters();
							param.put("batch", bean);

							setResponsePage(new AdminBatchCreateConfPage(param));
						}

					}
					catch (Exception e)
					{
						e.printStackTrace();
						getWebSession().error(e.getMessage());
					}
				}
			};

			btnCancel = new Link<Object>("btnCancel")
			{
				@Override
				public void onClick() {
					PageParameters param = new PageParameters();
					param.put("batch", bean);
					setResponsePage(new AdminBatchCreatePage(param));
				}
			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(tfBatchYear);

			add(btnCancel);
			add(btnSubmit);
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
