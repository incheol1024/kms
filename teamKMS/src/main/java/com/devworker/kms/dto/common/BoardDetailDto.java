package com.devworker.kms.dto.common;

import com.devworker.kms.entity.common.BoardDao;

public class BoardDetailDto extends BoardDto{
    private String contents;
    private boolean readOnly = false;

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public BoardDao toDao() {
        BoardDao dao = new BoardDao();
        dao.setBoardId(boardId);
        dao.setHits(hits);
        dao.setSubject(subject);
        dao.setUserId(userId);
        dao.setRegDate(regDate);
        dao.setUpdDate(updDate);
        dao.setContents(contents);
        return dao;
    }
}
