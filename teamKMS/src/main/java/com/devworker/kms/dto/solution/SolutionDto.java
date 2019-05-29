package com.devworker.kms.dto.solution;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.solution.SolutionDao;

public class SolutionDto {
    private long boardId;
    private long menuId;
    private BoardDetailDto boardDetailDto;

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

    public BoardDetailDto getBoardDetailDto() {
        return boardDetailDto;
    }

    public void setBoardDetailDto(BoardDetailDto boardDetailDto) {
        this.boardDetailDto = boardDetailDto;
    }

    public SolutionDao toDao() {
    	SolutionDao solutionDao = new SolutionDao();
        solutionDao.setBoardId(boardId);
        solutionDao.setMenuId(menuId);
        return solutionDao;
    }
}
