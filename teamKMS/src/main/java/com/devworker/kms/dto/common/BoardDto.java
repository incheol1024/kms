package com.devworker.kms.dto.common;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.util.CommonUtil;

import java.time.LocalDateTime;

public class BoardDto {
    long boardId;
    String subject;
    String contents;
    String userId;
    LocalDateTime regDate = LocalDateTime.now();
    LocalDateTime updDate  = LocalDateTime.now();
    int hits = 0;

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public BoardDao toDao() {
        BoardDao dao = new BoardDao();
        dao.setBoardId(boardId);
        dao.setHits(hits);
        dao.setSubject(subject);
        dao.setContents(contents);
        dao.setUserId(userId);
        dao.setRegDate(regDate);
        dao.setUpdDate(updDate);
        return dao;
    }
}
