package com.devworker.kms.dto;

import com.devworker.kms.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto{
	private String id;
	private String name;
	private String type;
	private int groupId;
	private String groupName;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@JsonIgnore
	public UserDao getDao() {
		UserDao dao = new UserDao();
		dao.setId(id);
		dao.setName(name);
		dao.setGroupId(groupId);
		dao.setPassword(password);
		dao.setType(type);
		return dao;
	}
}
