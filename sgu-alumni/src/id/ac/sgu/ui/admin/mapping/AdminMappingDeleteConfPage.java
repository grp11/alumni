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

public class AdminMappingDeleteConfPage extends BasePage {

	private BatchBean bean;

	public AdminMappingDeleteConfPage(PageParameters pageParameters)
	{
		if (null != pageParameters)
		{
			bean = (BatchBean) pageParameters.get("batch");
		}

		if (null == bean)
		{
			bean = new BatchBean();
		}

		AdminMappingDeleteConfForm adminMappingDeleteConfForm = new AdminMappingDeleteConfForm ("adminMappingDeleteConfForm", pageParameters);

		add(initNavigationBorder(adminMappingDeleteConfForm ));

	}

	private class AdminMappingDeleteConfForm extends Form<BatchBean>
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

		public AdminMappingDeleteConfForm(String id, PageParameters pageParameters)
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
						if (!alumniService.isInUserReference(bean))
						{
							getWebSession().info("Sorry, there is user in that mapping. You cannot delete the mapping");
						}
						else if (alumniService.deleteMapping(bean) == Cons.DELETE_BDF_FAILURE)
						{
							getWebSession().info("Sorry, cannot find mapping");
						}
						else
						{
							PageParameters param = new PageParameters();
							param.put("again", AdminMappingDeletePage.class);
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
					setResponsePage(new AdminMappingDeletePage(param));
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
