package com.devworker.kms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMSCommonPost")
public class PostDao{
	@Id
	@Column(name = "post_id")
	private int id;
	@Column(name="post_name")
	private String name;
	@Column(name="post_user")
	private String user;
	@Column(name="post_time")
	private String time;
	@Column(name="post_category")
	private int category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	
}