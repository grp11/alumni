package id.ac.sgu.ui.registration;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.utility.Cons;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class CreateNewUserPage extends BasePage
{

	private static Logger logger = Logger.getLogger(CreateNewUserPage.class);

	private UserBean bean;

	public CreateNewUserPage()
	{
		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(new UserBean());

		RegistrationForm registrationForm = new RegistrationForm("registrationForm", model, null);

		add(registrationForm);
	}

	public CreateNewUserPage(PageParameters pageParameters)
	{
		CompoundPropertyModel<UserBean> model = null;

		if (pageParameters != null) {

			bean = (UserBean) pageParameters.get("user");

			if (bean != null)
				model = new CompoundPropertyModel<UserBean>(bean);
		}

		RegistrationForm registrationForm = new RegistrationForm("registrationForm", model, pageParameters);

		add(registrationForm);
	}

	private class RegistrationForm extends Form<UserBean>
	{
		private FeedbackPanel feedbackPanel;

		private TextField<String> tfUsername;
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

		private Model dateModel;
		private TextField tfBirthday;
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

		private final Map<String, Map<String, List<String>>> alumniMap = alumniService.getBatchMapping();

		private final Map<String, List<String>> locationMap = userService.getLocationList();

		private IModel<List<? extends String>> batchMakeChoice;
		private IModel<List<? extends String>> departmentMakeChoice;
		private IModel<List<? extends String>> facultyModelChoice;

		private IModel<List<? extends String>> cityMakeChoice;
		private IModel<List<? extends String>> countryMakeChoice;

		public String getSelectedCountry()
		{
			return selectedCountry;
		}

		public void setSelectedCountry(String selectedCountry)
		{
			this.selectedCountry = selectedCountry;
		}

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
			populateGender();

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
					logger.info("selected batch:  " + selectedBatch);
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

			countryMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					Set<String> keys = locationMap.keySet();
					List<String> list = new ArrayList<String>(keys);
					return list;
				}
			};

			cityMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					if (selectedCountry == null)
					{
						return Collections.emptyList();
					}

					List<String> list = locationMap.get(selectedCountry);
					if (list == null)
					{
						list = Collections.emptyList();
					}

					return list;
				}

			};

		}

		public RegistrationForm(String id, final IModel<UserBean> model, PageParameters param) {
			super(id, model);

			init();

			tfUsername = new TextField<String>("username");
			tfUsername.setRequired(true);

			pwPassword = new PasswordTextField("password");
			pwPassword.setRequired(true);

			pwRepeatPassword = new PasswordTextField("repeatPassword");
			pwRepeatPassword.setRequired(true);

			tfFirstName = new TextField<UserBean>("firstName");
			tfFirstName.setRequired(true);

			tfLastName = new TextField<UserBean>("lastName");
			tfLastName.setRequired(true);

			dateModel = new Model();
			tfBirthday = new TextField("birthday", dateModel, Date.class);
//			tfBirthday.add(new DatePicker()); // Yoda error?
			tfBirthday.setRequired(true);

			taAddress = new TextArea("address");
			taAddress.setRequired(true);

			tfZIP = new TextField<UserBean>("ZIP");
			tfZIP.setRequired(true);

			tfTelephone = new TextField<UserBean>("telephone");
			tfTelephone.setRequired(true);

			tfHandphone = new TextField<UserBean>("handphone");
			tfHandphone.setRequired(true);

			tfEmail = new TextField<UserBean>("email");
			tfEmail.setRequired(true);

			ddcGender = new DropDownChoice<String>("gender", genderList)
			{

				@Override
				protected CharSequence getDefaultChoice(Object obj)
				{
					return Cons.CHOOSE;
				}

			};
			ddcGender.setRequired(true);

			ddcCountry = new DropDownChoice<String>("countryName",
					new PropertyModel<String>(this, "selectedCountry"), countryMakeChoice);

			ddcCity = new DropDownChoice<String>("cityName",
					new Model<String>(), cityMakeChoice);
			ddcCity.setOutputMarkupId(true);
			ddcCity.setRequired(true);

			ddcBatch = new DropDownChoice<String>("batchYear",
					new PropertyModel<String>(this, "selectedBatch"), batchMakeChoice);

			ddcDepartment = new DropDownChoice<String>("departmentName",
					new PropertyModel<String>(this, "selectedDepartment"), departmentMakeChoice);
			ddcDepartment.setOutputMarkupId(true);

			ddcFaculty = new DropDownChoice<String>("facultyName",
					new Model<String>(), facultyModelChoice);
			ddcFaculty.setOutputMarkupId(true);
			ddcFaculty.setRequired(true);

			btnSubmit = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;

						if (null == tfUsername.getModelObject()	||
							tfUsername.getModelObject().equals(""))
						{
							getWebSession().info("Masukkan username");
							state = false;
						}
						else
						{
							if (!validateLetter(tfUsername.getValue()))
							{
								getWebSession().info("Username hanya boleh huruf dan tanpa ada spasi");
								state = false;
							}

							if ((tfUsername.getModelObject().length() < 3)
								&& (tfUsername.getModelObject().length() > 10 ))
							{
								getWebSession().info("Panjang username tidak boleh kurang dari 3 dan lebih dari 10");
								state = false;
							}
						}

						if (null == pwPassword.getModelObject() ||
							pwPassword.getModelObject().equals(""))
						{
							getWebSession().info("Masukkan password");
							state = false;
						}
						else
						{
							if (!validateNonSpecialCharacterWithSpace(pwPassword.getModelObject()))
							{
								getWebSession().info("Password yang di masukkan tidak boleh ada spasi / simbol2 gak jelas");
								state = false;
							}
						}

						if (null == pwRepeatPassword.getModelObject() ||
							pwRepeatPassword.getModelObject().equals(""))
						{
							getWebSession().info("Field repeat password cannot be zero");
							state = false;
						}
						else
						{
							if (!validateNonSpecialCharacterWithSpace(pwPassword.getModelObject()))
							{
								getWebSession().info("Masukkan password lagi");
								state = false;
							}

							if  (!pwRepeatPassword.getValue().equals(pwPassword.getValue()))
							{
								getWebSession().info("Salah dalam mengulang password, tolong ulang password lagi sampai benar");
								state = false;
							}

						}

						if (null == tfFirstName.getValue() ||
							tfFirstName.getValue().equalsIgnoreCase(""))
						{
							getWebSession().info("Masukkan nama depan");
							state = false;
						}
						else
						{
							if (!validateLetterWithSpace(tfFirstName.getValue()))
							{
								getWebSession().info("Nama depan hanya boleh ada huruf dan tanpa ada spasi");
								state = false;
							}
						}

						if (null == tfLastName.getModelObject() ||
							tfLastName.getValue().equalsIgnoreCase(""))
						{
							getWebSession().info("Masukkan nama akhir");
							state = false;
						}
						else
						{
							if (!validateLetterWithSpace(tfLastName.getValue()))
							{
								getWebSession().info("Nama belakang hanya boleh ada huruf dan tanpa ada spasi");
								state = false;
							}
						}

						if (null == taAddress.getValue())
						{
							getWebSession().info("Masukkan alamat");
							state = false;
						}
						else
						{
							if (taAddress.getValue().length() >= 150)
							{
								getWebSession().info("Alamat hanya boleh 150 huruf");
								state = false;
							}
						}

						if (null == ddcGender.getModelObject()
								|| ddcGender.getModelObject().equals(Cons.CHOOSE))
						{
							getWebSession().info("Pilih jenis burung anda");
							state = false;
						}

						if (null == tfBirthday.getValue())
						{
							getWebSession().info("Birthday masih kosong");
							state = false;
						}
						else
						{
							if (!validateBirthdayAge(tfBirthday.getValue()))
							{
								getWebSession().info("Maav umur anda terlalu muda");
								state = false;
							}
							else
								logger.info("into here");
						}

						if (null == ddcCountry.getModelObject())
						{
							getWebSession().info("Pilih nama negara");
							state = false;
						}

						if (null == ddcCity.getModelObject())
						{
							getWebSession().info("Pilih nama kota");
							state = false;
						}

						if (null == ddcBatch.getModelObject())
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

						if (null == tfEmail.getValue() ||
							tfEmail.getValue().equals(""))
						{
							getWebSession().info("Masukkan email anda");
							state = false;
						}
						else
						{
							if (!validateEmail(tfEmail.getValue()))
							{
								getWebSession().info("Format email yang benar seharusnya seperti: example@example.com");
								state = false;
							}
						}

						if (null == tfHandphone.getValue() ||
							tfHandphone.getValue().equalsIgnoreCase(""))
						{
							getWebSession().info("Masukkan nomor handphone anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(String.valueOf(tfHandphone.getValue())))
							{
								getWebSession().info("Masukkan nomor handphone anda dengan benar");
								state = false;
							}
						}

						if (null == tfTelephone.getValue() ||
							tfTelephone.getValue().equalsIgnoreCase(""))
						{
							getWebSession().info("Masukkan nomor telpon anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(String.valueOf(tfTelephone.getValue())))
							{
								getWebSession().info("Masukkan nomor telepon anda dengan benar");
								state = false;
							}
						}

						if (null == tfZIP.getValue() ||
							tfZIP.getValue().equals(""))
						{
							getWebSession().info("Masukkan ZIP postal anda");
							state = false;
						}
						else
						{
							if (!validateDigitCharacter(tfZIP.getValue()))
							{
								getWebSession().info("ZIP harus dalam bentuk angka");
								state = false;
							}
							else
							{
								if ((tfZIP.getValue().length() > 5) || (tfZIP.getValue().length() < 5))
								{
									getWebSession().info("ZIP minimal 5 angka");
									state = false;
								}
							}
						}

						if (state)
						{

							PageParameters param = new PageParameters();
							param.put("user", objectModelParser(model));

							setResponsePage(new CreateNewUserConfPage(param));
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
					param.put("user", bean);
					setResponsePage(new CreateNewUserPage(param));

				}
			};


			btnLogin = new Link<Object>("btnLogin")
			{
				@Override
				public void onClick() {
					setResponsePage(LoginPage.class);
				}
			};

			add(tfUsername);
			add(pwPassword);
			add(pwRepeatPassword);
			add(ddcGender);

			add(tfFirstName);
			add(tfLastName);
			add(tfBirthday);
			add(taAddress);

			add(ddcCity);
			add(ddcCountry);

			ddcCountry.add(new AjaxFormComponentUpdatingBehavior("onchange")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					target.addComponent(ddcCity);
				}

			});

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

			add(tfZIP);
			add(tfEmail);

			add(tfHandphone);
			add(tfTelephone);

			add(btnCancel);
			add(btnLogin);
			add(btnSubmit);

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(feedbackPanel);
		}

		private void populateGender() {

			genderList = new Vector<String>();
			genderList.add(Cons.CHOOSE);
			genderList.add("Male");
			genderList.add("Female");

			Collections.sort(genderList);
		}

		private UserBean objectModelParser(final IModel<UserBean> model) throws ParseException
		{
			UserBean newBean = null;

			if (model != null)
			{
				String username = model.getObject().getUsername();
				String password = model.getObject().getPassword();
				String department = ddcDepartment.getModelObject();
				String faculty = ddcFaculty.getModelObject();
				String batch = String.valueOf(ddcBatch.getModelObject());
				String city = ddcCity.getModelObject();
				String country = ddcCountry.getModelObject();
				String gender = ddcGender.getModelObject();
				String email = model.getObject().getEmail();
				String telephone = model.getObject().getTelephone();
				String handphone = model.getObject().getHandphone();
				String zip = model.getObject().getZIP();
				String address = model.getObject().getAddress();
				String firstName = model.getObject().getFirstName();
				String lastName = model.getObject().getLastName();

				newBean = new UserBean();

				newBean.setUsername(username);
				newBean.setPassword(password);
				newBean.setFirstName(firstName);
				newBean.setLastName(lastName);
				newBean.setDepartmentName(department);
				newBean.setFacultyName(faculty);

				if (batch != null)
					newBean.setBatchYear(Integer.parseInt(batch));

				newBean.setCityName(city);
				newBean.setCountryName(country);
				newBean.setGender(gender);
				newBean.setEmail(email);

				newBean.setTelephone(telephone);
				newBean.setHandphone(handphone);
				if (zip != null)
					newBean.setZIP(zip);
				newBean.setAddress(address);

				newBean.setDOB((Date) tfBirthday.getModelObject());
			}
			return newBean;
		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent) {
			if (submittingComponent != null) {
				submittingComponent.onSubmit();
			}
		}


	}


}
