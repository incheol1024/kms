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

import com.devworker.kms.dto.board.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KMS_DOC")
public class DocDao {

//	@ManyToOne
//	@JoinColumn(name="boardId",foreignKey = @ForeignKey(name = "FK_TBL_DOC_TBL_BOARD"))

	@Column(name = "board_id")
	int boardId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id")
	int docId;

	@Column(name = "doc_path")
	String docPath;

	@Column(name = "doc_user_id")
	String docUserId;

	@Column(name = "doc_size")
	int docSize;

//	@Column(name = "comment_id")
//	int commentId;

	public DocDao() {
	}

	public DocDao(CommentDto commentDto) {
		this.boardId = commentDto.getBoardId();
		this.docPath = commentDto.getFilePath();
		this.docUserId = commentDto.getCmtUserId();
		this.docSize = (int) commentDto.getMultiPartFile().getSize();
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public void setBoardID(int boardId) {
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

	public int getDocSize() {
		return docSize;
	}

	public void setDocSize(int docSize) {
		this.docSize = docSize;
	}

/*	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
*/
}
