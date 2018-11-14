package com.devworker.kms.service.board;

import java.io.File;

public interface FileHandler {

	public abstract String getFilePath(String fileKey);
	
	public abstract String uploadFile(File file);
	
	public abstract File downloadFile(String fileKey);
	
	
}
