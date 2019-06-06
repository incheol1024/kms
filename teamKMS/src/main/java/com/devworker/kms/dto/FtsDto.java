package com.devworker.kms.dto;

import com.devworker.kms.fts.FTSDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FtsDto {
    private int id;
    private String name;
    private String user;
    private String time;
    private int category;
    private String word;

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
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @JsonIgnore
    public FTSDao toDao(){
        FTSDao dao = new FTSDao();
        dao.setCategory(category);
        dao.setId(id);
        dao.setName(name);
        dao.setUser(user);
        dao.setTime(time);
        dao.setWord(word);
        return dao;
    }
}
