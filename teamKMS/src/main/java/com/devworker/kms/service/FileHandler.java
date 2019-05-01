package com.devworker.kms.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;

@Service
public interface FileHandler {

	String getFilePath(String fileKey);

	String uploadFile(File file);

	File downloadFile(String fileKey);
	
	FileDto processUploadFile(MultipartFile file) throws Exception;

	void deleteFile(long docId) throws Exception;
}
