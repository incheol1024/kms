package com.devworker.kms.exception;

public class FileMemoryNotRemovedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileMemoryNotRemovedException() {
		this("File Memory is not removed ");
	}

	public FileMemoryNotRemovedException(String msg) {
		this(msg, null);

	}

	public FileMemoryNotRemovedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
