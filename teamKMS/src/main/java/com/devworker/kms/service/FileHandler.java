package com.devworker.kms.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;

@Service
public interface FileHandler {

	File downloadFile(String key);
	
	FileDto processUploadFile(MultipartFile multipartFile) throws Exception;

	boolean deleteFile(String key) throws Exception;
}
