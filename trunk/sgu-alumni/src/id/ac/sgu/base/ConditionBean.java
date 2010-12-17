package id.ac.sgu.base;

import id.ac.sgu.bean.IBaseBean;

public class ConditionBean implements IBaseBean {

	private String left;
	private String clause;
	private String right;
	private String conjunction;
	
	public ConditionBean(String left, String clause, String right) {
		this.left = left;
		this.clause = clause;
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public String getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/**
	 * @return the clause
	 */
	public String getClause() {
		return clause;
	}

	/**
	 * @param clause the clause to set
	 */
	public void setClause(String clause) {
		this.clause = clause;
	}

	/**
	 * @return the right
	 */
	public String getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * @return the conjunction
	 */
	public String getConjunction() {
		return conjunction;
	}

	/**
	 * @param conjunction the conjunction to set
	 */
	public void setConjunction(String conjunction) {
		this.conjunction = conjunction;
	}

	@Override
	public void clear() {
		this.left = "";
		this.right = "";
		this.clause = "";
		this.conjunction = "";
	}

}
