package id.ac.sgu.ui.alumni.search;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AlumniSearchViewPage extends BasePage {

	private UserBean bean;

	private List<UserBean> beanList;

	public AlumniSearchViewPage()
	{
		try
		{
			bean = new UserBean();
			beanList = new Vector<UserBean>();

			CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

			AlumniSearchViewForm alumniSearchViewForm = new AlumniSearchViewForm ("alumniSearchViewForm", model, null);

			add(initNavigationBorder(alumniSearchViewForm ));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public AlumniSearchViewPage(PageParameters pageParameters)
	{
		if (pageParameters != null)
		{
			if (pageParameters.containsKey("user"))
			{
				bean = (UserBean) pageParameters.get("user");
			}

			if (pageParameters.containsKey("userList"))
			{
				beanList = (List<UserBean>) pageParameters.get("userList");
			}
		}

		if (bean == null)
		{
			bean = new UserBean();
		}

		if (beanList == null)
		{
			beanList = new Vector<UserBean>();
		}

		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

		AlumniSearchViewForm alumniSearchViewForm = new AlumniSearchViewForm ("AlumniSearchViewForm", model, pageParameters);

		add(initNavigationBorder(alumniSearchViewForm ));
	}

	private class AlumniSearchViewForm extends Form<UserBean>
	{
		private Button btnSearch;
		private Link<Object> btnClear;

		private DataView<UserBean> userDataView;

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

		public AlumniSearchViewForm(String id, IModel<UserBean> model, PageParameters pageParameters)
		{
			super (id, model);

			init();

			ddcBatch = new DropDownChoice<String>("batchYear",
					new PropertyModel<String>(this, "selectedBatch"), batchMakeChoice);

			ddcDepartment = new DropDownChoice<String>("departmentName",
					new PropertyModel<String>(this, "selectedDepartment"), departmentMakeChoice);
			ddcDepartment.setOutputMarkupId(true);

			ddcFaculty = new DropDownChoice<String>("facultyName",
					new Model<String>(), facultyModelChoice);
			ddcFaculty.setOutputMarkupId(true);

			userDataView = new DataView<UserBean>("userDataView", new ListDataProvider<UserBean>(beanList), 10)
			{
				@Override
				protected void populateItem(final Item<UserBean> item)
				{
					final UserBean data = item.getModelObject();
					item.add(new Label("batchYear", String.valueOf(data.getBatchYear())));
					item.add(new Label("department", data.getDepartmentName()));
					item.add(new Label("firstName", data.getFirstName()));
					item.add(new Label("lastName", data.getLastName()));

					item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
					{
						@Override
						public Object getObject()
						{
							return (item.getIndex() % 2 == 0) ? "even" : "odd";
						}
					}));
				}
			};

			btnSearch = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;

						int checker = 0;

						UserBean process = new UserBean();

						if ((ddcBatch.getModelObject() == null)	)
						{
							getWebSession().info("Please choose batch year");
							state = false;
						}
						else
						{
							checker++;
							process.setBatchYear(Integer.parseInt(ddcBatch.getModelObject()));
						}

						if ((ddcDepartment.getModelObject() == null)	)
						{
							getWebSession().info("Please choose department for more detail");
							state = false;
						}
						else
						{
							checker++;
							process.setDepartmentName(ddcDepartment.getModelObject());
						}

						if ((ddcFaculty.getModelObject() == null)	)
						{
							getWebSession().info("Please choose faculty for more detail");
							state = false;
						}
						else
						{
							checker++;
							process.setFacultyName(ddcFaculty.getModelObject());

						}

						if (state || (checker != 0))
						{
							refreshDropDown();

							List<UserBean> users = userService.findAllUsers(process);

							if (users != null && !users.isEmpty())
							{
								beanList.clear();
								beanList.addAll(users);
							}
							else
							{
								getWebSession().info("Cannot find anything in this search, please try again with different value");
								beanList.clear();
							}
						}
						else
						{
							refreshDropDown();

							beanList.clear();
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

			btnClear = new Link<Object>("btnClear")
			{
				@Override
				public void onClick()
				{
					refreshDropDown();

					bean.clear();
					beanList.clear();
				}
			};

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

			add(new PagingNavigator("paging", userDataView));
			add(userDataView);

			add(new FeedbackPanel("feedbackPanel"));

			add(btnClear);
			add(btnSearch);

		}

		private void refreshDropDown()
		{
			batchMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					return Collections.emptyList();
				}
			};

			departmentMakeChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					return Collections.emptyList();
				}
			};

			facultyModelChoice = new AbstractReadOnlyModel<List<? extends String>>()
			{
				@Override
				public List<String> getObject()
				{
					return Collections.emptyList();
				}
			};

			selectedBatch = null;
			selectedDepartment = null;
		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent)
		{
			if (submittingComponent != null)
			{
				submittingComponent.onSubmit();
			}
		}

	}

}
