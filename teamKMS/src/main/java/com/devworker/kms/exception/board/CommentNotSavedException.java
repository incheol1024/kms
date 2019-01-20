package com.devworker.kms.exception.board;


/**
 *  @author Hwang In Cheol
 *  @version 1.1
 *  댓글 등록이 실패하면 CommentNotSavedException 예외를 사용합니다.
 *  
*/
public class CommentNotSavedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentNotSavedException() {
		this("Fail to insert comment into KMS_COMMENT.");
	}

	public CommentNotSavedException(String msg) {
		this(msg, null);
	}

	public CommentNotSavedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
