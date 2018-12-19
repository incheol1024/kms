package com.devworker.kms.dao.solution;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KMSSolution")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SolutionDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "title", nullable = false, length = 300)
	String title;
	@Column(name = "solution", nullable = false, length = 100)
	String solution;
	@Column(name = "site", nullable = false, length = 100)
	String site;
	@Column(name = "content", nullable = false, length = 4000)
	String content;
	@Column(name = "username", nullable = false, length = 100)
	String userName;
	
	@Column(name = "reg_date", nullable = false)
	LocalDate regDate;
	@Column(name = "up_date", nullable = false)
	LocalDate upDate;
	
	@Column(name = "replycount", nullable = false)
	int replyCount;
	@Column(name = "viewcount", nullable = false)
	int viewCount;
	
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

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
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
	
	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public LocalDate getUpdDate() {
		return upDate;
	}

	public void setUpdDate(LocalDate updDate) {
		this.upDate = updDate;
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
		return "SolutionDto [id=" + id + ", title=" + title + ", solution=" + solution + ", site=" + site + ", content=" + content + ", userName=" + userName
				+ ", reg_date=" + regDate + ", up_date=" + upDate + ", replyCount=" + replyCount + ", viewCount=" + viewCount + "]";
	}
}
