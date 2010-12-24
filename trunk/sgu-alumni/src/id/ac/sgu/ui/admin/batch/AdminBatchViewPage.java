package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.utility.Cons;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
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

public class AdminBatchViewPage extends BasePage
{

	private static Logger logger = Logger.getLogger(AdminBatchViewPage.class);

	private UserBean bean;

	private List<UserBean> beanList;

	public AdminBatchViewPage()
	{
		try
		{
			bean = new UserBean();
			beanList = new Vector<UserBean>();

			CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

			AdminBatchViewForm adminBatchViewForm = new AdminBatchViewForm(
				"adminBatchViewForm", model, null);

			add(initNavigationBorder(adminBatchViewForm));
		}
		catch (Exception  e)
		{
			e.printStackTrace();

		}
	}

	public AdminBatchViewPage(PageParameters param)
	{
		if (param != null)
		{
			if (param.containsKey("user"));
			{
				bean = (UserBean) param.get("user");
			}

			if (param.containsKey("userList"))
			{
				beanList = (List<UserBean>) param.get("userList");
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

		AdminBatchViewForm adminBatchViewForm = new AdminBatchViewForm(
				"adminBatchViewForm", model, param);

		add(initNavigationBorder(adminBatchViewForm));
	}

	private class AdminBatchViewForm extends Form<UserBean> {

		private DropDownChoice<String> ddcBatchYear;

		private Button btnSearch;

		private Link<Object> btnClear;

		private DataView<UserBean> userDataView;

		private PagingNavigator pagingNavigator;

		private List<String> batchList;

		private void init() {
			populateBatches();
		}

		public AdminBatchViewForm(String id, IModel<UserBean> model, PageParameters pageParameters) {
			super(id, model);

			init();

			ddcBatchYear = new DropDownChoice<String>("batchYear", batchList)
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

			btnSearch = new Button("btnSearch")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;
						if (ddcBatchYear.getModelObject() == null)
						{
							getWebSession().info("Please choose batch");
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

			btnClear = new Link<Object>("btnClear")
			{
				@Override
				public void onClick()
				{
					bean.clear();
					beanList.clear();
				}
			};

			add(ddcBatchYear);

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

		private void populateBatches()
		{
			batchList = new Vector<String>();

			List<BatchBean> listTemp = alumniService.findAllBatches();

			if (null != listTemp)
			{
				while (!listTemp.isEmpty())
				{
					BatchBean temp = listTemp.remove(0);
					batchList.add(Integer.toString(temp.getBatchYear()));
				}
				Collections.sort(batchList);
			}
		}

	}

}
