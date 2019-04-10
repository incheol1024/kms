package com.devworker.kms.exception.board;

public class CommentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentNotFoundException() {
		this("Fail to insert comment into KMS_COMMENT.");
	}

	public CommentNotFoundException(String msg) {
		this(msg, null);
	}

	public CommentNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
