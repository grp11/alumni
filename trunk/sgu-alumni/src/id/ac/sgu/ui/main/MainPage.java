package id.ac.sgu.ui.main;

import id.ac.sgu.base.BasePage;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;

public class MainPage extends BasePage {

	private static Logger logger = Logger.getLogger(MainPage.class);
	
	public MainPage() {
			
		try {
			logger.info("MainPage.Constructor -- ENTER --");
			MainForm loginForm = new MainForm("mainForm");
			add(initNavigationBorder(loginForm));
			
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	
	}
	
	private class MainForm extends Form {

		private static final long serialVersionUID = -1822897122337315780L;

		public MainForm(String id) {

			super(id);
		
		}
		
		@Override
		protected void delegateSubmit(IFormSubmittingComponent submittingComponent) {
			
			if (submittingComponent != null)
				submittingComponent.onSubmit();
			
		}
			
	}
	
}
