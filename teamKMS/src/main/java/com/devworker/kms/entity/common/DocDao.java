package com.devworker.kms.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devworker.kms.dto.common.FileDto;

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

	@Column(name = "doc_name")
	String docName;

	@Column(name = "doc_ext")
	String docExt;

	@Column(name = "doc_user_id")
	String docUserId;

	@Column(name = "doc_size")
	long docSize;

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

	public long getDocSize() {
		return docSize;
	}

	public void setDocSize(long docSize) {
		this.docSize = docSize;
	}

	public CommentDao getCmtId() {
		return cmtId;
	}

	public void setCmtId(CommentDao cmtId) {
		this.cmtId = cmtId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocExt() {
		return docExt;
	}

	public void setDocExt(String docExt) {
		this.docExt = docExt;
	}

	@Override
	public String toString() {
		return "DocDao [docId=" + docId + ", boardId=" + boardId + ", cmtId=" + cmtId + ", docPath=" + docPath
				+ ", docName=" + docName + ", docExt=" + docExt + ", docUserId=" + docUserId + ", docSize=" + docSize
				+ "]";
	}
	
	public void setUpEntity(FileDto fileDto) {
		this.docExt = fileDto.getFileExt();
		this.docName = fileDto.getFileName();
		this.docPath = fileDto.getKey();
		this.docSize = fileDto.getFileSize();
	}

}
