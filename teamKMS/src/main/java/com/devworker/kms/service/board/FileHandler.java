package com.devworker.kms.service.board;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.board.DocDao;

public interface FileHandler {

	String getFilePath(String fileKey);

	String uploadFile(File file);

	File downloadFile(String fileKey);

	List<DocDao> processUploadFile(int boardId, List<MultipartFile> file) throws Exception;

	List<DocDao> processUploadFile(int boardId, int CommentId, List<MultipartFile> file) throws Exception;

}
