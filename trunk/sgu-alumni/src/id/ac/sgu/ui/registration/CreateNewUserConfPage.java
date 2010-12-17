package id.ac.sgu.ui.registration;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class CreateNewUserConfPage extends BasePage {


	public CreateNewUserConfPage(PageParameters param) {


	}

	private class CreateNewUserConfForm extends Form<UserBean> {


		public CreateNewUserConfForm(String id, IModel<UserBean> model, PageParameters param) {
			super(id, model);


		}

	}

}
