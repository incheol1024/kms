package com.devworker.kms.dao.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devworker.kms.dao.UserDao;

@Entity
@Table(name = "KMS_LIKE_CHECK")
public class LikeCheckDao {

	@Id
	@Column(name = "like_check_id")
	@GeneratedValue
	private int likeCheckId;
	
	@ManyToOne
	@JoinColumn(name = "cmt_id")
	private CommentDao cmtId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDao userId;

	@Column(name = "like_yn")
	private int likeYn;

	@Column(name = "unlike_yn")
	private int unlikeYn;

	public CommentDao getCmtId() {
		return cmtId;
	}

	public void setCmtId(CommentDao cmtId) {
		this.cmtId = cmtId;
	}

	public UserDao getUserId() {
		return userId;
	}

	public void setUserId(UserDao userId) {
		this.userId = userId;
	}

	public int getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(int likeYn) {
		this.likeYn = likeYn;
	}

	public int getUnlikeYn() {
		return unlikeYn;
	}

	public void setUnlikeYn(int unlikeYn) {
		this.unlikeYn = unlikeYn;
	}

	@Override
	public String toString() {
		return "LikeDao [cmtId=" + cmtId + ", userId=" + userId + ", likeYn=" + likeYn + ", unlikeYn=" + unlikeYn + "]";
	}

}
