package id.ac.sgu.ui.main;

import id.ac.sgu.base.BaseSession;
import id.ac.sgu.bean.base.UserBean;
import id.ac.sgu.ui.admin.AdminPanel;
import id.ac.sgu.ui.admin.batch.AdminBatchCreatePage;
import id.ac.sgu.ui.admin.batch.AdminBatchViewPage;
import id.ac.sgu.ui.admin.department.AdminDepartmentCreatePage;
import id.ac.sgu.ui.admin.department.AdminDepartmentEditPage;
import id.ac.sgu.ui.login.LoginPage;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * @author exodream
 * Based on example that Edison Ch. gives.
 * */
public class NavigationBorder extends Border {

	private static Logger logger = Logger.getLogger(NavigationBorder.class);

	private static final long serialVersionUID = 2368458024199126224L;

	private Form attachedForm;

	private HashMap<String, Object> subPanelList = new HashMap<String, Object>();
	private HashMap<String, Object> mainPanelList = new HashMap<String, Object>();
	private HashMap<String, Fragment> fragmentList = new HashMap<String, Fragment>();

	private Label labelWorkspace;

	public NavigationBorder(final String id, UserBean userBean) {
		super(id);

		final Link homeLink = new Link("homeLink") {

			@Override
			public void onClick() {
				setResponsePage(new MainPage());
			}

		};

		MenuPanel pMenu = new MenuPanel("menuPanel");
		PanelItem pMenuItem = new PanelItem("pMenu", "pMenuItem", pMenu);
		add(pMenu);
		add(pMenuItem);
		pMenuItem.add(homeLink);

		Link loginPageLink = new Link("loginPageLink"){

			@Override
			public void onClick() {

				try {

					BaseSession session = (BaseSession) getSession();

					session.signOut();

					setResponsePage(LoginPage.class);

				} catch(Exception e) {
					logger.error("Error at NavigationBorder.constructor: " + e.getMessage());
					e.printStackTrace();
				}
			}
		};

		//-- BEGIN ADMIN PANEL
		AdminPanel adminPanel = new AdminPanel("adminPanel");
		Fragment adminFragment = makeAdminPanel(adminPanel);
		pMenu.add(adminPanel);
		pMenuItem.add(adminFragment);
		fragmentList.put("admin", adminFragment);
		pMenuItem.add(loginPageLink);
		labelWorkspace = new Label("labelWorkspace", userBean.getRoleName().toUpperCase() + " WORKSPACE");
		add(labelWorkspace);

	}

	private Fragment adminLinks() {

		logger.info("NavigationBorder.adminLinks -- ENTER -- ");

		Fragment adminFragment = new Fragment("adminLinkPanelContainer", "adminLinkPanel", this.getParent());

		Link<Object> admin = new Link<Object>("admin") {

			@Override
			public void onClick() {

//				setResponsePage(cls);

			}

		};

		return adminFragment;
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
//				setResponsePage(AdminDepartmentCreatePage.class);

			}

		};
		adminDepartmentViewPanel.setVisible(true);
		adminDepartmentFragment.add(adminDepartmentViewPanel);
		subPanelList.put("adminDepartmentViewPanel", adminDepartmentViewPanel);

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

	private Fragment makeAdminEditUser() {


		return null;
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









		adminFragment.add(adminDepartmentPanel);
		adminFragment.add(adminDepartmentPanelLink);

		adminFragment.add(adminBatchPanel);
		adminFragment.add(adminBatchPanelLink);

		return adminFragment;
	}

	/*
	 * TODO: Create UserPanel class and HTML
	 * private Fragment makeUserPanel(UserPanel userPanel){


		return userFragment;
	}*/

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
