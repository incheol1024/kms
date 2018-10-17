package com.devworker.kms.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMSGroup")
public class GroupDao {
	@Id
	@Column(name = "group_id")
	private int id;
	@Column(name="group_name")
	private String name;
	@Column(name="group_parent",nullable = true)
	private int parentid;
	
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
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	
}
