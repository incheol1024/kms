package com.devworker.kms.dao.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_DOC")
public class DocDao {

	@ManyToOne
	@JoinColumn(name="boardId",foreignKey = @ForeignKey(name = "FK_TBL_DOC_TBL_BOARD"))
	BoardDao boardId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id")
	int docId;

	@Column(name = "doc_path")
	String docPath;

	@Column(name = "doc_user_id")
	String docUserId;

	@Column(name = "doc_size")
	String docSize;

	public BoardDao getBoardID() {
		return boardId;
	}

	public void setBoardID(BoardDao boardId) {
		this.boardId = boardId;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
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

	public String getDocSize() {
		return docSize;
	}

	public void setDocSize(String docSize) {
		this.docSize = docSize;
	}

}
