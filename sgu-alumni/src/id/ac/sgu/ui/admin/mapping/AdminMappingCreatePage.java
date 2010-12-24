package id.ac.sgu.ui.admin.mapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.admin.batch.AdminBatchCreatePage;
import id.ac.sgu.utility.Cons;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminMappingCreatePage extends BasePage {

	private BatchBean bean;

	public AdminMappingCreatePage()
	{
		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminMappingCreateForm adminMappingCreateForm = new AdminMappingCreateForm(
				"adminMappingCreateForm", model, null);

		add(initNavigationBorder(adminMappingCreateForm));

	}

	public AdminMappingCreatePage(PageParameters pageParameters)
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

		AdminMappingCreateForm adminMappingCreateForm = new AdminMappingCreateForm(
				"adminMappingCreateForm", model, pageParameters);

		add(initNavigationBorder(adminMappingCreateForm));
	}

	private class AdminMappingCreateForm extends Form<BatchBean>
	{
		private FeedbackPanel feedbackPanel;

		private Button btnSubmit;
		private Link<Object> btnCancel;

		private DropDownChoice<String> ddcBatch;
		private DropDownChoice<String> ddcDepartment;
		private DropDownChoice<String> ddcFaculty;

		private List<String> batchList;
		private List<String> departmentList;
		private List<String> facultyList;

		public void init()
		{
			populateBatch();
			populateDepartment();
			populateFaculty();
		}

		public AdminMappingCreateForm(String id, IModel<BatchBean> model, PageParameters pageParameters)
		{
			super(id, model);

			init();

			ddcBatch = new DropDownChoice<String>("batchYear", batchList)
			{
				protected CharSequence getDefaultChoice(Object obj)
				{
					return "";
				}
			};

			ddcDepartment = new DropDownChoice<String>("departmentName", departmentList)
			{
				protected CharSequence getDefaultChoice(Object obj)
				{
					return Cons.CHOOSE;
				}
			};

			ddcFaculty = new DropDownChoice<String>("facultyName", facultyList)
			{
				protected CharSequence getDefaultChoice(Object obj)
				{
					return Cons.CHOOSE;
				}
			};

			btnSubmit = new Button("btnSubmit")
			{
				@Override
				public void onSubmit()
				{
					try {
						boolean state = true;
						BatchBean processBean = new BatchBean();

						if (null == ddcBatch.getModelObject())
						{
							getWebSession().info("Pilih tahun ajaran");
							state = false;
						}
						else
						{
							String batch = String.valueOf(ddcBatch.getModelObject());
							processBean.setBatchYear(Integer.parseInt(batch));

						}

						if (null == ddcDepartment.getModelObject())
						{
							getWebSession().info("Pilih department");
							state = false;
						}
						else
						{
							if (ddcDepartment.getModelObject().equalsIgnoreCase(Cons.CHOOSE))
							{
								getWebSession().info("Pilih department");
								state = false;
							}
							else
							{
								processBean.setDepartmentName(ddcDepartment.getModelObject());
							}
						}

						if (null == ddcFaculty.getModelObject())
						{
							getWebSession().info("Pilih fakultas");
							state = false;
						}
						else
						{
							if (ddcFaculty.getModelObject().equalsIgnoreCase(Cons.CHOOSE))
							{
								getWebSession().info("Pilih fakultas");
								state = false;
							}
							else
							{
								processBean.setFacultyName(ddcFaculty.getModelObject());
							}
						}

						if (state)
						{
							PageParameters param = new PageParameters();
							param.put("batch", processBean);

							setResponsePage(new AdminMappingCreateConfPage(param));
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
					param.put("batch", bean);
					setResponsePage(new AdminMappingCreatePage(param));
				}
			};

			add(ddcBatch);
			add(ddcDepartment);
			add(ddcFaculty);

			add(btnSubmit);
			add(btnCancel);

			feedbackPanel = new FeedbackPanel("feedbackPanel");
			add(feedbackPanel);
		}

		private void populateBatch()
		{
			batchList = new Vector<String>();

			List<BatchBean> listTemp = alumniService.findAllBatches();

		//	batchList.add(Cons.CHOOSE);

			if (null != listTemp) {
				while (!listTemp.isEmpty()) {

					BatchBean temp = listTemp.remove(0);
					batchList.add(String.valueOf(temp.getBatchYear()));

				}
				Collections.sort(batchList);
			}
		}

		private void populateDepartment() {

			departmentList = new Vector<String>();

			List<DepartmentBean> listTemp = alumniService.findAllDepartments();

			departmentList.add(Cons.CHOOSE);

			if (null != listTemp) {
				while (!listTemp.isEmpty()) {

					DepartmentBean temp = listTemp.remove(0);
					departmentList.add(temp.getDepartmentName());

				}
				Collections.sort(departmentList);
			}

		}

		private void populateFaculty() {
			facultyList = new Vector<String>();

			List<FacultyBean> listTemp = alumniService.findAllFaculties();

			facultyList.add(Cons.CHOOSE);

			if (null != listTemp) {
				while (!listTemp.isEmpty()) {

					FacultyBean temp = listTemp.remove(0);
					facultyList.add(temp.getFacultyName());


				}

				Collections.sort(facultyList);
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
