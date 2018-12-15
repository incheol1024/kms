package com.devworker.kms.dao.board;

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

	@ManyToOne
	@JoinColumn(name = "board_id")
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
	int docSize;

	@ManyToOne
	@JoinColumn(name = "cmt_id")
	CommentDao commentId;

	public DocDao() {
	}

<<<<<<< HEAD
	public BoardDao getBoardId() {
=======
	public int getBoardId() {
>>>>>>> branch 'master' of https://github.com/incheol1024/kns.git
		return boardId;
	}

<<<<<<< HEAD
	public void setBoardId(BoardDao boardId) {
=======
	public void setBoardId(int boardId) {
>>>>>>> branch 'master' of https://github.com/incheol1024/kns.git
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

	public CommentDao getCommentId() {
		return commentId;
	}

	public void setCommentId(CommentDao commentId) {
		this.commentId = commentId;
	}

}
