package com.devworker.kms.exception;

public class CommentNotSavedException extends RuntimeException{

	
	public CommentNotSavedException() {
		super("Fail to insert comment into KMS_COMMENT.");
	}
}
