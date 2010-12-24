package id.ac.sgu.ui.admin.mapping;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AdminMappingDeletePage extends BasePage
{
	private BatchBean bean;

	public AdminMappingDeletePage()
	{
		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminMappingDeleteForm adminMappingDeleteForm = new AdminMappingDeleteForm("adminMappingDeleteForm", model, null);

		add(initNavigationBorder(adminMappingDeleteForm));

	}

	public AdminMappingDeletePage(PageParameters pageParameters)
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

		AdminMappingDeleteForm adminMappingDeleteForm = new AdminMappingDeleteForm(
				"adminMappingDeleteForm", model, pageParameters);

		add(initNavigationBorder(adminMappingDeleteForm));

	}

	private class AdminMappingDeleteForm extends Form<BatchBean>
	{
		private FeedbackPanel feedbackPanel;

		private Button btnSubmit;
		private Link<Object> btnCancel;

		private DropDownChoice<String> ddcBatch;
		private DropDownChoice<String> ddcDepartment;
		private DropDownChoice<String> ddcFaculty;

		private String selectedBatch;
		private String selectedDepartment;

		private final Map<String, Map<String, List<String>>> alumniMap = alumniService.getBatchMapping();

		private IModel<List<? extends String>> batchMakeChoice;
		private IModel<List<? extends String>> departmentMakeChoice;
		private IModel<List<? extends String>> facultyModelChoice;

		public void setSelectedBatch(String selectedBatch)
		{
			this.selectedBatch = selectedBatch;
		}

		public String getSelectedBatch()
		{
			return selectedBatch;
		}

		public void setSelectedDepartment(String selectedDepartment)
		{
			this.selectedDepartment = selectedDepartment;
		}

		public String getSelectedDepartment()
		{
			return selectedDepartment;
		}

		public void init()
		{
			batchMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					Set<String> keys = alumniMap.keySet();
					List<String> list = new ArrayList<String>(keys);
					return list;
				}
			};

			departmentMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{

					if (selectedBatch == null)
					{
						selectedDepartment = null;
						if (facultyModelChoice.getObject() != null)
						{
							facultyModelChoice = new AbstractReadOnlyModel<List<? extends String>>()
							{
								@Override
								public List<String> getObject()
								{
									return Collections.emptyList();
								}
							};
						}

						return Collections.emptyList();
					}

					Map<String, List<String>> models = alumniMap.get(selectedBatch);

					if (models == null)
					{
						return Collections.emptyList();
					}
					else
					{
						List<String> list = new ArrayList<String>(models.keySet());
						return list;
					}

				}

			};

			facultyModelChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{

					if (selectedBatch == null || selectedDepartment == null)
					{
						return Collections.emptyList();
					}


					Map<String, List<String>> models = alumniMap.get(selectedBatch);

					if (models == null)
					{
						return Collections.emptyList();
					}
					else
					{
						return models.get(selectedDepartment);
					}

				}

			};

		}

		public AdminMappingDeleteForm(String id, IModel<BatchBean> model, PageParameters pageParameters)
		{
			super(id, model);

			init();

			ddcBatch = new DropDownChoice<String>("batchYear",
					new PropertyModel<String>(this, "selectedBatch"), batchMakeChoice);

			ddcDepartment = new DropDownChoice<String>("departmentName",
					new PropertyModel<String>(this, "selectedDepartment"), departmentMakeChoice);
			ddcDepartment.setOutputMarkupId(true);

			ddcFaculty = new DropDownChoice<String>("facultyName",
					new Model<String>(), facultyModelChoice);
			ddcFaculty.setOutputMarkupId(true);
			ddcFaculty.setRequired(true);

			add(ddcBatch);
			add(ddcDepartment);
			add(ddcFaculty);

			ddcBatch.add(new AjaxFormComponentUpdatingBehavior("onchange")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					target.addComponent(ddcDepartment);
					target.addComponent(ddcFaculty);
				}
			});

			ddcDepartment.add(new AjaxFormComponentUpdatingBehavior("onchange")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					target.addComponent(ddcFaculty);
				}
			});

			btnSubmit = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try {
						boolean state = true;

						if (null == ddcBatch.getModelObject())
						{
							getWebSession().info("Pilih tahun ajaran");
							state = false;
						}

						if (null == ddcDepartment.getModelObject())
						{
							getWebSession().info("Pilih department");
							state = false;
						}

						if (null == ddcFaculty.getModelObject())
						{
							getWebSession().info("Pilih fakultas");
							state = false;
						}

						if (state)
						{
							BatchBean processBean = new BatchBean();
							processBean.setBatchYear(Integer.parseInt(ddcBatch.getModelObject()));
							processBean.setDepartmentName(ddcDepartment.getModelObject());
							processBean.setFacultyName(ddcFaculty.getModelObject());
							PageParameters param = new PageParameters();
							param.put("batch", processBean);

							setResponsePage(new AdminMappingDeleteConfPage(param));
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

			add(btnSubmit);
			add(btnCancel);

			feedbackPanel = new FeedbackPanel("feedbackPanel");

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
