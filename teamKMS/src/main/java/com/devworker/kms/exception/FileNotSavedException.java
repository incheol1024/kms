package com.devworker.kms.exception;

public class FileNotSavedException extends RuntimeException{

	public FileNotSavedException() {
		super("Fail to save file in storage.");
	}
}
