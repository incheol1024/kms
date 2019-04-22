package com.devworker.kms.dto.solution;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;

public class SolutionDto {
    long board_id;
    String site;
    String solution;
    int reply_count = 0;

    public long getBoardId() {
        return board_id;
    }

    public void setBoardId(long board_id) {
        this.board_id = board_id;
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
    	return reply_count;
    }
    
    public void setReplyCount(int reply_count) {
    	this.reply_count = reply_count;
    }
    
    public SolutionDao toDao() {
    	SolutionDao dao = new SolutionDao();
        dao.setId(board_id);
        dao.setSite(site);
        dao.setSolution(solution);
        dao.setReplyCount(reply_count);
        return dao;
    }
}
