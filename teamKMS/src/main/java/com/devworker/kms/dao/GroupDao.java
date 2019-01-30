package com.devworker.kms.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devworker.kms.dto.GroupDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KMSGroup")
public class GroupDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int id;
	@Column(name="group_name")
	private String name;
	@Column(name="group_parent")
	private Integer parentId;
	
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	@JsonIgnore
	public GroupDto getDto() {
		GroupDto dto = new GroupDto();
		dto.setId(id);
		dto.setName(name);
		dto.setParentId(parentId);
		return dto;
	}

	
}
