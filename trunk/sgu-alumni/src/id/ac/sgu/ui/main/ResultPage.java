package id.ac.sgu.ui.main;

import id.ac.sgu.base.BaseApplication;
import id.ac.sgu.base.BasePage;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class ResultPage extends BasePage {

	private static Logger logger = Logger.getLogger(ResultPage.class);

	public ResultPage(PageParameters pageParameters)
	{
		ResultForm resultForm = new ResultForm("resultForm", pageParameters);
		add(initNavigationBorder(resultForm));
	}

	private class ResultForm extends Form<Object>
	{

		private FeedbackPanel feedbackPanel;

		private String genNum = "";
		private Label lblGenNum;

		private Button btnMainPage;
		private Button btnBack;

		public ResultForm(String id, final PageParameters param)
		{
			super(id);

			if ((String) param.getKey("genNum") != null)
			{
				genNum = "Sukses";
			}

			lblGenNum = new Label("genNum", genNum);

			btnMainPage = new Button("btnMainPage")
			{

				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit()
				{
					setResponsePage(MainPage.class);
				}
			};

			btnBack = new Button("btnBack")
			{

				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit()
				{
					if (param != null)
						setResponsePage((Class) param.get("again"));
					else
						setResponsePage(MainPage.class);
				}
			};

			feedbackPanel = new FeedbackPanel("feedbackPanel");

			add(lblGenNum);
			add(btnBack);
			add(btnMainPage);
			add(feedbackPanel);

		}

		@Override
		protected void delegateSubmit(IFormSubmittingComponent component)
		{
			if (component != null)
			{
				component.onSubmit();
			}
		}

	}

}
