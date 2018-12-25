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
	
	@Column(name = "title")
	String title;
	@Column(name = "solution")
	String solution;
	@Column(name = "site")
	String site;
	@Column(name = "content")
	String content;
	@Column(name = "username")
	String userName;
	
	@Column(name = "reg_date")
	LocalDate regDate;
	@Column(name = "up_date")
	LocalDate upDate;
	
	@Column(name = "replycount")
	int replyCount;
	@Column(name = "viewcount")
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

}
