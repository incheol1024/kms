package com.devworker.kms.component;

import org.springframework.stereotype.Service;

import com.devworker.kms.dto.common.FileDto;

@Service
public interface FileHandler {

	FileDto processUploadFile(FileDto fileDto) throws Exception;
	
	FileDto processDownloadFile(FileDto fileDto);
	
	boolean deleteFile(String key) throws Exception;

}
