package id.ac.sgu.base;

import id.ac.sgu.ui.main.NavigationBorder;
import id.ac.sgu.utility.login.LoginService;
import id.ac.sgu.utility.service.AlumniService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BasePage extends WebPage {

	private static Logger logger = Logger.getLogger(BasePage.class);

	@SpringBean(name="loginServiceBean")
	protected LoginService loginService;

	@SpringBean(name="alumniServiceBean")
	protected AlumniService alumniService;

	@SpringBean(name="userServiceBean")
	protected AlumniService userService;

	public BasePage() {
		// CSS
		//add(CSSPackageResource.getHeaderContribution(this.getClass(), "styles.css"));

		// Javascript
		//add(JavascriptPackageResource.getHeaderContribution(this.getClass(), ""));

	}

	protected boolean validateEmail(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	protected boolean validateDigitCharacter(String digit) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(digit);
		return m.matches();
	}

	protected boolean validateAlphabetic(String s) {
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	protected boolean validateLetterWithSpace(String s) {
		Pattern p = Pattern.compile("[a-zA-Z ]+");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	protected void refreshForm(Form<Object> form, String formId, Component newComponent){
		form.remove(form.get(formId));
		form.add(newComponent);
	}

	protected NavigationBorder initNavigationBorder(Form tobeAttachedForm) {
	//	logger.info("BasePage.initNavigationBorder -- ENTER --");

		NavigationBorder navigationBorder = getWebSession().getNavigationBorder();
		Form form = navigationBorder.getAttachedForm();

		if (form != null) {
			if (!tobeAttachedForm.getId().equals(form.getId())) {

		//		logger.info("Into here --!tobeAttachedForm.getId()");

				navigationBorder.remove(form);
				navigationBorder.add(tobeAttachedForm);

				navigationBorder.setAttachedForm(tobeAttachedForm);
				getWebSession().setNavigationBorder(navigationBorder);

			}
		}

	//	logger.info("BasePage.initNavigationBorder -- EXIT --");

		return navigationBorder;
	}

	public BaseSession getWebSession(){
		return (BaseSession) Session.get();
	}

}
