package com.devworker.kms.component;

import org.springframework.stereotype.Service;

import com.devworker.kms.dto.common.FileDto;

import java.io.File;
import java.io.IOException;

@Service
public interface FileHandler {

	String tempDefaultDir = System.getProperty("java.io.tmpdir");

	FileDto processUploadFile(FileDto fileDto);
	
	FileDto processDownloadFile(FileDto fileDto);
	
	boolean deleteFile(String key);

	static File checkUploadDir() throws IOException {

		String tempUploadDir = tempDefaultDir + File.separator + "upload";
		File tempFile = new File(tempUploadDir);
		if(!tempFile.exists())
			new File(tempUploadDir).mkdir();

		return tempFile;
	}

	static File checkDownloadDir() throws IOException {

		String tempUploadDir = tempDefaultDir + File.separator + "upload";
		File tempFile = new File(tempUploadDir);
		if(!tempFile.exists())
			new File(tempUploadDir).mkdir();

		return tempFile;
	}
}
