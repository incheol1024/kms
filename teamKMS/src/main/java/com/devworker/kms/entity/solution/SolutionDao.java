package com.devworker.kms.entity.solution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KMS_Solution")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SolutionDao {
	@Id
	@Column(name = "board_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long boardId;
	
	@Column(name = "solution")
	String solution;
	@Column(name = "site")
	String site;
	@Column(name = "reply_count")
	int replyCount;
	
	public Long getId() {
		return boardId;
	}

	public void setId(Long boardId) {
		this.boardId = boardId;
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
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

}
