package id.ac.sgu.bl.base;

import id.ac.sgu.bean.base.BatchBean;
import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

public class BatchBL extends BaseBL {

	private DAOLocator daoLocator;

	public int createBatch(BatchBean batchBean) {
		int result = daoLocator.getBatchDAO().CreateNew(batchBean);

		if (1 == result)
			result = Cons.CREATE_BATCH_SUCCESS;

		return (0 == result) ? Cons.CREATE_BATCH_FAILURE : result;
	}

	public int deleteBatch(BatchBean batchBean) {
		int result = daoLocator.getBatchDAO().deleteRecent(batchBean);

		if (1 == result)
			result = Cons.DELETE_BATCH_SUCCESS;

		return (0 == result) ? Cons.DELETE_BATCH_FAILURE : result;
	}

	public int updateBatch(BatchBean batchBean) {
		int result = daoLocator.getBatchDAO().updateRecent(batchBean);

		if (1 == result)
			result = Cons.UPDATE_BATCH_SUCCESS;

		return (0 == result) ? Cons.UPDATE_BATCH_FAILURE : result;
	}

	public int createBDFMapping(BatchBean batchBean) {
		int result = daoLocator.getBatchDAO().createBDFMapping(batchBean);

		if (1 == result)
			result = Cons.CREATE_BDF_SUCCESS;

		return (0 == result) ? Cons.CREATE_BATCH_FAILURE : result;
	}

	public int deleteBDFMapping(BatchBean batchBean) {
		int result = daoLocator.getBatchDAO().deleteBDFMapping(batchBean);

		if (1 == result)
			result = Cons.DELETE_BDF_SUCCESS;

		return (0 == result) ? Cons.DELETE_BDF_FAILURE : result;
	}

	public int updateBDFMapping(BatchBean batchBean) {

		int result = daoLocator.getBatchDAO().updateBDFMapping(batchBean);

		if (1 == result)
			result = Cons.UPDATE_BDF_SUCCESS;

		return (0 == result) ? Cons.UPDATE_BATCH_FAILURE : result;
	}

	/**
	 * @return
	 * 		false on failure
	 * 		true on success
	 * */
	public boolean validateBatch(BatchBean batchBean) {
		boolean result = false;

		if (null == batchBean)
			return result;

		if ((batchBean.getBatchYear() <= Cons.MAX_BTCH_YEAR)
		 && (batchBean.getBatchYear() >= Cons.MIN_BTCH_YEAR))
			result = true;

		if (daoLocator.getBatchDAO().existingBatchId(batchBean) == 0)
			result = true;
		else
			result = false;

		return result;
	}

	/**
	 * @param daoLocator the daoLocator to set
	 */
	public void setDaoLocator(DAOLocator daoLocator) {
		this.daoLocator = daoLocator;
	}

	/**
	 * @return the daoLocator
	 */
	public DAOLocator getDaoLocator() {
		return daoLocator;
	}

}

