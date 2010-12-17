package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.FacultyBean;
import id.ac.sgu.ui.main.MainPage;
import id.ac.sgu.utility.Cons;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AdminDepartmentEditPage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminDepartmentEditPage.class);
	
	private DepartmentBean bean;
	
	public AdminDepartmentEditPage() {
		
		bean = new DepartmentBean();
		
		CompoundPropertyModel model = new CompoundPropertyModel(bean);
				
		AdminDepartmentEditForm adminDepartmentEditForm = new AdminDepartmentEditForm("adminDepartmentEditForm", model, null);

		add(initNavigationBorder(adminDepartmentEditForm));
		
	}
	
	public AdminDepartmentEditPage(PageParameters pageParameters) {
		
		CompoundPropertyModel model = null;
		
		if (pageParameters != null) {
			
			bean = (DepartmentBean) pageParameters.get("department");
			
			if (bean != null) {
				model = new CompoundPropertyModel(bean);
			}
						
		}
		
		AdminDepartmentEditForm adminDepartmentEditForm = new AdminDepartmentEditForm("adminDepartmentEditForm", model, pageParameters);

		add(initNavigationBorder(adminDepartmentEditForm));
				
	}
	
	private class AdminDepartmentEditForm extends Form<DepartmentBean>{
		
		private DropDownChoice<String> ddcDepartmentName;
		private TextField<String> tfNewName;
		private TextField<String> tfNewAlias;
		private DropDownChoice<String> ddcFaculty;	
		private Button btnModify;
		private Link<Object> btnCancel;
		private FeedbackPanel feedbackPanel;
		private DepartmentBean original;
		
		private Map<String, DepartmentBean> departmentMap;
		private List<String> departmentList;
		private Map<String, Integer> facultyMap;
		private List<String> facultyList;

		public void init() {
			populateDepartment();
			populateFaculty();
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
		
		public AdminDepartmentEditForm(String id, final IModel<DepartmentBean> model, PageParameters pageParameters) {
			super(id, model);
			
			init();
			
			ddcDepartmentName = new DropDownChoice<String>("departmentName", departmentList) {
				
				protected CharSequence getDefaultChoice(Object obj) {
					return Cons.CHOOSE;
					
				}
				
			};
			
			tfNewName = new TextField<String>("newName");
			
			tfNewAlias = new TextField<String>("newAlias");
			
			ddcFaculty = new DropDownChoice<String>("facultyName", facultyList) {
				
				protected CharSequence getDefaultChoice(Object obj) {
					return Cons.CHOOSE;
				}
				
			};
			
			ddcFaculty.setOutputMarkupId(true);
			
			btnModify = new Button("btnModify") {
				
				@Override
				public void onSubmit() {
					
					boolean state = true;
					
					if (ddcDepartmentName.getModelObject().equals(Cons.CHOOSE)) {
						getWebSession().info("Choose department name");
						state = false;
					}
					
					if (null == tfNewName.getModelObject() 		||
						tfNewName.getModelObject().equals("")	||
						!validateLetterWithSpace(tfNewName.getModelObject()))
					{
						getWebSession().info("Tolong isi nama department yang baru");
						state = false;
					}
									
					if (null == tfNewAlias.getModelObject()		||
						tfNewAlias.getModelObject().equals("")  ||
						!validateLetterWithSpace(tfNewAlias.getModelObject())) 
					{
						getWebPage().info("Nama alias harus di isi");
						state = false;
					}
					
					if (original == null)
						original = new DepartmentBean();
						
					if (state) {
						original.setDepartmentName(ddcDepartmentName.getModelObject());
						
						PageParameters param = new PageParameters();
						param.put("department", bean);
			//			param.put("original", original);
						
						setResponsePage(new AdminDepartmentEditConfPage(param));
					}
					
					
					logger.info("button modify is clicked");
					
				}
				                        
				
			};
			
			btnCancel = new Link<Object>("btnCancel"){
				
				@Override
				public void onClick() {
					
					setResponsePage(MainPage.class);
				}
				
			};
			
			feedbackPanel = new FeedbackPanel("feedbackPanel");
			
			add(ddcDepartmentName);
			add(tfNewName);
			add(tfNewAlias);
			add(btnCancel);
			add(btnModify);
			//add(ddcFaculty);
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
