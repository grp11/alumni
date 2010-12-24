package id.ac.sgu.dao.base;

import id.ac.sgu.bean.base.CommentBean;
import id.ac.sgu.dao.BaseDAO;
import id.ac.sgu.utility.Cons;
import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class CommentDAO extends BaseDAO<CommentBean> {

	public CommentDAO() {
	}

	@Override
	public List<CommentBean> findAll()
	{
		Vector<CommentBean> vComment = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.COMMENTS_VIEW);

		boxDB.addColumn(Cons.ALL_COLUMNS);

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vComment = new Vector<CommentBean>(2);

			while (res.next()) {
				CommentBean commentBean = new CommentBean();

				commentBean.setFromName(res.getString("userName"));
				commentBean.setCreated(res.getDate("commentCreated"));
				commentBean.setCommentContents(res.getString("commentContent"));
				vComment.addElement(commentBean);
			}

			vComment.trimToSize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				boxDB.clear();
				if (!pdb.isClosedStatement())
					pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vComment;
	}

	public List<CommentBean> findAll(CommentBean bean)
	{
		Vector<CommentBean> vComment = null;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable(Cons.COMMENTS_VIEW);

		boxDB.addColumn(Cons.ALL_COLUMNS);

		boxDB.addCondition(0);
		boxDB.addCondition("\"commentTo\"", BoxDB.EQUALS, String.valueOf(bean.getTo()));

		try {
			res = pdb.returnedMultipleQuery(boxDB);

			vComment = new Vector<CommentBean>(2);

			while (res.next()) {
				CommentBean commentBean = new CommentBean();

				commentBean.setFromName(res.getString("userName"));
				commentBean.setCreated(res.getDate("commentCreated"));
				commentBean.setCommentContents(res.getString("commentContent"));
				vComment.addElement(commentBean);
			}

			vComment.trimToSize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				boxDB.clear();
				if (!pdb.isClosedStatement())
					pdb.closeStatement();
				if (!pdb.isResultSetNull())
					pdb.closeResultSet();
				if (res != null)
					this.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vComment;
	}

	@Override
	public int CreateNew(CommentBean bean) {

		int result = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();

		BoxDB boxDB = new BoxDB();

		boxDB.setTable("comments");

		int newCommentId = getLastId() + 1;

		boxDB.addColumn("comment_id", newCommentId);
		boxDB.addColumn("comment_author_id", String.valueOf(bean.getFrom()));
		boxDB.addColumn("comment_to_id", String.valueOf(bean.getTo()));
		boxDB.addColumn("comment_text", bean.getCommentContents());

		try {
			result = pdb.insert(boxDB);
			newCommentId = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				newCommentId = 0;
				pdb.closeStatement();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	protected CommentBean findBy(String key, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int deleteRecent(CommentBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateRecent(CommentBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getLastId()
	{
		int profileId = 0;

		PostgresDB pdb = new PostgresDB();
		pdb.getConnection();
		try {
			profileId = pdb.getLastId("comments", "comment_id");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return profileId;
	}

}
