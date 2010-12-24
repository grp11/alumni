package id.ac.sgu.ui.registration;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.admin.department.AdminDepartmentCreatePage;
import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.utility.Cons;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class CreateNewUserConfPage extends BasePage {

	private static Logger logger = Logger.getLogger(CreateNewUserConfPage.class);

	private UserBean bean;

	public CreateNewUserConfPage(PageParameters pageParameters)
	{
		CreateNewUserConfForm createNewUserConfForm = new CreateNewUserConfForm("createNewUserConfForm", pageParameters);
		add(createNewUserConfForm);
	}

	private class CreateNewUserConfForm extends Form<UserBean>
	{
		private Label lblUsername;
		private Label lblFirstname;
		private Label lblLastname;
		private Label lblBirthday;
		private Label lblGender;
		private Label lblAddress;
		private Label lblDepartment;
		private Label lblFaculty;
		private Label lblBatchYear;
		private Label lblHandphone;
		private Label lblTelephone;
		private Label lblCity;
		private Label lblCountry;
		private Label lblZIP;
		private Label lblEmail;

		private String username;
		private String firstName;
		private String lastName;
		private String password;
		private String birthday;
		private String gender;
		private String address;
		private String department;
		private String faculty;
		private String batchyear;
		private String city;
		private String country;
		private String zip;
		private String email;
		private String telephone;
		private String handphone;

		private Button btnConfirm;
		private Link<Object> btnCancel;

		public CreateNewUserConfForm(String id, PageParameters pageParameters)
		{
			super(id);

			bean = (UserBean) pageParameters.get("user");

			if (bean != null)
			{
				username = bean.getUsername();
				password = bean.getPassword();
				department = bean.getDepartmentName();
				faculty = bean.getFacultyName();
				batchyear = String.valueOf(bean.getBatchYear());
				city = bean.getCityName();
				country = bean.getCountryName();

				zip = bean.getZIP();
				telephone = bean.getTelephone();
				handphone = bean.getHandphone();
				address = bean.getAddress();

				firstName = bean.getFirstName();
				lastName = bean.getLastName();
				birthday = String.valueOf(bean.getDOB());

				gender = bean.getGender();

				email = bean.getEmail();

			}

			lblUsername = new Label("lblUsername", username);
			lblFirstname = new Label("lblFirstName", firstName);
			lblLastname = new Label("lblLastName", lastName);
			lblDepartment = new Label("lblDepartment", department);
			lblFaculty= new Label("lblFaculty", faculty);
			lblBatchYear = new Label("lblBatchYear", batchyear);
			lblAddress = new Label("lblAddress", address);
			lblGender = new Label("lblGender", gender);
			lblHandphone = new Label("lblHandphone", handphone);
			lblTelephone = new Label("lblTelephone", telephone);
			lblEmail = new Label("lblEmail", email);
			lblCity = new Label("lblCity", city);
			lblCountry = new Label("lblCountry", country);
			lblZIP = new Label("lblZIP", zip);
			lblBirthday = new Label("lblBirthday", birthday);

			add(lblUsername);
			add(lblFirstname);
			add(lblLastname);
			add(lblDepartment);
			add(lblFaculty);
			add(lblBatchYear);
			add(lblAddress);
			add(lblGender);
			add(lblHandphone);
			add(lblTelephone);
			add(lblEmail);
			add(lblZIP);
			add(lblCity);
			add(lblCountry);
			add(lblBirthday);

			btnCancel = new Link<Object>("btnCancel")
			{
				@Override
				public void onClick()
				{
					PageParameters param = new PageParameters();
					param.put("user", bean);
					setResponsePage(new CreateNewUserPage(param));
				}
			};

			btnConfirm = new Button("btnConfirm")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						if (userService.existingUserName(bean.getUsername()) == Cons.NOT_EXIST)
						{
							if (userService.createNewUser(bean) == Cons.CREATE_USR_SUCCESS)
							{
								getWebSession().info("Successfully create new user");
								setResponsePage(LoginPage.class);
							}
						}
						else
						{
							getWebSession().info("Please change your username, because the same username has been existing in our database");
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						if (e.getMessage() != null) {
							getWebSession().error(e.getMessage());
						}
					}
				}
			};

			add(new FeedbackPanel("feedbackPanel"));
			add(btnCancel);
			add(btnConfirm);

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
