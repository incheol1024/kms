package com.devworker.kms.exception.board;

public class FileTransactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileTransactionException() {
		this("File Transaction Exception");
	}

	public FileTransactionException(String msg) {
		this(msg, null);
	}

	public FileTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
