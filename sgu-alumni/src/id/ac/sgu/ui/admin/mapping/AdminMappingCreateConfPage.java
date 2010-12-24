package id.ac.sgu.ui.admin.mapping;

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

public class AdminMappingCreateConfPage extends BasePage {

	private BatchBean bean;

	public AdminMappingCreateConfPage(PageParameters pageParameters)
	{
		if (null != pageParameters)
		{
			bean = (BatchBean) pageParameters.get("batch");
		}

		if (null == bean)
		{
			bean = new BatchBean();
		}

		AdminMappingCreateConfForm adminMappingCreateConfForm = new AdminMappingCreateConfForm(
				"adminMappingCreateConfForm", pageParameters);
		add(initNavigationBorder(adminMappingCreateConfForm));

	}

	private class AdminMappingCreateConfForm extends Form<BatchBean>
	{
		private String batchYear;
		private String department;
		private String faculty;

		private Label lblBatchYear;
		private Label lblDepartment;
		private Label lblFaculty;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		private FeedbackPanel feedbackPanel;

		public AdminMappingCreateConfForm(String id, PageParameters pageParameters)
		{
			super(id);

			bean = (BatchBean) pageParameters.get("batch");

			if (null != bean)
			{
				batchYear = String.valueOf(bean.getBatchYear());
				department = bean.getDepartmentName();
				faculty = bean.getFacultyName();
			}

			lblBatchYear = new Label("batchYear", batchYear);
			lblDepartment = new Label("department", department);
			lblFaculty = new Label("faculty", faculty);

			btnConfirm = new Button("btnConfirm")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						if (alumniService.createBDFMapping(bean) == Cons.CREATE_BDF_FAILURE)
						{
							getWebSession().info("Sorry, this mapping has already been existed in database");
						}
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminMappingCreatePage.class);
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
					param.put("batch", bean);
					setResponsePage(new AdminMappingCreatePage(param));
				}
			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(lblBatchYear);
			add(lblDepartment);
			add(lblFaculty);

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
