package com.devworker.kms.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GroupDto {
	private int id;
	private String name;
	@JsonInclude(Include.NON_EMPTY)
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
	
	
}
