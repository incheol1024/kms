package com.devworker.kms.dto.board;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.board.CommentDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentDto {

	private int boardId;

	private int cmtId;

	private String cmtContents;

	private String cmtUserId;

	private LocalDateTime cmtDate;

	@JsonIgnore
	private MultipartFile multiPartFile;

	@JsonProperty("file")
	private String fileName;

	private String bucketName;

	private String filePath;

	public CommentDto(CommentDao commentDao) {
		this.setBoardId(commentDao.getBoardId());
		this.setCmtContents(commentDao.getCmtContents());
		this.setCmtDate(commentDao.getCmtDate());
		this.setCmtId(commentDao.getCmtId());
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getCmtId() {
		return cmtId;
	}

	public void setCmtId(int cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtContents() {
		return cmtContents;
	}

	public void setCmtContents(String cmtContents) {
		this.cmtContents = cmtContents;
	}

	public String getCmtUserId() {
		return cmtUserId;
	}

	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}

	public LocalDateTime getCmtDate() {
		return cmtDate;
	}

	public void setCmtDate(LocalDateTime cmtDate) {
		this.cmtDate = cmtDate;
	}

	public MultipartFile getMultiPartFile() {
		return multiPartFile;
	}

	public void setMultiPartFile(MultipartFile multiPartFile) {
		this.multiPartFile = multiPartFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
