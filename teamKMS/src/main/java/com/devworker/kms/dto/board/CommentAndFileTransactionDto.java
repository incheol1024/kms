package com.devworker.kms.dto.board;

public class CommentAndFileTransactionDto {
	/*
	 * CommentDao commentDao;
	 * 
	 * FileTransactionDto fileTranscationDto;
	 * 
	 * 
	 * @Override public String toString() { return
	 * "CommentAndFileTransactionDto [commentDao=" + commentDao +
	 * ", fileTranscationDto=" + fileTranscationDto + "]"; }
	 * 
	 */

	long boardId;
	String cmtContents;
	String fileTransactKey;
	int fileCount;

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getCmtContents() {
		return cmtContents;
	}

	public void setCmtContents(String cmtContents) {
		this.cmtContents = cmtContents;
	}

	public String getFileTransactKey() {
		return fileTransactKey;
	}

	public void setFileTransactKey(String fileTransactKey) {
		this.fileTransactKey = fileTransactKey;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	@Override
	public String toString() {
		return "CommentAndFileTransactionDto [boardId=" + boardId + ", cmtContents=" + cmtContents
				+ ", fileTransactKey=" + fileTransactKey + ", fileCount=" + fileCount + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
