package com.devworker.kms.exception;

/**
 * @author Hwang In Cheol
 * @version 1.1
 * 파일 저장 실패시 FileNotSavedException 을 사용합니다.
*/
public class FileNotSavedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotSavedException() {
		this("Fail to save file in storage.");
	}
	
	public FileNotSavedException(String msg) {
		this(msg, null);
	}

	public FileNotSavedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
