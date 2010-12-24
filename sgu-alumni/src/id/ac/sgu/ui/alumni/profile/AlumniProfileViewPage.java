package id.ac.sgu.ui.alumni.profile;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class AlumniProfileViewPage extends BasePage {

	private static Logger logger = Logger.getLogger(AlumniProfileViewPage.class);

	private UserBean bean;

	public AlumniProfileViewPage()
	{
		bean = new UserBean();

		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

		AlumniProfileViewForm alumniProfileViewForm = new AlumniProfileViewForm ("alumniProfileViewForm", null);

		add(initNavigationBorder(alumniProfileViewForm));

	}

	private class AlumniProfileViewForm extends Form
	{

		public AlumniProfileViewForm(String id, PageParameters pageParameters)
		{
			super(id);


		}


	}

}
