package com.devworker.kms.dao.qna;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devworker.kms.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KMSQna")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QnaDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "title")
	String title;

	@Column(name = "content")
	String content;
	@Column(name = "username")
	String userName;
	@Column(name = "replycount")
	int replyCount;
	@Column(name = "viewcount")
	int viewCount;

	//@ManyToOne
	//UserDao user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return "QnaDto [id=" + id + ", title=" + title + ", content=" + content + ", userName=" + userName
				+ ", replyCount=" + replyCount + ", viewCount=" + viewCount + "]";
	}

}
