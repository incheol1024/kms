package com.devworker.kms.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.dto.common.FileDto;

@Service
public interface FileHandler {

	String getFilePath(String fileKey);

	String uploadFile(File file);

	File downloadFile(String fileKey);
	
	List<DocDao> processUploadFile(int boardId, List<MultipartFile> file) throws Exception;

	List<DocDao> processUploadFile(BoardDao boardId, int CommentId, List<MultipartFile> file) throws Exception;

	FileDto processUploadFile(int boardId, int CommentId, MultipartFile file) throws Exception;

	FileDto processUploadFile(MultipartFile file) throws Exception;

	void deleteFile(long docId) throws Exception;
}