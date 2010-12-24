package id.ac.sgu.bean.base;

import id.ac.sgu.bean.IBaseBean;

import java.io.Serializable;
import java.util.Date;

public class CommentBean implements IBaseBean, Serializable {

	private static final long serialVersionUID = -3292552461427203077L;

	private int commentId;
	private String commentContents;
	private Date modified;
	private Date created;
	private int to;
	private String toName;
	private int from;
	private String fromName;
	private int commentLikes;
	private int commentRetweet;

	// Added 21 Dec 2010
	private String departmentName;
	private int departmentId;

	private String facultyName;
	private int facultyId;

	private int batchYear;
	private int batchId;

	public CommentBean() {
	}

	/**
	 * @return the commentId
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the commentContents
	 */
	public String getCommentContents() {
		return commentContents;
	}

	/**
	 * @param commentContents the commentContents to set
	 */
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * @return the toName
	 */
	public String getToName() {
		return toName;
	}

	/**
	 * @param toName the toName to set
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * @return the fromName
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * @return the commentLikes
	 */
	public int getCommentLikes() {
		return commentLikes;
	}

	/**
	 * @param commentLikes the commentLikes to set
	 */
	public void setCommentLikes(int commentLikes) {
		this.commentLikes = commentLikes;
	}

	/**
	 * @return the commentRetweet
	 */
	public int getCommentRetweet() {
		return commentRetweet;
	}

	/**
	 * @param commentRetweet the commentRetweet to set
	 */
	public void setCommentRetweet(int commentRetweet) {
		this.commentRetweet = commentRetweet;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}

	/**
	 * @param facultyName the facultyName to set
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	/**
	 * @return the facultyId
	 */
	public int getFacultyId() {
		return facultyId;
	}

	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	/**
	 * @return the batchYear
	 */
	public int getBatchYear() {
		return batchYear;
	}

	/**
	 * @param batchYear the batchYear to set
	 */
	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
	}

	/**
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	@Override
	public void clear() {
		this.commentId = 0;
		this.commentContents = null;
		this.modified = null;
		this.created = null;
		this.to = 0;
		this.from = 0;
		this.commentLikes = 0;
		this.commentRetweet = 0;
		this.departmentName = null;
		this.facultyName = null;
		this.departmentId = 0;
		this.facultyId = 0;
		this.batchYear = 0;
		this.batchId = 0;
	}

}
