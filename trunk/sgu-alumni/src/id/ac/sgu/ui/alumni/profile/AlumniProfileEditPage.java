package id.ac.sgu.ui.alumni.profile;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.utility.Cons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
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

public class AlumniProfileEditPage extends BasePage {

	private static Logger logger = Logger.getLogger(AlumniProfileEditPage.class);

	private UserBean bean;

	public AlumniProfileEditPage()
	{

		bean = new UserBean();
		bean.copyProperties(getWebSession().getUserBean());

		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

		AlumniProfileEditForm alumniProfileEditForm = new AlumniProfileEditForm (
				"alumniProfileEditForm", model, null);

		add(initNavigationBorder(alumniProfileEditForm));

	}

	public AlumniProfileEditPage(PageParameters pageParameters)
	{

		CompoundPropertyModel<UserBean> model = null;

		if (pageParameters != null)
		{

			bean = (UserBean) pageParameters.get("user");

			if (bean != null)
			{
				model = new CompoundPropertyModel<UserBean>(bean);
			}

		}

		AlumniProfileEditForm alumniProfileEditForm = new AlumniProfileEditForm (
				"alumniProfileEditForm", model, pageParameters);

		add(initNavigationBorder(alumniProfileEditForm));

	}


	private class AlumniProfileEditForm extends Form<UserBean>
	{
		private final List<String> GENDER = Arrays.asList(new String[] { Cons.CHOOSE,
				"Female", "Male" });

		private Button btnSubmit;
		private Link<Object> btnCancel;
		private int gender_pos = 0;
		private String genders = GENDER.get(gender_pos);

		private DropDownChoice<String> ddcBatch;
		private DropDownChoice<String> ddcDepartment;
		private DropDownChoice<String> ddcFaculty;
		private DropDownChoice<String> ddcCountry;
		private DropDownChoice<String> ddcCity;

		private String selectedBatch;
		private String selectedDepartment;
		private String selectedCountry;

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

		public AlumniProfileEditForm(String id, IModel<UserBean> model, PageParameters param)
		{
			super(id, model);

			init();

			add(new AjaxEditableLabel<UserBean>("firstName"));
			add(new AjaxEditableLabel<UserBean>("lastName"));

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

			String name_gend = bean.getGender();

			if (name_gend != null)
			if (name_gend.equalsIgnoreCase("1"))
			{
				gender_pos = 1;
				bean.setGender("Female");
			}
			else if (name_gend.equalsIgnoreCase("2"))
			{
				gender_pos = 2;
				bean.setGender("Male");
			}

			add(new AjaxEditableChoiceLabel<String>("gender", GENDER));

//			add(new AjaxEditableChoiceLabel<UserBean>("gender" , ));
			add(new AjaxEditableLabel<UserBean>("DOB"));

			add(new AjaxEditableMultiLineLabel<UserBean>("address"));
			add(new AjaxEditableLabel<UserBean>("ZIP"));

			add(new AjaxEditableLabel<UserBean>("telephone"));
			add(new AjaxEditableLabel<UserBean>("handphone"));
			add(new AjaxEditableLabel<UserBean>("email"));


			btnCancel = new Link<Object>("btnCancel")
			{
				@Override
				public void onClick()
				{

				}
			};
			add(btnCancel);

			btnSubmit = new Button("btnSubmit")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;

						if (bean.getFirstName() == null)
						{
							getWebSession().info("Tolong isi nama depan anda");
							state = false;
						}
						else
						{
							if (bean.getFirstName().length() > 10)
							{
								getWebSession().info("Nama depan tidak boleh lebih dari 10 dan kurang dari 3");
								state = false;
							}
							if (!validateLetter(bean.getFirstName()))
							{
								getWebSession().info("Nama depan hanya boleh ada huruf saja");
								state = false;
							}
						}

						if (bean.getLastName() == null)
						{
							getWebSession().info("Tolong isi nama balakang anda");
							state = false;
						}
						else
						{
							if ( (bean.getLastName().length() > 10) || (bean.getLastName().length() < 3) )
							{
								getWebSession().info("Nama belakang tidak boleh lebih dari 10 dan kurang dari 3");
								state = false;
							}
							if (!validateLetter(bean.getLastName()))
							{
								getWebSession().info("Nama belakang hanya boleh ada huruf saja");
								state = false;
							}
						}

						if (bean.getGender() == null ||
							bean.getGender().equalsIgnoreCase(Cons.CHOOSE))
						{
							getWebSession().info("Tolong pilih gender");
							state = false;
						}
						else
						{
							if (bean.getGender().equalsIgnoreCase("female"))
							{
								bean.setGender("1");
							}
							else if (bean.getGender().equalsIgnoreCase("male"))
							{
								bean.setGender("1");
							}
							else
							{
								state = false;
								throw new IllegalAccessError("OPS GENDER HAS BEEN FAULT");
							}
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


						if (bean.getTelephone() == null)
						{
							getWebSession().info("Tolong isi nomor telphone anda");
							state = false;
						}
						else
						{
							if (bean.getTelephone().length() > 7 || bean.getTelephone().length() < 4)
							{
								getWebSession().info("Digit nomor telpon tidak boleh lebih dari 7 dan kuran dari 4");
								state = false;
							}
							if (!validateDigitCharacter(bean.getTelephone()))
							{
								getWebSession().info("Tolong isi nomor telpon dengan benar");
								state = false;
							}
						}

						if (bean.getHandphone() == null)
						{
							getWebSession().info("Tolong isi nama depan anda");
							state = false;
						}
						else
						{
							if (bean.getHandphone().length() > 7 || bean.getHandphone().length() < 4)
							{
								getWebSession().info("Digit nomor hp tidak boleh lebih dari 7 dan kuran dari 4");
								state = false;
							}
							if (!validateDigitCharacter(bean.getHandphone()))
							{
								getWebSession().info("Tolong isi nomor hp anda dengan benar");
								state = false;
							}
						}

						if (bean.getZIP() == null)
						{
							getWebSession().info("Tolong isi ZIP");
							state = false;
						}
						else
						{
							if (bean.getZIP().length() != 5)
							{
								getWebSession().info("ZIP hanya boleh 5 digit");
								state = false;
							}
							if (!validateDigitCharacter(bean.getZIP()))
							{
								getWebSession().info("Tolong isi ZIP dengan benar");
								state = false;
							}
						}

						if (bean.getAddress() == null)
						{
							getWebSession().info("Tolong isi address dengan benar");
							state = false;
						}
						else
						{
							if (bean.getAddress().length() > 100)
							{
								getWebSession().info("Address tidak boleh lebih dari 100 huruf");
								state = false;
							}

							if (!validateNonSpecialCharacterWithSpace(bean.getAddress()))
							{
								getWebSession().info("Address tidak boleh ada simbol2 aneh");
								state = false;
							}
						}

						if (validateBirthdayAge(String.valueOf(bean.getDOB())))
						{
							getWebSession().info("Maaf anda harus mengganti tanggal lahir anda / tanggal lahir tidak valid");
							state = false;
						}

						if (bean.getEmail() == null)
						{
							getWebSession().info("Maaf anda harus mengganti email anda");
							state = false;
						}
						else
						{
							if (bean.getEmail().length() > 14)
							{
								getWebSession().info("Email tidak boleh lebih dari 14 huruf");
								state = false;
							}
							if (!validateEmail(bean.getEmail()))
							{
								getWebSession().info("Format email anda salah");
								state = false;
							}
						}

						if (state)
						{

							String department = ddcDepartment.getModelObject();
							String faculty = ddcFaculty.getModelObject();
							String batch = String.valueOf(ddcBatch.getModelObject());
							String city = ddcCity.getModelObject();
							String country = ddcCountry.getModelObject();

							UserBean processBean = new UserBean();
							processBean.copyProperties(bean);
							processBean.setDepartmentName(department);
							processBean.setFacultyName(faculty);
							processBean.setBatchYear(Integer.parseInt(batch));
							processBean.setCityName(city);
							processBean.setCountryName(country);

							if (userService.updateUser(processBean) == Cons.UPDATE_USR_FAILURE)
							{
								getWebSession().info("Ops, sorry you cannot update your profile");
							}
							else
							{
									getWebSession().info("Successfully updated your profile. You need to login again to perform changes");
									getWebSession().getUserBean().copyProperties(bean);
									bean.clear();
									setResponsePage(LoginPage.class);
							}
						}
						else
						{
							if (bean.getGender() != null)
							{
								if (bean.getGender().equalsIgnoreCase("1"))
								{
									bean.setGender("Female");
								}
								else if (bean.getGender().equalsIgnoreCase("2"))
								{
									bean.setGender("Male");
								}
							}
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


			add(btnSubmit);
			add(new FeedbackPanel("feedbackPanel"));

		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent)
		{
			if (submittingComponent != null)
			{
				submittingComponent.onSubmit();
			}
		}

		/**
		 * @return the genders
		 */
		public String getGenders() {
			return genders;
		}

		/**
		 * @param genders the genders to set
		 */
		public void setGenders(String genders) {
			this.genders = genders;
		}


	}


}
