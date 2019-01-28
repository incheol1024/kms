package com.devworker.kms.exception.board;

public class FileMemoryNotContainsKeyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileMemoryNotContainsKeyException() {
		this("File Memory not contains transcact key");
	}

	public FileMemoryNotContainsKeyException(String msg) {
		this(msg, null);
	}

	public FileMemoryNotContainsKeyException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
