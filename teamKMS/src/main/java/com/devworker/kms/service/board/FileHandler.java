package com.devworker.kms.service.board;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandler {

	String getFilePath(String fileKey);

	String uploadFile(File file);

	File downloadFile(String fileKey);

	boolean processUploadFile(int boardId, List<MultipartFile> file) throws Exception;

}
