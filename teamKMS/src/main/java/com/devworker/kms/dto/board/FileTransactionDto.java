package com.devworker.kms.dto.board;

public class FileTransactionDto {

	private String fileTransactKey;
	
	private int fileCount;
	
	public FileTransactionDto() {
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
		return "FileTransactionDto [fileTransactKey=" + fileTransactKey + ", fileCount=" + fileCount + "]";
	}
	

	
	
}
