package com.devworker.kms.dto.board;

import com.devworker.kms.dao.board.CommentDao;

public class CommentAndFileTransactionDto {

	CommentDao commentDao;
	
	FileTransactionDto fileTranscationDto;
	
	
	@Override
	public String toString() {
		return "CommentAndFileTransactionDto [commentDao=" + commentDao + ", fileTranscationDto=" + fileTranscationDto
				+ "]";
	}
	
	
}
