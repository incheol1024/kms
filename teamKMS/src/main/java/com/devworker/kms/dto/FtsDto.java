package com.devworker.kms.dto;

import com.devworker.kms.fts.FTSDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FtsDto {
    private long id;
    private String name;
    private String user;
    private String text;
    private String time;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @JsonIgnore
    public FTSDao toDao(){
        FTSDao dao = new FTSDao();
        dao.setId(id);
        dao.setName(name);
        dao.setUser(user);
        dao.setText(text);
        dao.setTime(time);
        return dao;
    }

}
