package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class AdminBatchResultPage extends BasePage {

	private BatchBean bean;

	public AdminBatchResultPage(PageParameters param) {


	}

	private class AdminBatchResultData extends Form<BatchBean> {


		public AdminBatchResultData(String id, IModel<BatchBean> model, PageParameters param) {
			super(id, model);
		}
	}

}
