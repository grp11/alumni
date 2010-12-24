package id.ac.sgu.ui.main;

import id.ac.sgu.base.BaseSession;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.admin.AdminPanel;
import id.ac.sgu.ui.admin.batch.AdminBatchCreatePage;
import id.ac.sgu.ui.admin.batch.AdminBatchDeletePage;
import id.ac.sgu.ui.admin.batch.AdminBatchViewPage;
import id.ac.sgu.ui.admin.department.AdminDepartmentCreatePage;
import id.ac.sgu.ui.admin.department.AdminDepartmentDeletePage;
import id.ac.sgu.ui.admin.department.AdminDepartmentEditPage;
import id.ac.sgu.ui.admin.department.AdminDepartmentViewPage;
import id.ac.sgu.ui.admin.mapping.AdminDeleteAlumniUserPage;
import id.ac.sgu.ui.admin.mapping.AdminMappingCreatePage;
import id.ac.sgu.ui.admin.mapping.AdminMappingDeletePage;
import id.ac.sgu.ui.admin.mapping.AdminMappingViewPage;
import id.ac.sgu.ui.alumni.AlumniPanel;
import id.ac.sgu.ui.alumni.comment.AlumniCommentAddPage;
import id.ac.sgu.ui.alumni.comment.AlumniCommentSearchPage;
import id.ac.sgu.ui.alumni.profile.AlumniProfileEditPage;
import id.ac.sgu.ui.alumni.profile.AlumniProfileViewPage;
import id.ac.sgu.ui.alumni.search.AlumniSearchViewPage;
import id.ac.sgu.ui.login.LoginPage;
import id.ac.sgu.utility.Cons;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;

public class NavigationBorder extends Border {

	private static Logger logger = Logger.getLogger(NavigationBorder.class);

	private static final long serialVersionUID = 2368458024199126224L;

	private Form<?> attachedForm;

	private HashMap<String, Object> subPanelList = new HashMap<String, Object>();
	private HashMap<String, Object> mainPanelList = new HashMap<String, Object>();
	private HashMap<String, Fragment> fragmentList = new HashMap<String, Fragment>();

	private Label labelWorkspace;

	public NavigationBorder(final String id, UserBean userBean) {
		super(id);

		if (userBean.getRoleId() == Cons.ADMIN)
		{
			final Link homeLink = new Link("homeLink")
			{
				@Override
				public void onClick()
				{
					setResponsePage(new MainPage());
				}
			};

			MenuPanel pMenu = new MenuPanel("menuPanel");
			PanelItem pMenuItem = new PanelItem("pMenu", "pMenuItem", pMenu);
			add(pMenu);
			add(pMenuItem);
			pMenuItem.add(homeLink);

			Link loginPageLink = new Link("loginPageLink")
			{
				@Override
				public void onClick()
				{
					try
					{
						BaseSession session = (BaseSession) getSession();
						session.signOut();
						setResponsePage(LoginPage.class);
					}
					catch(Exception e)
					{
						logger.error("Error at NavigationBorder.constructor: " + e.getMessage());
						e.printStackTrace();
					}
				}
			};

			AdminPanel adminPanel = new AdminPanel("adminPanel");
			Fragment adminFragment = makeAdminPanel(adminPanel);
			pMenu.add(adminPanel);
			pMenuItem.add(adminFragment);
			fragmentList.put("admin", adminFragment);

			pMenuItem.add(loginPageLink);
		}
		else if (userBean.getRoleId() == Cons.REGULAR_USER)
		{
			final Link homeLink = new Link("homeLink")
			{
				@Override
				public void onClick()
				{
					setResponsePage(new MainPage());
				}
			};
			logger.info("MenuPanelAlumni -- ENTER -- ");

			MenuPanelAlumni pMenu = new MenuPanelAlumni("menuPanel");
			logger.info("MenuPanelAlumni -- EXIT -- ");
			logger.info("PanelItem -- ENTER -- ");

			PanelItem pMenuItem = new PanelItem("pMenu", "pMenuItem", pMenu);
			logger.info("PanelItem -- EXIT -- ");

			logger.info("BEGIN ADD COMPONENET-- ENTER -- ");
			add(pMenu);
			add(pMenuItem);
			logger.info("BEGIN ADD COMPONENET-- EXIT -- ");


			pMenuItem.add(homeLink);

			Link loginPageLink = new Link("loginPageLink")
			{
				@Override
				public void onClick()
				{
					try
					{
						BaseSession session = (BaseSession) getSession();
						session.signOut();
						setResponsePage(LoginPage.class);
					}
					catch(Exception e)
					{
						logger.error("Error at NavigationBorder.constructor: " + e.getMessage());
						e.printStackTrace();
					}
				}
			};
			logger.info("AlumniPanel -- ENTER -- ");


			AlumniPanel alumniPanel = new AlumniPanel("alumniPanel");
			Fragment alumniFragment = makeAlumniPanel(alumniPanel);
			pMenu.add(alumniPanel);
			pMenuItem.add(alumniFragment);
			fragmentList.put("alumni", alumniFragment);

			pMenuItem.add(loginPageLink);
/*			labelWorkspace = new Label("labelWorkspace", userBean.getRoleName().toUpperCase() + " WORKSPACE");
			add(labelWorkspace);*/
		}
		else {
			setResponsePage(LoginPage.class);
		}
		labelWorkspace = new Label("labelWorkspace", userBean.getRoleName().toUpperCase() + " WORKSPACE");
		add(labelWorkspace);

	}


	private Fragment makeAdminFacultyLinks() {

		Fragment adminFacultyFragment = new Fragment("adminFacultyContainer","adminFacultyPanel", this.getParent());

		Link<Object> adminFacultyMakePanel = new Link<Object>(""){

			@Override
			public void onClick() {
//				setResponsePage(cls);
			}

		};
		adminFacultyMakePanel.setVisible(false);
		adminFacultyFragment.add(adminFacultyMakePanel);
		subPanelList.put("adminFacultyMakePanel", adminFacultyMakePanel);



		return adminFacultyFragment;
	}



	private Fragment makeAdminMappingLinks()
	{
		Fragment adminMappingFragment = new Fragment("adminMappingPanelContainer", "adminMappingPanel", this.getParent());
		Link<Object> adminMappingCreatePanel = new Link<Object> ("adminMappingCreatePanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminMappingCreatePage.class);

			}

		};
		adminMappingCreatePanel.setVisible(true);
		adminMappingFragment.add(adminMappingCreatePanel);
		subPanelList.put("adminMappingCreatePanel", adminMappingCreatePanel);

		Link<Object> adminMappingDeletePanel = new Link<Object> ("adminMappingDeletePanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminMappingDeletePage.class);

			}

		};
		adminMappingDeletePanel.setVisible(true);
		adminMappingFragment.add(adminMappingDeletePanel);
		subPanelList.put("adminMappingDeletePanel", adminMappingDeletePanel);

		Link<Object> adminMappingViewPanel = new Link<Object> ("adminMappingViewPanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminMappingViewPage.class);

			}

		};
		adminMappingViewPanel.setVisible(true);
		adminMappingFragment.add(adminMappingViewPanel);
		subPanelList.put("adminMappingViewPanel", adminMappingViewPanel);

		Link<Object> adminDeleteAlumniUserPanel = new Link<Object> ("adminDeleteAlumniUserPanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminDeleteAlumniUserPage.class);

			}

		};
		adminDeleteAlumniUserPanel.setVisible(true);
		adminMappingFragment.add(adminDeleteAlumniUserPanel);
		subPanelList.put("adminDeleteAlumniUserPanel", adminDeleteAlumniUserPanel);



		return adminMappingFragment;
	}


	private Fragment makeAdminDepartmentLinks() {

		Fragment adminDepartmentFragment = new Fragment("adminDepartmentPanelContainer", "adminDepartmentPanel", this.getParent());

		Link<Object> adminDepartmentCreatePanel = new Link<Object> ("adminDepartmentCreatePanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminDepartmentCreatePage.class);

			}

		};
		adminDepartmentCreatePanel.setVisible(true);
		adminDepartmentFragment.add(adminDepartmentCreatePanel);
		subPanelList.put("adminDepartmentCreatePanel", adminDepartmentCreatePanel);

		Link<Object> adminDepartmentEditPanel = new Link<Object> ("adminDepartmentEditPanel") {

			@Override
			public void onClick() {

				setResponsePage(AdminDepartmentEditPage.class);

			}

		};
		adminDepartmentEditPanel.setVisible(true);
		adminDepartmentFragment.add(adminDepartmentEditPanel);
		subPanelList.put("adminDepartmentEditPanel", adminDepartmentEditPanel);

		Link<Object> adminDepartmentViewPanel = new Link<Object> ("adminDepartmentViewPanel") {

			@Override
			public void onClick() {
				setResponsePage(AdminDepartmentViewPage.class);

			}

		};
		adminDepartmentViewPanel.setVisible(true);
		adminDepartmentFragment.add(adminDepartmentViewPanel);
		subPanelList.put("adminDepartmentViewPanel", adminDepartmentViewPanel);

		Link<Object> adminDepartmentDeletePanel = new Link<Object> ("adminDepartmentDeletePanel") {

			@Override
			public void onClick() {
				setResponsePage(AdminDepartmentDeletePage.class);

			}

		};
		adminDepartmentDeletePanel.setVisible(true);
		adminDepartmentFragment.add(adminDepartmentDeletePanel);
		subPanelList.put("adminDepartmentDeletePanel", adminDepartmentDeletePanel);



		return adminDepartmentFragment;
	}

	private Fragment makeAdminBatchLinks() {

		Fragment adminBatchFragment = new Fragment("adminBatchPanelContainer", "adminBatchPanel", this.getParent());

		Link<Object> adminBatchCreatePanel = new Link<Object>("adminBatchCreatePanel")	{

			@Override
			public void onClick()
			{
				setResponsePage(AdminBatchCreatePage.class);
			}

		};
		adminBatchCreatePanel.setVisible(true);
		adminBatchFragment.add(adminBatchCreatePanel);
		subPanelList.put("adminBatchCreatePanel", adminBatchCreatePanel);

		Link<Object> adminBatchEditPanel = new Link<Object>("adminBatchEditPanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminBatchEditPanel.setVisible(true);
		adminBatchFragment.add(adminBatchEditPanel);
		subPanelList.put("adminBatchEditPanel", adminBatchEditPanel);

		Link<Object> adminBatchViewPanel = new Link<Object>("adminBatchViewPanel")	{

			@Override
			public void onClick()
			{
				setResponsePage(AdminBatchViewPage.class);
			}

		};
		adminBatchViewPanel.setVisible(true);
		adminBatchFragment.add(adminBatchViewPanel);
		subPanelList.put("adminBatchViewPanel", adminBatchViewPanel);

		Link<Object> adminBatchDeletePanel = new Link<Object>("adminBatchDeletePanel")	{

			@Override
			public void onClick()
			{
				setResponsePage(AdminBatchDeletePage.class);
			}

		};
		adminBatchDeletePanel.setVisible(true);
		adminBatchFragment.add(adminBatchDeletePanel);
		subPanelList.put("adminBatchDeletePanel", adminBatchDeletePanel);


		return adminBatchFragment;
	}

	private Fragment makeAdminLocationLinks() {

		Fragment adminLocationFragment = new Fragment("adminLocationPanelContainer", "adminLocationPanel", this.getParent());

		Link<Object> adminCityCreatePanel = new Link<Object>("adminCityCreatePanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCityCreatePanel.setVisible(false);
		adminLocationFragment.add(adminCityCreatePanel);
		subPanelList.put("adminCityCreatePanel", adminCityCreatePanel);

		Link<Object> adminCityEditPanel = new Link<Object>("adminCityEditPanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCityEditPanel.setVisible(false);
		adminLocationFragment.add(adminCityEditPanel);
		subPanelList.put("adminCityEditPanel", adminCityEditPanel);

		Link<Object> adminCityViewPanel = new Link<Object>("adminBatchViewPanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCityViewPanel.setVisible(false);
		adminLocationFragment.add(adminCityViewPanel);
		subPanelList.put("adminCountryViewPanel", adminCityViewPanel);

		// Begin Country
		Link<Object> adminCountryCreatePanel = new Link<Object>("adminCountryCreatePanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCountryCreatePanel.setVisible(false);
		adminLocationFragment.add(adminCountryCreatePanel);
		subPanelList.put("adminCountryCreatePanel", adminCountryCreatePanel);

		Link<Object> adminCountryEditPanel = new Link<Object>("adminCountryEditPanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCountryEditPanel.setVisible(false);
		adminLocationFragment.add(adminCountryEditPanel);
		subPanelList.put("adminCountryEditPanel", adminCountryEditPanel);

		Link<Object> adminCountryViewPanel = new Link<Object>("adminCountryViewPanel")	{

			@Override
			public void onClick()
			{
//				setResponsePage(AdminFacultyCreatePage.class);
			}

		};
		adminCountryViewPanel.setVisible(false);
		adminLocationFragment.add(adminCountryViewPanel);
		subPanelList.put("adminCountryViewPanel", adminCountryViewPanel);

		return null;
	}

	private Fragment makeAdminViewUserLinks() {
		Fragment adminFragment = new Fragment("adminViewUserContainer", "adminViewUserPanel", this.getParent());




		return adminFragment;
	}

	private Fragment makeAlumniCommentPanel()
	{
		Fragment alumniCommentFragment = new Fragment("alumniCommentPanelContainer", "alumniCommentPanel", this.getParent());

		Link<Object> alumniCommentSearchPanel = new Link<Object> ("alumniCommentSearchPanel")
		{
			@Override
			public void onClick()
			{
				setResponsePage(AlumniCommentSearchPage.class);
			}
		};
		alumniCommentSearchPanel.setVisible(true);
		alumniCommentFragment.add(alumniCommentSearchPanel);
		subPanelList.put("alumniCommentSearchPanel", alumniCommentSearchPanel);

		Link<Object> alumniCommentAddPanel = new Link<Object> ("alumniCommentAddPanel")
		{
			@Override
			public void onClick()
			{
				setResponsePage(AlumniCommentAddPage.class);
			}
		};
		alumniCommentAddPanel.setVisible(true);
		alumniCommentFragment.add(alumniCommentAddPanel);
		subPanelList.put("alumniCommentAddPanel", alumniCommentAddPanel);

		return alumniCommentFragment;
	}


	private Fragment makeAlumniSearchPanel()
	{
		Fragment alumniSearchFragment = new Fragment("alumniSearchPanelContainer", "alumniSearchPanel", this.getParent());

		Link<Object> alumniSearchUserPanel = new Link<Object> ("alumniSearchViewPanel")
		{
			@Override
			public void onClick()
			{
				setResponsePage(AlumniSearchViewPage.class);
			}
		};
		alumniSearchUserPanel.setVisible(true);
		alumniSearchFragment.add(alumniSearchUserPanel);
		subPanelList.put("alumniSearchPanel", alumniSearchUserPanel);

		return alumniSearchFragment;
	}

	private Fragment makeAlumniProfilePanel()
	{
		Fragment alumniProfileFragment = new Fragment("alumniProfilePanelContainer", "alumniProfilePanel", this.getParent());

		Link<Object> alumniProfileViewPanel = new Link<Object> ("alumniProfileViewPanel")
		{
			@Override
			public void onClick()
			{
				setResponsePage(AlumniProfileViewPage.class);
			}
		};
		alumniProfileViewPanel.setVisible(true);
		alumniProfileFragment.add(alumniProfileViewPanel);
		subPanelList.put("alumniProfileViewPanel", alumniProfileViewPanel);

		Link<Object> alumniProfileEditPanel = new Link<Object>("alumniProfileEditPanel")
		{
			@Override
			public void onClick()
			{
				setResponsePage(AlumniProfileEditPage.class);

			}
		};
		alumniProfileEditPanel.setVisible(true);
		alumniProfileFragment.add(alumniProfileEditPanel);
		subPanelList.put("alumniProfileEditPanel", alumniProfileEditPanel);




		return alumniProfileFragment;

	}

	private Fragment makeAlumniPanel(AlumniPanel alumniPanel)
	{
		Fragment alumniFragment = new Fragment("alumniFragmentContainer", "alumniFragment", alumniPanel);

		final Fragment alumniSearchPanel = makeAlumniSearchPanel();
		alumniSearchPanel.setVisible(false);
		alumniSearchPanel.setOutputMarkupId(true);
		alumniSearchPanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> alumniSearchPanelLink = new AjaxLink<Object>("alumniSearchPanelLink")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				alumniSearchPanel.setVisible(!alumniSearchPanel.isVisible());
				target.addComponent(alumniSearchPanel);
			}
		};
		alumniSearchPanelLink.setVisible(true);
		mainPanelList.put("alumniDepartmentPanel", alumniSearchPanelLink);

		final Fragment alumniProfilePanel = makeAlumniProfilePanel();
		alumniProfilePanel.setVisible(false);
		alumniProfilePanel.setOutputMarkupId(true);
		alumniProfilePanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> alumniProfilePanelLink = new AjaxLink<Object>("alumniProfilePanelLink")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				alumniProfilePanel.setVisible(!alumniProfilePanel.isVisible());
				target.addComponent(alumniProfilePanel);
			}
		};
		alumniProfilePanelLink.setVisible(true);
		mainPanelList.put("alumniProfilePanel", alumniProfilePanelLink);

		final Fragment alumniCommentPanel = makeAlumniCommentPanel();
		alumniCommentPanel.setVisible(false);
		alumniCommentPanel.setOutputMarkupId(true);
		alumniCommentPanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> alumniCommentPanelLink = new AjaxLink<Object>("alumniCommentPanelLink")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				alumniCommentPanel.setVisible(!alumniCommentPanel.isVisible());
				target.addComponent(alumniCommentPanel);
			}
		};
		alumniCommentPanelLink.setVisible(true);
		mainPanelList.put("alumniCommentPanel", alumniCommentPanelLink);

		alumniFragment.add(alumniSearchPanel);
		alumniFragment.add(alumniSearchPanelLink);

		alumniFragment.add(alumniProfilePanel);
		alumniFragment.add(alumniProfilePanelLink);

		alumniFragment.add(alumniCommentPanel);
		alumniFragment.add(alumniCommentPanelLink);

		return alumniFragment;
	}


	// Construct admin panel
	private Fragment makeAdminPanel(AdminPanel adminPanel) {
		logger.info("NavigationBorder.makeAdminPanel -- ENTER -- ");

		Fragment adminFragment = new Fragment("adminFragmentContainer", "adminFragment", adminPanel);
		logger.info("adminUserPanel -- adminLinks --");

		final Fragment adminDepartmentPanel = makeAdminDepartmentLinks();
		adminDepartmentPanel.setVisible(false);
		adminDepartmentPanel.setOutputMarkupId(true);
		adminDepartmentPanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> adminDepartmentPanelLink = new AjaxLink<Object>("adminDepartmentPanelLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				// TODO: set visibilitiy through privileges
				adminDepartmentPanel.setVisible(!adminDepartmentPanel.isVisible());
				target.addComponent(adminDepartmentPanel);

			}

		};
		adminDepartmentPanelLink.setVisible(true);
		mainPanelList.put("adminDepartmentPanel", adminDepartmentPanelLink);

		final Fragment adminBatchPanel = makeAdminBatchLinks();
		adminBatchPanel.setVisible(false);
		adminBatchPanel.setOutputMarkupId(true);
		adminBatchPanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> adminBatchPanelLink = new AjaxLink<Object>("adminBatchPanelLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				// TODO: set visibilitiy through privileges
				adminBatchPanel.setVisible(!adminBatchPanel.isVisible());
				target.addComponent(adminBatchPanel);

			}

		};
		adminBatchPanelLink.setVisible(true);
		mainPanelList.put("adminBatchPanel", adminBatchPanelLink);



		final Fragment adminMappingPanel = makeAdminMappingLinks();
		adminMappingPanel.setVisible(false);
		adminMappingPanel.setOutputMarkupId(true);
		adminMappingPanel.setOutputMarkupPlaceholderTag(true);

		AjaxLink<Object> adminMappingPanelLink = new AjaxLink<Object>("adminMappingPanelLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				adminMappingPanel.setVisible(!adminMappingPanel.isVisible());
				target.addComponent(adminMappingPanel);

			}

		};
		adminMappingPanelLink.setVisible(true);
		mainPanelList.put("adminMappingPanel", adminMappingPanelLink);



		adminFragment.add(adminDepartmentPanel);
		adminFragment.add(adminDepartmentPanelLink);

		adminFragment.add(adminBatchPanel);
		adminFragment.add(adminBatchPanelLink);

		adminFragment.add(adminMappingPanel);
		adminFragment.add(adminMappingPanelLink);


		return adminFragment;
	}

	/**
	 * @param attachedForm the attachedForm to set
	 */
	public void setAttachedForm(Form<?> attachedForm) {
		this.attachedForm = attachedForm;
	}

	/**
	 * @return the attachedForm
	 */
	public Form<?> getAttachedForm() {
		return attachedForm;
	}

}
