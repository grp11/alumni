package id.ac.sgu.ui.admin.batch;

import id.ac.sgu.base.BasePage;
import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.utility.Cons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
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

public class AdminBatchViewPage extends BasePage {

	private static Logger logger = Logger.getLogger(AdminBatchViewPage.class);

	private BatchBean bean;

	public AdminBatchViewPage() {

		bean = new BatchBean();

		CompoundPropertyModel<BatchBean> model = new CompoundPropertyModel<BatchBean>(bean);

		AdminBatchViewForm adminBatchViewForm = new AdminBatchViewForm(
				"adminBatchViewForm", model, null);

		add(initNavigationBorder(adminBatchViewForm));

	}

	public AdminBatchViewPage(PageParameters param) {

		CompoundPropertyModel<BatchBean> model = null;

		if (param != null) {

			bean = (BatchBean) param.get("batch");

			if (bean != null) {
				model = new CompoundPropertyModel<BatchBean>(bean);
			}

		}

		AdminBatchViewForm adminBatchViewForm = new AdminBatchViewForm(
				"adminBatchViewForm", model, param);
		add(initNavigationBorder(adminBatchViewForm));

	}

	private class AdminBatchViewForm extends Form<BatchBean> {

		private DropDownChoice<String> ddcBatchYear;

		private Button btnSubmit;

		private Link<Object> btnCancel;

		private FeedbackPanel feedbackPanel;

		private List<BatchBean> batchDataSources;

		private DataView<BatchBean> dataView;

		private PagingNavigator pagingNavigator;

		private List<String> batchList;

		private String selectedBatch;

		/**
		 * @param selectedBatch the selectedBatch to set
		 */
		public void setSelectedBatch(String selectedBatch) {
			this.selectedBatch = selectedBatch;
		}

		/**
		 * @return the selectedBatch
		 */
		public String getSelectedBatch() {
			return selectedBatch;
		}

		private void init(PageParameters param) {
			populateBatches();

			batchDataSources = new ArrayList<BatchBean>();

			processBatchSelect(param);

			populateDataGrid();
		}

		private void processBatchSelect(PageParameters param) {
			try {
				System.out.println("selectedBatch= " + selectedBatch);

				if (selectedBatch == null) {
					populateBatchDataSources();

				}

				if (bean.getBatchYear() != 0)
				{

					logger.info("Input batch year masukk ke SETUJU: " + bean.getBatchYear());

					//populateBatchDataSources(Integer.parseInt(selectedBatch));
					populateBatchDataSources(bean.getBatchYear());

				}
				else {
					System.out.println("Input batch year: " + bean.getBatchYear());
//					populateBatchDataSources(Integer.parseInt(selectedBatch));
					populateBatchDataSources(bean.getBatchYear());

				}

			} catch(NumberFormatException e) {
				System.out.println("An execption has received: selectedBatch= " + selectedBatch);
				if (selectedBatch != null)
				if (selectedBatch.equalsIgnoreCase(Cons.ALL)) {
					populateBatchDataSources();
				}
				else {
					e.printStackTrace();
					getWebSession().error(e.getMessage());
				}
			}
		}

		private void populateBatches() {
			batchList = new Vector<String>();

			List<BatchBean> listTemp = alumniService.findAllBatches();

	//		batchList.add(Cons.CHOOSE);
	//		batchList.add(Cons.ALL);

			if (null != listTemp) {

				while (!listTemp.isEmpty()) {

					BatchBean temp = listTemp.remove(0);
					batchList.add(Integer.toString(temp.getBatchYear()));

				}
				Collections.sort(batchList);
			}

		}

		private void populateBatchDataSources() {

			List<BatchBean> list = null;

			try {
				list = alumniService.findAllBatchesInMapping();

				if (null != list) {
					for (BatchBean bb : list) {
						batchDataSources.add(bb);
					}
				}

			} catch(Exception e) {
				e.printStackTrace();
				getWebSession().info(e.getMessage());
			}

		}

		private void populateBatchDataSources(int year) {

			List<BatchBean> list = null;

			try {
				list = alumniService.findAllBatchesInMapping(year);

				if (null != list) {
					for (BatchBean bb : list) {
						batchDataSources.add(bb);
						System.out.println("ambil nama department: " + bb.getDepartmentName());

					}

				}

			} catch(Exception e) {
				e.printStackTrace();
				getWebSession().info(e.getMessage());
			}

		}

		private ListDataProvider<BatchBean> SearchDataProvider() {

			return null;
		}

		@SuppressWarnings("serial")
		private void populateDataGrid() {

			dataView = new DataView<BatchBean>("batchDataView", new ListDataProvider<BatchBean>(batchDataSources)) {

				@Override
				protected void populateItem(final Item<BatchBean> item) {

					BatchBean batch = item.getModelObject();
					item.add(new Label("lblBatchId", String.valueOf(batch.getBatchId())));
					item.add(new Label("lblBatchYear", String.valueOf(batch.getBatchYear())));
					item.add(new Label("lblDepartmentName", batch.getDepartmentName()));
					item.add(new Label("lblFacultyName", batch.getFacultyName()));

					item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel<String>() {

						@Override
						public String getObject() {
							return (item.getIndex() % 2 == 1) ? "even" : "odd";
						}

					}));

				}
			};

			dataView.setItemsPerPage(12);

		}

		public AdminBatchViewForm(String id, IModel<BatchBean> model, PageParameters pageParameters) {
			super(id, model);

			init(pageParameters);

			if (pageParameters != null)

			btnSubmit = new Button("btnSubmit") {

				@Override
				public void onSubmit() {

					try {
						System.out.println("get batch year: " + bean.getBatchYear());


						PageParameters param = new PageParameters();
						param.put("batch", bean);
						setResponsePage(new AdminBatchViewPage(param));

					} catch(Exception e) {
						e.printStackTrace();
						getWebSession().error(e.getMessage());
					}

				}

			};


			feedbackPanel = new FeedbackPanel("feedbackPanel");

			ddcBatchYear = new DropDownChoice<String>("batchYear", batchList);

			btnCancel = new Link<Object>("btnCancel") {

				@Override
				public void onClick() {

			//		String bInput = ddcBatchYear.getModelObject();
					System.out.println("ON SELECT: " + ddcBatchYear.getModelValue());
					System.out.println("ON SELECT VALUE: " + selectedBatch);

//					int bInput = Integer.parseInt(ddcBatchYear.getModelObject());

					//System.out.println("bInput : " + bInput);
//					int bInputs = Integer.parseInt(bInput);
	//				System.out.println("bInputs : " + bInputs);

	//				bean.setBatchYear(bInput);

					PageParameters param = new PageParameters();

					param.put("batch", bean);

					setResponsePage(new AdminBatchViewPage(param));

				}

			};
			dataView.setOutputMarkupId(true);
			add(dataView);


			pagingNavigator = new PagingNavigator("navigator", dataView);

	        //encapsulate the ListView in a WebMarkupContainer in order for it to update
	        final WebMarkupContainer listContainer = new WebMarkupContainer("dataContainer");
	        //generate a markup-id so the contents can be updated through an AJAX call
	        listContainer.setOutputMarkupId(true);
	//        listContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(3)));

	        listContainer.add(dataView);

	        add(listContainer);

			OnChangeAjaxBehavior onChangeAjaxBehavior = new OnChangeAjaxBehavior()
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{

					selectedBatch = String.valueOf(ddcBatchYear.getModelObject());

					System.out.println("selectedBatch in AJAX: " + selectedBatch);

					target.addComponent(listContainer);
				}
			};
			ddcBatchYear.add(onChangeAjaxBehavior);

			add(ddcBatchYear);

/*			ddcBatchYear.add(new AjaxFormComponentUpdatingBehavior("onchange") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
//					respond(target);
				//	remove(dataView);


					selectedBatch = String.valueOf(ddcBatchYear.getModelObject());

					System.out.println("selectedBatch in AJAX: " + selectedBatch);
					init();
					System.out.println("into here");
					target.addComponent(listContainer);
					System.out.println("after refresherrr");

				}
			});
*/

		// change to the WebMarkupContainer hierarchy
			add(btnCancel);
			add(pagingNavigator);
		//	add(btnSubmit);
			add(feedbackPanel);

		}

	}

}
