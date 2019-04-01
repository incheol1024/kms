package com.devworker.kms.entity.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_DOC")
public class DocDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id")
	long docId;

	@ManyToOne
	@JoinColumn(name = "board_id")
	BoardDao boardId;

	@ManyToOne
	@JoinColumn(name = "cmt_id")
	CommentDao cmtId;

	@Column(name = "doc_path")
	String docPath;

	@Column(name = "doc_user_id")
	String docUserId;

	@Column(name = "doc_size")
	int docSize;

	public DocDao() {
	}

	public DocDao(Long docId, CommentDao cmtId) {
		this.docId = docId;
		this.cmtId = cmtId;
	}

	public BoardDao getBoardId() {
		return boardId;
	}

	public void setBoardId(BoardDao boardId) {
		this.boardId = boardId;
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getDocUserId() {
		return docUserId;
	}

	public void setDocUserId(String docUserId) {
		this.docUserId = docUserId;
	}

	public int getDocSize() {
		return docSize;
	}

	public void setDocSize(int docSize) {
		this.docSize = docSize;
	}

	public CommentDao getCmtId() {
		return cmtId;
	}

	public void setCmtId(CommentDao cmtId) {
		this.cmtId = cmtId;
	}

	@Override
	public String toString() {
		return "DocDao [docId=" + docId + ", boardId=" + boardId + ", cmtId=" + cmtId + ", docPath=" + docPath
				+ ", docUserId=" + docUserId + ", docSize=" + docSize + ", getBoardId()=" + getBoardId()
				+ ", getDocId()=" + getDocId() + ", getDocPath()=" + getDocPath() + ", getDocUserId()=" + getDocUserId()
				+ ", getDocSize()=" + getDocSize() + ", getCmtId()=" + getCmtId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
