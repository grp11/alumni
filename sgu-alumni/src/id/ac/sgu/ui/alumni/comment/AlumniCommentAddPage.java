package id.ac.sgu.ui.alumni.comment;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.CommentBean;
import id.ac.sgu.utility.Cons;

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
import org.apache.wicket.markup.html.form.TextField;
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

public class AlumniCommentAddPage extends BasePage
{

	private static Logger logger = Logger.getLogger(AlumniCommentAddPage.class);

	private CommentBean bean;
	private List<CommentBean> beanList;

	public AlumniCommentAddPage()
	{
		try
		{
			bean = new CommentBean();
			beanList = new Vector<CommentBean>();

			CompoundPropertyModel<CommentBean> model = new CompoundPropertyModel<CommentBean>(bean);

			AlumniCommentAddForm alumniCommentAddForm = new AlumniCommentAddForm(
					"alumniCommentAddForm", model, null);

			add(initNavigationBorder(alumniCommentAddForm));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public AlumniCommentAddPage(PageParameters pageParameters)
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

		AlumniCommentAddForm alumniCommentAddForm = new AlumniCommentAddForm(
				"alumniCommentAddForm", model, pageParameters);

		add(initNavigationBorder(alumniCommentAddForm));
	}


	private class AlumniCommentAddForm extends Form<CommentBean>
	{

		private Button btnSearch;
		private Link<Object> btnClear;

		private DataView<CommentBean> userDataView;

		private DropDownChoice<String> ddcBatch;
		private DropDownChoice<String> ddcDepartment;
		private DropDownChoice<String> ddcFaculty;

		private TextField<CommentBean> tfCommentContents;

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
			bean.setFromName(getWebSession().getUserBean().getUsername());

			commentRefresh();

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

		public AlumniCommentAddForm(String id, final IModel<CommentBean> model, PageParameters pageParameters)
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

			tfCommentContents = new TextField<CommentBean>("commentContents");
			add(tfCommentContents);

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

			btnSearch = new Button("btnSubmit")
			{
				@Override
				public void onSubmit()
				{
					try
					{
						int checker = 0;

						CommentBean process = new CommentBean();

						if ((ddcBatch.getModelObject() == null)	)
						{
							getWebSession().info("Please choose batch year");
						}
						else
						{
							checker++;
							process.setBatchYear(Integer.parseInt(ddcBatch.getModelObject()));

						}

						if ((ddcDepartment.getModelObject() == null)	)
						{
							getWebSession().info("Please choose department for more detail");
						}
						else
						{
							checker++;
							process.setDepartmentName(ddcDepartment.getModelObject());

						}

						if ((ddcFaculty.getModelObject() == null)	)
						{
							getWebSession().info("Please choose faculty for more detail");
						}
						else
						{
							checker++;
							process.setFacultyName(ddcFaculty.getModelObject());
						}

						if (tfCommentContents.getModelObject() == null)
						{
							getWebSession().info("Please fill comments");
						}
						else
						{
							checker++;
							process.setCommentContents(model.getObject().getCommentContents());
						}

						if (checker == 4)
						{
							checker = 0;

							process.setFromName(bean.getFromName());

							if (process.getFromName() != null)
							{
								if (userService.createPostNewComment(process) == Cons.POST_COMMENT_FAILURE)
								{
									getWebSession().info("Comment is failed to post");
								}
								else
								{
									getWebSession().info("Successfully post a comment");
								}

								doClear();
							}
								else
							{
								getWebSession().error("ERROR: CANNOT FIND USERNAME");
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
					doClear();
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

		private void doClear()
		{
			dropDownClear();

			selectedBatch = null;
			selectedDepartment = null;

			bean.clear();

			commentRefresh();
		}

		private void dropDownClear()
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
		}

		private void commentRefresh()
		{
			List<CommentBean> comments = userService.findAllComments();

			if (comments != null && !comments.isEmpty())
			{
				beanList.clear();
				beanList.addAll(comments);
			}
			else
			{
				getWebSession().info("Cannot find any comments in this result");
				beanList.clear();
			}
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
