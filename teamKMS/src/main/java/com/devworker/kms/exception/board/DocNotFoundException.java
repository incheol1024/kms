package com.devworker.kms.exception.board;

public class DocNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocNotFoundException() {
		this("Doc is not found in database.");
	}

	public DocNotFoundException(String msg) {
		this(msg, null);
	}
	
	public DocNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
