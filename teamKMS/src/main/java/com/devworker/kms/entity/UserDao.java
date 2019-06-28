package com.devworker.kms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devworker.kms.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KMS_User")
public class UserDao {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_type")
    private String type;
    @Column(name = "user_group")
    private int groupId;
    @Column(name = "user_pass")
    private String password;
    @Column(name = "user_avatar")
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonIgnore
    public UserDto getDto() {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setPassword(password);
        dto.setType(type);
        dto.setGroupId(groupId);
        dto.setAvatar(avatar);
        return dto;
    }
}
