package com.devworker.kms.dto.solution;

import com.devworker.kms.entity.solution.SolutionDao;

public class SolutionDto {
    long boardId;
    String site;
    String solution;
    int replyCount = 0;

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    public int getReplyCount() {
    	return replyCount;
    }
    
    public void setReplyCount(int replyCount) {
    	this.replyCount = replyCount;
    }
    
    public SolutionDao toDao() {
    	SolutionDao solutionDao = new SolutionDao();
    	solutionDao.setId(boardId);
    	solutionDao.setSite(site);
    	solutionDao.setSolution(solution);
    	solutionDao.setReplyCount(replyCount);
        return solutionDao;
    }
}
