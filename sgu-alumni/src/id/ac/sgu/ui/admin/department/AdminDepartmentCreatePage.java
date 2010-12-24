package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminDepartmentCreatePage extends BasePage {

//	private static Logger logger = Logger.getLogger(AdminDepartmentCreatePage.class);
	
	private DepartmentBean bean;
	
	public AdminDepartmentCreatePage() {
		
		bean = new DepartmentBean();
	
		CompoundPropertyModel<DepartmentBean> model = new CompoundPropertyModel<DepartmentBean>(bean);

		AdminDepartmentCreateForm AdminDepartmentCreateForm = new AdminDepartmentCreateForm(
				"adminDepartmentCreateForm", model, null);

		add(initNavigationBorder(AdminDepartmentCreateForm));

	}
	
	public AdminDepartmentCreatePage(PageParameters pageParameters) {
		
		CompoundPropertyModel<DepartmentBean> model = null;
		
		if (pageParameters != null) {
			
			bean = (DepartmentBean) pageParameters.get("department");
			
			if (bean != null)
				model = new CompoundPropertyModel<DepartmentBean>(bean);
		}
		
		AdminDepartmentCreateForm adminDepartmentCreateForm = new AdminDepartmentCreateForm("adminDepartmentCreateForm", model, pageParameters);
		
		add(initNavigationBorder(adminDepartmentCreateForm));
		
	}
	
	private class AdminDepartmentCreateForm extends Form<DepartmentBean>{
		
		private TextField<String> tfName;
		private TextField<String> tfAlias;
		
		private Button btnSubmit;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;
		
		private Map<String, Integer> facultyMap;
		
		public void init() {}
		
		public AdminDepartmentCreateForm(String id, final IModel<DepartmentBean> model, PageParameters pageParameters) {
			super(id, model);
			
			tfName = new TextField<String>("departmentName");
			tfAlias = new TextField<String>("departmentAlias");
			
			btnSubmit = new Button("btnSearch") {
				
				@Override
				public void onSubmit() {
					
					
					try {
						
						boolean status = true;
	
						if (tfName.getModelObject() == null || 
							!validateLetterWithSpace(tfName.getModelObject()))
						{
							getWebSession().info("Masukkan nama department");
							status = false;
						}

						if (tfAlias.getModelObject() == null ||
							!validateLetter(tfAlias.getModelObject()))
						{
							getWebSession().info("Masukkan alias");
							status = false;
						}						
						
						if (status) {
							PageParameters param = new PageParameters();
							param.put("department", bean);
							setResponsePage(new AdminDepartmentCreateConfPage(param));
						}
						
					} catch(Exception e) {
						e.printStackTrace();
						if (e.getMessage() != null) {
							getWebSession().error(e.getMessage());
						}
						
					}
				}
				
			};
					
			btnCancel = new Link<Object>("btnCancel") {
				
				@Override
				public void onClick() {
					
					PageParameters param = new PageParameters();
					param.put("department", bean);					
					setResponsePage(new AdminDepartmentCreatePage(param));
					
				}
								
			};
			
			feedbackPanel = new FeedbackPanel("feedbackPanel");
			
			add(tfName);
			add(tfAlias);
			add(btnSubmit);
			add(btnCancel);
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
