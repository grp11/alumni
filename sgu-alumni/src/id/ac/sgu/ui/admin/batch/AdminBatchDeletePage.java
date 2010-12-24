package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.utility.Cons;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminBatchDeletePage extends BasePage
{
//	private static Logger logger = Logger.getLogger(AdminBatchDeletePage.class);

	private BatchBean bean;

	public AdminBatchDeletePage()
	{

		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminBatchDeleteForm AdminBatchCreateForm = new AdminBatchDeleteForm(
				"adminBatchDeleteForm", model, null);

		add(initNavigationBorder(AdminBatchCreateForm));

	}


	public AdminBatchDeletePage(PageParameters pageParameters)
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

		AdminBatchDeleteForm adminBatchCreateForm = new AdminBatchDeleteForm(
				"adminBatchDeleteForm", model, pageParameters);
		add(initNavigationBorder(adminBatchCreateForm));

	}

	private class AdminBatchDeleteForm extends Form<BatchBean>
	{
		private static final long serialVersionUID = 1L;

		private DropDownChoice<String> ddcBatchYear;

		private Button btnSubmit;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;

		private List<String> batchData;

		private void init()
		{
			populateBatch();
		}

		public AdminBatchDeleteForm(String id, IModel<BatchBean> model, PageParameters param)
		{
			super(id, model);

			init();

			ddcBatchYear = new DropDownChoice<String>("batchYear", batchData);

			btnSubmit = new Button("btnSearch")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;

						if (null == ddcBatchYear.getModelObject() ||
							ddcBatchYear.equals(Cons.CHOOSE))
						{
							getWebSession().info("Masukkan tahun ajaran yang akan dihapus");
							state = false;
						}

						if (state)
						{
							PageParameters param = new PageParameters();
							param.put("batch", bean);

							setResponsePage(new AdminBatchDeleteConfPage(param));
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

			btnCancel = new Link<Object>("btnCancel") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick()
				{
					PageParameters param = new PageParameters();
					param.put("batch", bean);
					setResponsePage(new AdminBatchCreatePage(param));
				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(ddcBatchYear);
			add(btnSubmit);
			add(btnCancel);
			add(feedbackPanel);

		}

		private void populateBatch()
		{
			batchData = new Vector<String>();

			List<BatchBean> listTemp = alumniService.findAllBatches();

			if (null != listTemp)
			{

				while (!listTemp.isEmpty())
				{

					BatchBean temp = listTemp.remove(0);
					batchData.add(Integer.toString(temp.getBatchYear()));

				}
				Collections.sort(batchData);
			}

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
