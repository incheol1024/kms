package com.devworker.kms.entity.solution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KMS_Solution")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SolutionDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long board_id;
	
	@Column(name = "solution")
	String solution;
	@Column(name = "site")
	String site;
	@Column(name = "reply_count")
	int reply_count;
	
	public Long getId() {
		return board_id;
	}

	public void setId(Long board_id) {
		this.board_id = board_id;
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
	
	public int getReplyCount() {
		return reply_count;
	}

	public void setReplyCount(int reply_count) {
		this.reply_count = reply_count;
	}

}
