package id.ac.sgu.ui.alumni.comment;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.CommentBean;
import id.ac.sgu.bean.base.UserBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
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

public class AlumniCommentSearchPage extends BasePage
{

	private static Logger logger = Logger.getLogger(AlumniCommentSearchPage.class);

	private CommentBean bean;
	private List<CommentBean> beanList;

	public AlumniCommentSearchPage()
	{
		try
		{
			bean = new CommentBean();
			beanList = new Vector<CommentBean>();

			CompoundPropertyModel<CommentBean> model = new CompoundPropertyModel<CommentBean>(bean);

			AlumniCommentSearchForm alumniCommentSearchForm = new AlumniCommentSearchForm(
					"alumniCommentSearchForm", model, null);

			add(initNavigationBorder(alumniCommentSearchForm));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public AlumniCommentSearchPage(PageParameters pageParameters)
	{
		if (pageParameters != null)
		{
			if (pageParameters.containsKey("comment"))
			{
				bean = (CommentBean) pageParameters.get("comment");
			}

			if (pageParameters.containsKey("commentList"))
			{
				beanList = (List<CommentBean>) pageParameters.get("commentList");
			}

		}

		if (bean == null)
		{
			bean = new CommentBean();
		}

		if (beanList == null)
		{

			beanList = new Vector<CommentBean>();
		}

		CompoundPropertyModel<CommentBean> model = new CompoundPropertyModel<CommentBean>(bean);

		AlumniCommentSearchForm alumniCommentSearchForm = new AlumniCommentSearchForm(
				"alumniCommentSearchForm", model, pageParameters);

		add(initNavigationBorder(alumniCommentSearchForm));
	}

	private class AlumniCommentSearchForm extends Form<CommentBean>
	{

		private Button btnSearch;
		private Link<Object> btnClear;

		private DataView<CommentBean> userDataView;

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
		public AlumniCommentSearchForm(String id, IModel<CommentBean> model, PageParameters pageParameters)
		{
			super(id, model);
			init();

			ddcBatch = new DropDownChoice<String>("batchYear",
					new PropertyModel<String>(this, "selectedBatch"), batchMakeChoice);

			ddcDepartment = new DropDownChoice<String>("departmentName",
					new PropertyModel<String>(this, "selectedDepartment"), departmentMakeChoice);
			ddcDepartment.setOutputMarkupId(true);

			ddcFaculty = new DropDownChoice<String>("facultyName",
					new Model<String>(), facultyModelChoice);
			ddcFaculty.setOutputMarkupId(true);

			userDataView = new DataView<CommentBean>("userDataView", new ListDataProvider<CommentBean>(beanList), 10)
			{
				@Override
				protected void populateItem(final Item<CommentBean> item)
				{
					final CommentBean data = item.getModelObject();
					item.add(new Label("fromName", data.getFromName()));
					item.add(new Label("comment", data.getCommentContents()));
					item.add(new Label("created", String.valueOf(data.getCreated())));

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

						CommentBean process = new CommentBean();

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

						if (state || (checker == 3))
						{
							checker = 0;
							List<CommentBean> comments = userService.findAllComments(process);

							if (comments != null && !comments.isEmpty())
							{
								beanList.clear();
								beanList.addAll(comments);
							}
							else
							{
								getWebSession().info("Cannot find anything in this search, please try again with different value");
								beanList.clear();
							}
						}
						else
						{
							checker = 0;
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
