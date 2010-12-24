package id.ac.sgu.bl.base;

import id.ac.sgu.bean.base.CommentBean;
import id.ac.sgu.bl.BaseBL;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.factory.DAOLocator;

public class CommentBL extends BaseBL {

	public DAOLocator daoLocator;


	public int createNew(CommentBean bean)
	{
		int result = daoLocator.getCommentDAO().CreateNew(bean);
		return (result == 0) ?
				Cons.POST_COMMENT_FAILURE : Cons.POST_COMMENT_SUCCESS;
	}

	/**
	 * @return the daoLocator
	 */
	public DAOLocator getDaoLocator() {
		return daoLocator;
	}

	/**
	 * @param daoLocator the daoLocator to set
	 */
	public void setDaoLocator(DAOLocator daoLocator) {
		this.daoLocator = daoLocator;
	}

}
