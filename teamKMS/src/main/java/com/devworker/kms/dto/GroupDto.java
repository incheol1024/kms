package com.devworker.kms.dto;

import java.util.ArrayList;
import java.util.List;

import com.devworker.kms.dao.GroupDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GroupDto {
    public static final GroupDto ROOTGROUP;

	static {
		ROOTGROUP = new GroupDto();
		ROOTGROUP.parentId = -1;
		ROOTGROUP.name = "ROOT";
		ROOTGROUP.id = 0;
	}
	private int id;
	private String name;
	private int parentId;
	private List<GroupDto> children  = new ArrayList<>();
	
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
	public List<GroupDto> getChildren() {
		return children;
	}
	public void setChildren(List<GroupDto> children) {
		this.children = children;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	@JsonIgnore
	public GroupDao getDao() {
		GroupDao dao = new GroupDao();
		dao.setParentid(parentId);
		dao.setName(name);
		dao.setId(id);
		return dao;
	}
}
