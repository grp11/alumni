package id.ac.sgu.ui.admin.department;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.DepartmentBean;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.utility.Cons;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
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

public class AdminDepartmentViewPage extends BasePage {

	//private static Logger logger = Logger.getLogger(AdminDepartmentViewPage.class);

	private UserBean bean;

	private List<UserBean> beanList;

	public AdminDepartmentViewPage()
	{
		try
		{
			bean = new UserBean();
			beanList = new Vector<UserBean>();

			CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

			AdminDepartmentViewForm adminDepartmentViewForm = new AdminDepartmentViewForm(
				"adminDepartmentViewForm", model, null);

			add(initNavigationBorder(adminDepartmentViewForm));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public AdminDepartmentViewPage(PageParameters pageParameters)
	{
		try
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

			AdminDepartmentViewForm adminDepartmentViewForm = new AdminDepartmentViewForm(
					"adminDepartmentViewForm", model, pageParameters);

			add(initNavigationBorder(adminDepartmentViewForm));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private class AdminDepartmentViewForm extends Form<UserBean>
	{

		private Button btnSearch;
		private Link<Object> btnClear;

		private DropDownChoice<String> ddcDepartment;

		private DataView<UserBean> userDataView;
		private List<String> departmentList;


		public void init()
		{
			populateDepartment();
		}

		public AdminDepartmentViewForm(String id, IModel<UserBean> model, PageParameters pageParameters)
		{
			super(id, model);

			init();

			ddcDepartment = new DropDownChoice<String>("departmentName", departmentList)
			{
				protected CharSequence getDefaultChoice(Object obj)
				{
					return Cons.CHOOSE;
				}
			};

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


			btnClear = new Link<Object>("btnClear")
			{
				@Override
				public void onClick()
				{
					bean.clear();
					beanList.clear();
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
						if ((ddcDepartment.getModelObject() == null) ||
							(ddcDepartment.getModelObject().equalsIgnoreCase(Cons.CHOOSE))	)
						{
							getWebSession().info("Please choose department");
							state = false;
						}

						if (state)
						{
							List<UserBean> users = userService.findAllUsers(bean);

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

			add(ddcDepartment);

			add(new PagingNavigator("paging", userDataView));
			add(userDataView);

			add(new FeedbackPanel("feedbackPanel"));

			add(btnClear);
			add(btnSearch);
		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent)
		{
			if (submittingComponent != null)
			{
				submittingComponent.onSubmit();
			}
		}

		private void populateDepartment()
		{
			departmentList = new Vector<String>();

			List<DepartmentBean> listTemp = alumniService.findAllDepartments();

			departmentList.add(Cons.CHOOSE);

			if (null != listTemp)
			{
				while (!listTemp.isEmpty())
				{
					DepartmentBean temp = listTemp.remove(0);
					departmentList.add(temp.getDepartmentName());
				}
				Collections.sort(departmentList);
			}
		}

	}

}
