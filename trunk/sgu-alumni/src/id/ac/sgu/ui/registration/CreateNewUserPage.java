package id.ac.sgu.ui.registration;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.utility.Cons;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class CreateNewUserPage extends BasePage {

//	private static Logger logger = Logger.getLogger(CreateNewUserPage.class);

	private UserBean bean;

	public CreateNewUserPage() {

		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(new UserBean());

		RegistrationForm registrationForm = new RegistrationForm("registrationForm", model, null);

		add(registrationForm);

	}

	public CreateNewUserPage(PageParameters pageParameters) {
		CompoundPropertyModel<UserBean> model = null;

		if (pageParameters != null) {

			bean = (UserBean) pageParameters.get("user");

			if (bean != null)
				model = new CompoundPropertyModel<UserBean>(bean);
		}

		RegistrationForm registrationForm = new RegistrationForm("registrationForm", model, pageParameters);

		add(initNavigationBorder(registrationForm));


	}

	private class RegistrationForm extends Form<UserBean> {

		private FeedbackPanel feedbackPanel;

		private TextField<UserBean> tfUsername;
		private PasswordTextField pwPassword;
		private PasswordTextField pwRepeatPassword;
		private TextField<UserBean> tfFirstName;
		private TextField<UserBean> tfLastName;

		private DropDownChoice<String> ddcGender;
		private DropDownChoice<String> ddcBatch;
		private DropDownChoice<String> ddcDepartment;
		private DropDownChoice<String> ddcFaculty;
		private DropDownChoice<String> ddcCountry;
		private DropDownChoice<String> ddcCity;


//		private TextField<UserBean> birthday;
		private TextField<UserBean> tfTelephone;
		private TextField<UserBean> tfHandphone;
		private TextField<UserBean> tfEmail;
		private TextArea taAddress;
		private TextField<UserBean> tfZIP;
		private Button btnSubmit;
		private Link<Object> btnLogin;
		private Link<Object> btnCancel;

		private String selectedBatch;
		private String selectedDepartment;
		private String selectedCountry;

		private List<String> genderList;

		// Country -> city
		private final Map<String, List<String>> locationMap = new HashMap<String, List<String>>();

		// Department -> faculty
		private final Map<String, List<String>> departmentMap = new HashMap<String, List<String>>();

		// Batch -> Department -> faculty
		private final Map<String, Map<String, List<String>>> alumniMap = new HashMap<String, Map<String,List<String>>>();

		public void init() {

			populateGender();




		}

		public RegistrationForm(String id, final IModel<UserBean> model, PageParameters param) {
			super(id, model);

			init();

			tfUsername = new TextField<UserBean>("username");
			pwPassword = new PasswordTextField("password");
			pwRepeatPassword = new PasswordTextField("repeatPassword");
			tfFirstName = new TextField<UserBean>("firstName");
			tfLastName = new TextField<UserBean>("lastName");


			taAddress = new TextArea("address");
			tfZIP = new TextField<UserBean>("ZIP");

			tfTelephone = new TextField<UserBean>("telephone");
			tfHandphone = new TextField<UserBean>("handphone");
			tfEmail = new TextField<UserBean>("email");

			ddcGender = new DropDownChoice<String>("gender", genderList) {

				@Override
				protected CharSequence getDefaultChoice(Object obj) {
					return Cons.CHOOSE;

				}

			};



			btnSubmit = new Button("btnSubmit") {

				@Override
				public void onSubmit(){
					try {
						boolean state = true;

						if (null == tfUsername.getModelObject()		||
							tfUsername.getModelObject().equals("")
						//	userService.checkUsername(tfUsername.getModelObject())

						)
						{
							getWebSession().info("Masukkna username");
							state = false;
						}
						else
						{
							if (!validateAlphabetic(tfUsername.getModelObject().getUsername()))
							{
								getWebSession().info("Nama yang dimasukkan salah");
								state = false;
							}
						}

						if (null == pwPassword.getModelObject() ||
							pwPassword.getModelObject().equals(""))
						{

							getWebSession().info("Masukkan password");
							state = false;
						}
						else {

							if (!validateLetterWithSpace(pwPassword.getModelObject()))
							{
								getWebSession().info("Nama yang dimasukkan salah");
								state = false;
							}
						}

						if (null == pwRepeatPassword.getModelObject() ||
							pwRepeatPassword.getModelObject().equals(""))
						{
							getWebSession().info("Masukkan password lagi");
							state = false;
						}
						else {
							if (!validateLetterWithSpace(pwRepeatPassword.getModelObject()))
							{
								getWebSession().info("Masukkan password lagi");
								state = false;
							}

							if  (!pwRepeatPassword.getModelObject().equals(pwPassword.getModelObject()))
							{
								getWebSession().info("Salah dalam mengulang password");
								state = false;
							}

						}

						if (null == tfFirstName.getModelObject() ||
							tfFirstName.equals(""))
						{
							getWebSession().info("Masukkan nama depan");
							state = false;
						}
						else {
							if (!validateLetterWithSpace(tfFirstName.getModelObject().getFirstName()))
							{
								getWebSession().info("Nama depan hanya boleh ada huruf dan spasi");
								state = false;
							}
						}

						if (null == tfLastName.getModelObject() ||
							tfLastName.equals(""))
						{
							getWebSession().info("Masukkan nama akhir");
							state = false;
						}
						else
						{
							if (!validateLetterWithSpace(tfLastName.getModelObject().getFirstName()))
							{
								getWebSession().info("Nama belakang hanya boleh ada huruf dan spasi");
								state = false;
							}
						}

						if (null == ddcBatch.getModelObject() ||
							ddcBatch.getModelObject().equals(""))
						{
							getWebSession().info("Pilih batch year");
							state = false;
						}

						if (null == ddcDepartment.getModelObject() ||
							ddcDepartment.getModelObject().equals(""))
						{
							getWebSession().info("Pilih department");
							state = false;
						}

						if (null == ddcFaculty.getModelObject() ||
							ddcFaculty.getModelObject().equals(""))
						{
							getWebSession().info("Pilih faculty");
							state = false;
						}

						if (null == tfEmail.getModelObject() ||
							tfEmail.getModelObject().equals(""))
						{
							getWebSession().info("Masukkan email anda");
							state = false;
						}
						else {
							if (!validateEmail(tfEmail.getModelObject().getEmail()))
							{
								getWebSession().info("Format email salah");
								state = false;
							}
						}

						if (null == tfHandphone.getModelObject() ||
							tfHandphone.getModelObject().equals(""))
						{
							getWebSession().info("Masukkan nomor handphone anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(String.valueOf(tfHandphone.getModelObject().getHandphone())))
							{
								getWebSession().info("Masukkan nomor handphone anda dengan benar");
								state = false;
							}
						}

						if (null == tfTelephone.getModelObject() ||
							tfTelephone.getValue().equalsIgnoreCase(""))
						{
							getWebSession().info("Masukkan nomor telpon anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(String.valueOf(tfTelephone.getModelObject().getTelephone())))
							{
								getWebSession().info("Masukkan nomor telepon anda dengan benar");
								state = false;
							}
						}

						if (tfZIP.getModelObject().equals(0))
						{
							getWebSession().info("Masukkan ZIP postal anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(String.valueOf(tfZIP.getModelObject().getZIP())))
							{
								getWebSession().info("Masukkan nomor telepon anda dengan benar");
								state = false;
							}
						}

						if (state) {
							bean.setDepartmentName(ddcDepartment.getModelObject());
							bean.setFacultyName(ddcFaculty.getModelObject());

							PageParameters param = new PageParameters();
							param.put("user", bean);
		//					setResponsePage(new CreateNewUserConfPage(param));


						}
					}
					catch (Exception e) {
						e.printStackTrace();
						getWebSession().error(e.getMessage());
					}
				}

			};

			btnCancel = new Link<Object>("btnCancel") {

				@Override
				public void onClick() {

					PageParameters param = new PageParameters();
					param.put("user", bean);
					setResponsePage(new CreateNewUserPage(param));

				}
			};


			btnLogin = new Link<Object>("btnLogin") {
				@Override
				public void onClick() {
					setResponsePage(LoginPage.class);
				}

			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(tfUsername);
			add(pwPassword);
			add(pwRepeatPassword);
			add(ddcGender);

			add(tfFirstName);
			add(tfLastName);
			add(taAddress);

//			add(ddcCity);
	//		add(ddcCountry);

		//	add(ddcBatch);
		//	add(ddcDepartment);
		//	add(ddcFaculty);

			add(tfZIP);
			add(tfEmail);

			add(tfHandphone);
			add(tfTelephone);

			add(btnCancel);
			add(btnLogin);
			add(btnSubmit);
			add(feedbackPanel);
		}

		private void populateGender() {

			genderList = new Vector<String>();
			genderList.add(Cons.CHOOSE);
			genderList.add("Male");
			genderList.add("Female");

			Collections.sort(genderList);
		}

		private void populateBatch() {



		}

		private void populateLocation() {


		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent) {
			if (submittingComponent != null) {
				submittingComponent.onSubmit();
			}
		}


	}


}
