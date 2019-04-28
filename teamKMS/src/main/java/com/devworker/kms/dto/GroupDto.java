package com.devworker.kms.dto;

import java.util.ArrayList;
import java.util.List;

import com.devworker.kms.entity.GroupDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class GroupDto {
    public static final GroupDto ROOTGROUP;

	static {
		ROOTGROUP = new GroupDto();
		ROOTGROUP.parentId = -1;
		ROOTGROUP.name = "ROOT";
		ROOTGROUP.id = 0;
	}

	@ApiModelProperty(notes = "그룹 아이디. 디비에서 Auto increase한다")
	@Min(value = 0)
	private int id;

	@ApiModelProperty(notes = "그룹의 이름",required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(notes = "부모 그룹의 이름",required = true)
	@Min(value = 0)
	private int parentId;

	@ApiModelProperty(notes = "자식 그룹의 리스트. 특정 함수에서만 데이터가 채워짐")
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
		dao.setParentId(parentId);
		dao.setName(name);
		dao.setId(id);
		return dao;
	}
}
