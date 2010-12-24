package id.ac.sgu.ui.admin.mapping;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.main.MainPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
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
import org.apache.wicket.util.string.Strings;

public class AdminDeleteAlumniUserPage extends BasePage
{
	private static Logger logger = Logger.getLogger(AdminDeleteAlumniUserPage.class);

	private UserBean bean;

	public AdminDeleteAlumniUserPage()
	{
		bean = new UserBean();
		CompoundPropertyModel<UserBean> model = new CompoundPropertyModel<UserBean>(bean);

		AdminDeleteAlumniUserForm AdminDeleteAlumniUserForm = new AdminDeleteAlumniUserForm(
				"adminDeleteAlumniUserForm", model, null);

		add(initNavigationBorder(AdminDeleteAlumniUserForm));

	}

	public AdminDeleteAlumniUserPage(PageParameters pageParameters)
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

		AdminDeleteAlumniUserForm AdminDeleteAlumniUserForm = new AdminDeleteAlumniUserForm(
				"adminDeleteAlumniUserForm", model, pageParameters);

		add(initNavigationBorder(AdminDeleteAlumniUserForm));

	}

	private class AdminDeleteAlumniUserForm extends Form<UserBean>
	{
		private FeedbackPanel feedbackPanel;

		private Button btnSubmit;
		private Link<Object> btnCancel;
		final AutoCompleteTextField<String> field;

		private DataView<UserBean> userDataView;

		private List<UserBean> beanList;

		public void init()
		{
			populateUserBeanList();
		}

		public AdminDeleteAlumniUserForm(String id, IModel<UserBean> model, PageParameters pageParameters)
		{
			super(id, model);

			init();

			userDataView = new DataView<UserBean>("userDataView", new ListDataProvider<UserBean>(beanList), 10)
			{
				@Override
				protected void populateItem(final Item<UserBean> item)
				{
					final UserBean data = item.getModelObject();
					item.add(new Label("username", data.getUsername()));
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

			field = new AutoCompleteTextField<String>("ac",
					new Model<String>(""))
			{
				@Override
				protected Iterator<String> getChoices(String input)
				{
					if (Strings.isEmpty(input))
					{
						List<String> emptyList = Collections.emptyList();
						return emptyList.iterator();
					}

					List<String> choices = new ArrayList<String>(10);

					for (int i = 0; i < beanList.size(); i++)
					{
						final String username_c = beanList.get(i).getUsername();

						if (username_c.startsWith(input))
						{
							choices.add(username_c);
							if (choices.size() == 10)
							{
								break;
							}
						}
					}

					return choices.iterator();
				}
			};
			add(field);

			final Label label = new Label("selectedUsername", field.getDefaultModel());
			label.setOutputMarkupId(true);
			add(label);

			field.add(new AjaxFormSubmitBehavior(this, "onchange")
			{
				@Override
				protected void onSubmit(AjaxRequestTarget target)
				{
					target.addComponent(label);
				}

				@Override
				protected void onError(AjaxRequestTarget target)
				{
				}
			});

			btnSubmit = new Button("btnSubmit")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						boolean state = true;
						String fields = field.getModelObject();

						if (fields == null || fields.equals(""))
						{
							getWebSession().info("Username cannot be empty");
							state = false;
						}
						else if (!findWIthNecessary(fields))
						{
							getWebSession().info("Username is not exist in the database");
							state = false;
						}

						if (state)
						{
							UserBean process = new UserBean();
							process.setUsername(fields);

							PageParameters param = new PageParameters();
							param.put("user", process);

							setResponsePage(new AdminDeleteAlumniUserConfPage(param));
						}
					}
					catch(Exception e)
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
					setResponsePage(new AdminDeleteAlumniUserPage(param));
				}
			};

			add(btnSubmit);
			add(btnCancel);

			add(new PagingNavigator("paging", userDataView));
			add(userDataView);

			feedbackPanel = new FeedbackPanel("feedbackPanel");
			add(feedbackPanel);
		}

		private void populateUserBeanList()
		{
			if (beanList != null)
			{
				beanList.clear();
				beanList.addAll(userService.findAllUsers());
			}
			else
			{
				beanList = userService.findAllUsers();
			}
		}

		private boolean findWIthNecessary(String inputtedUsername)
		{
			boolean match = false;

			for (int i = 0; i<beanList.size(); i++)
			{
				final String username_c = beanList.get(i).getUsername();

				if (inputtedUsername.equals(username_c))
				{
					match = true;
					break;
				}

			}

			return match;
		}


	}


}
