package com.devworker.kms.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.service.board.FileHandler;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	@Qualifier("fileHandlerImplLocal")
	FileHandler fileHandler;

	@PostMapping("/upload/comment")
	public List<DocDao> uploadCommentFile(@RequestParam(name = "boardId") int boardId,
			@RequestParam(name = "cmtId") int commentId,
			@RequestParam(name = "multiPartFile" ) List<MultipartFile> multiPartFile) throws Exception {
		return fileHandler.processUploadFile(boardId, commentId, multiPartFile);
	}

	@PostMapping("/upload/board")
	public List<DocDao> uploadBoardFile(@RequestParam(name = "boardId") int boardId,
			@RequestParam(name = "commentId") int commentId,
			@RequestParam(name = "multiPartFile") List<MultipartFile> multiPartFile) throws Exception {
		return fileHandler.processUploadFile(boardId, multiPartFile);
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestParam(name = "filekey") String filekey) throws IOException {
		File file = fileHandler.downloadFile(filekey.trim());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(new HttpHeaders()).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

}
