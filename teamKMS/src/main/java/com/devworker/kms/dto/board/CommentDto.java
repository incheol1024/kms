package com.devworker.kms.dto.board;

import java.io.File;
import java.time.LocalDateTime;

import com.devworker.kms.entity.board.CommentDao;
import com.devworker.kms.entity.board.DocDao;

public class CommentDto {

	private int cmtId;

	private String cmtContents;

	private String cmtUserId;

	private LocalDateTime cmtDate;

	private int cmtLike;

	private long docId;

	private String docName;

	public CommentDto() {
	}

	public CommentDto(CommentDao comment, DocDao doc) {
		setComment(comment);
		setDoc(doc);
	}

	public CommentDto(CommentDao comment) {
		setComment(comment);
	}

	private void setDoc(DocDao doc) {
		this.docId = doc.getDocId();
		this.docName = bringDocName(doc.getDocPath());
	}

	private void setComment(CommentDao comment) {
		this.cmtId = comment.getCmtId();
		this.cmtContents = comment.getCmtContents();
		this.cmtUserId = comment.getCmtUserId();
		this.cmtDate = comment.getCmtDate();
		this.cmtLike = comment.getCmtLike();
	}

	private String bringDocName(String docPath) {
		int lastIndex = docPath.lastIndexOf(File.separator);

		if (lastIndex == -1)
			return docPath;
		return docPath.substring(lastIndex + 1);
	}

	public int getCmtId() {
		return cmtId;
	}

	public void setCmtId(int cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtContents() {
		return cmtContents;
	}

	public void setCmtContents(String cmtContents) {
		this.cmtContents = cmtContents;
	}

	public String getCmtUserId() {
		return cmtUserId;
	}

	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}

	public LocalDateTime getCmtDate() {
		return cmtDate;
	}

	public void setCmtDate(LocalDateTime cmtDate) {
		this.cmtDate = cmtDate;
	}

	public int getCmtLike() {
		return cmtLike;
	}

	public void setCmtLike(int cmtLike) {
		this.cmtLike = cmtLike;
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	@Override
	public String toString() {
		return "CommentDto [cmtId=" + cmtId + ", cmtContents=" + cmtContents + ", cmtUserId=" + cmtUserId + ", cmtDate="
				+ cmtDate + ", cmtLike=" + cmtLike + ", docId=" + docId + ", docName=" + docName + ", getCmtId()="
				+ getCmtId() + ", getCmtContents()=" + getCmtContents() + ", getCmtUserId()=" + getCmtUserId()
				+ ", getCmtDate()=" + getCmtDate() + ", getCmtLike()=" + getCmtLike() + ", getDocId()=" + getDocId()
				+ ", getDocName()=" + getDocName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
}
