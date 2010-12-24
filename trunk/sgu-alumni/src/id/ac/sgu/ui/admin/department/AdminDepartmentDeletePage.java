package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.utility.Cons;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminDepartmentDeletePage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminDepartmentDeletePage.class);

	private DepartmentBean bean;

	public AdminDepartmentDeletePage()
	{

		bean = new DepartmentBean();

		CompoundPropertyModel<DepartmentBean> model = new CompoundPropertyModel<DepartmentBean>(bean);

		AdminDepartmentDeleteForm adminbatchAdminDepartmentDeleteForm = new AdminDepartmentDeleteForm
			("adminDepartmentDeleteForm", model, null);

		add(initNavigationBorder(adminbatchAdminDepartmentDeleteForm));

	}

	public AdminDepartmentDeletePage(PageParameters pageParameters)
	{
		CompoundPropertyModel<DepartmentBean> model = null;

		if (null != pageParameters)
		{

			bean = (DepartmentBean) pageParameters.get("department");

			if (null != bean)
			{
				model = new CompoundPropertyModel<DepartmentBean>(bean);
			}

		}

		AdminDepartmentDeleteForm adminbatchAdminDepartmentDeleteForm = new AdminDepartmentDeleteForm
		("adminDepartmentDeleteForm", model, pageParameters);

		add(initNavigationBorder(adminbatchAdminDepartmentDeleteForm));

	}

	private class AdminDepartmentDeleteForm extends Form<DepartmentBean>
	{

		private DropDownChoice<String> ddcDepartmentName;

		private List<String> departmentData;

		private FeedbackPanel feedbackPanel;
		private Button btnConfirm;
		private Link<Object> btnCancel;

		private void init()
		{
			departmentData = alumniService.getAllDepartmentName(false);
		}

		public AdminDepartmentDeleteForm(String id, IModel<DepartmentBean> model, PageParameters pageParameters)
		{
			super(id, model);

			init();

			ddcDepartmentName = new DropDownChoice<String>("departmentName", departmentData);

			btnConfirm = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try
					{

						boolean state = true;

						if (null == ddcDepartmentName ||
							ddcDepartmentName.getModelObject().equals(Cons.CHOOSE))
						{
							getWebSession().info("Pilih nama department yang ingin dihapus");
							state = false;
						}

						if (state)
						{
							PageParameters param = new PageParameters();
							param.put("department", bean);
							setResponsePage(new AdminDepartmentDeleteConfPage(param));
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
				public void onClick() {
					PageParameters param = new PageParameters();
					param.put("department", bean);
					setResponsePage(new AdminDepartmentDeletePage(param));
				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(ddcDepartmentName);
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
