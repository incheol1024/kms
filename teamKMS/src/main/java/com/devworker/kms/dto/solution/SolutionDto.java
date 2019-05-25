package com.devworker.kms.dto.solution;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.entity.solution.SolutionDao;

public class SolutionDto {
    long boardId;
    long menuId;

    BoardDto dto;

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public BoardDto getDto() {
        return dto;
    }

    public void setDto(BoardDto dto) {
        this.dto = dto;
    }

    public SolutionDao toDao() {
    	SolutionDao solutionDao = new SolutionDao();
        solutionDao.setBoardId(boardId);
        solutionDao.setMenuId(menuId);
        return solutionDao;
    }
}
