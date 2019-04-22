package com.devworker.kms.dto.common;

import java.io.File;
import java.time.LocalDateTime;

import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;

public class CommentDto {

	private long boardId;

	private long cmtId;

	private String cmtContents;

	private String cmtCode;

	private String cmtUserId;

	private LocalDateTime cmtDate;

	private long cmtLike;

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

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtContents() {
		return cmtContents;
	}

	public void setCmtContents(String cmtContents) {
		this.cmtContents = cmtContents;
	}

	public String getCmtCode() {
		return cmtCode;
	}

	public void setCmtCode(String cmtCode) {
		this.cmtCode = cmtCode;
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

	public long getCmtLike() {
		return cmtLike;
	}

	public void setCmtLike(long cmtLike) {
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
		return "CommentDto [boardId=" + boardId + ", cmtId=" + cmtId + ", cmtContents=" + cmtContents + ", cmtCode="
				+ cmtCode + ", cmtUserId=" + cmtUserId + ", cmtDate=" + cmtDate + ", cmtLike=" + cmtLike + ", docId="
				+ docId + ", docName=" + docName + "]";
	}


}
