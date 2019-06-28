package com.devworker.kms.dto;

import com.devworker.kms.entity.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class UserDto{
	@NotBlank
	@ApiModelProperty(notes = "사용자의 아이디. 중복 불가능하다.",required = true)
	private String id;
	@ApiModelProperty(notes = "사용자의 표기상의 이름",required = true)
	@NotBlank
	private String name;
	@ApiModelProperty(notes = "사용자의 type. 현재는 이를 특별히 사용하지 않는다",required = true)
	@NotBlank
	private String type;
	@ApiModelProperty(notes = "현재 부모 그룹. 반드시 지정되어야 한다.",required = true)
	private int groupId;
	@ApiModelProperty(notes = "부모 그룹의 이름. 특정한 경우에만 데이터가 채워진다.")
	private String groupName;
	@ApiModelProperty(notes = "사용자의 암호. 어떤경우에도 암호가 복호화 된 상태로 전달되어서는 안된다.")
	@NotBlank
	private String password;

	@ApiModelProperty(notes = "사용자의 아바타의 S3 경로")
	private String avatar;
	
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@JsonIgnore
	public UserDao getDao() {
		UserDao dao = new UserDao();
		dao.setId(id);
		dao.setName(name);
		dao.setGroupId(groupId);
		dao.setPassword(password);
		dao.setType(type);
		dao.setAvatar(avatar);
		return dao;
	}
}
