package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.utility.Cons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AdminBatchCreatePage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminBatchCreatePage.class);

	private BatchBean bean;

	public AdminBatchCreatePage() {

		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminBatchCreateForm AdminBatchCreateForm = new AdminBatchCreateForm(
				"adminBatchCreateForm", model, null);

		add(initNavigationBorder(AdminBatchCreateForm));

	}

	public AdminBatchCreatePage(PageParameters pageParameters) {

		CompoundPropertyModel<BatchBean> model = null;

		if (pageParameters != null) {

			bean = (BatchBean) pageParameters.get("batch");

			if (bean != null) {
				model = new CompoundPropertyModel<BatchBean>(bean);
			}


		}

		AdminBatchCreateForm adminBatchCreateForm = new AdminBatchCreateForm(
				"adminBatchCreateForm", model, pageParameters);
		add(initNavigationBorder(adminBatchCreateForm));

	}

	private class AdminBatchCreateForm extends Form<BatchBean> {

		private TextField<Integer> tfBatchYear;

		private DropDownChoice<String> ddcDepartmentName;
		private DropDownChoice<String> ddcFacultyName;

		private Button btnSubmit;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;

		private final Map<String, List<String>> modelsMap = alumniService.getDepartmentMapping();

		private IModel<List<? extends String>> departmentMakeChoices;
		private IModel<List<? extends String>> facultyModelChoices;

		private String selectedDropdown;

		/**
		 * @param selectedDropdown the selectedDropdown to set
		 */
		public void setSelectedDropdown(String selectedDropdown) {
			this.selectedDropdown = selectedDropdown;
		}

		/**
		 * @return the selectedDropdown
		 */
		public String getSelectedDropdown() {
			return selectedDropdown;
		}

		@SuppressWarnings("serial")
		private void init() {

			departmentMakeChoices = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					Set<String> keys = modelsMap.keySet();
					List<String> list = new ArrayList<String>(keys);
					return list;
				}

			};

			facultyModelChoices = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					List<String> facultyModels = modelsMap.get(selectedDropdown);
					if (facultyModels == null)
					{
						facultyModels = Collections.emptyList();
					}
					return facultyModels;
				}

			};

		}

		public AdminBatchCreateForm(String id, final IModel<BatchBean> model, PageParameters pageParameters) {

			super(id, model);

			init();

			tfBatchYear = new TextField<Integer>("batchYear");

			ddcDepartmentName = new DropDownChoice<String>("departmentName",
					new PropertyModel<String>(this, "selectedDropdown"), departmentMakeChoices);

			ddcFacultyName = new DropDownChoice<String>("facultyName",
					new Model<String>(), facultyModelChoices);
			ddcFacultyName.setOutputMarkupId(true);

			btnSubmit = new Button("btnSubmit") {

				@Override
				public void onSubmit() {

					try {

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

						if (null == ddcDepartmentName.getModelObject() ||
							ddcDepartmentName.getModelObject().equals(Cons.CHOOSE))
						{
							getWebSession().info("Pilih nama department");
							state = false;
						}

						if (null == ddcFacultyName.getModelObject() ||
							ddcFacultyName.getModelObject().equals(Cons.CHOOSE))
						{
							getWebSession().info("Plih nama faculty");
							state = false;
						}

						if (state) {
							bean.setDepartmentName(ddcDepartmentName.getModelObject());
							bean.setFacultyName(ddcFacultyName.getModelObject());

							PageParameters param = new PageParameters();
							param.put("batch", bean);

							setResponsePage(new AdminBatchCreateConfPage(param));

						}

					} catch (Exception e){
						e.printStackTrace();
						getWebSession().error(e.getMessage());
					}

				}


			};

			btnCancel = new Link<Object>("btnCancel") {

				@Override
				public void onClick() {

					PageParameters param = new PageParameters();
					param.put("batch", bean);
					setResponsePage(new AdminBatchCreatePage(param));

				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(tfBatchYear);

			// Ajax behaviour
			add(ddcDepartmentName);
			add(ddcFacultyName);

			ddcDepartmentName.add(new AjaxFormComponentUpdatingBehavior("onchange") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					target.addComponent(ddcFacultyName);
				}

			});

			add(btnCancel);
			add(btnSubmit);
			add(feedbackPanel);

		}


		@Override
		protected void delegateSubmit(IFormSubmittingComponent component) {

			if (component != null) {
				component.onSubmit();
			}

		}
	}



}
