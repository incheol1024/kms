package com.devworker.kms.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.component.DocComponent;

/**
 * @author Hwang In Cheol
 * @version 1.0
 * 파일 처리하는 컨트롤러 입니다.
*/
@RestController
@RequestMapping("/file")
public class DocController {

	@Autowired
	DocComponent docService;
	
	@Autowired
	DocComponent docSevice;

	/**
	 * @param boardId
	 * @param cmtId
	 * @param multiPartFile
	 * @return fileTransactKey of String type
	 * @throws Exception
	 */
	@PostMapping("/upload/comment")
	public FileTransactionDto uploadCommentFile(@RequestParam(name = "boardId") BoardDao boardId,
			//@RequestParam(name = "cmtId") int cmtId,
			@RequestParam(name = "multiPartFile") List<MultipartFile> multiPartFile) throws Exception {
		return docService.addDoc(
				//boardId, 
				//cmtId, 
				multiPartFile);
	}

	/**
	 * @param boardId
	 * @param cmtId
	 * @param multiPartFile
	 * @return fileTransactKey of String type
	 * @throws Exception
	 */
/*	
	@PostMapping("/upload/common")
	public String uploadBoardFile(@RequestParam(name = "boardId") BoardDao boardId,
			@RequestParam(name = "cmtId") int cmtId,
			@RequestParam(name = "multiPartFile") List<MultipartFile> multiPartFile) throws Exception {
		return docService.addDoc(boardId, cmtId, multiPartFile);
	}
*/
	/**
	 * @param filekey is docId that KMS_DOC table primary column value 
	 * @return ResponseEntity 로 리턴하고있지만.. 다른 방식으로 리턴하는게 좋아보임 아직은 모름.
	 * @throws IOException
	 */
	@GetMapping("/download/{docId}")
	public ResponseEntity<Resource> download(@PathVariable long docId) throws IOException {
		File file = docService.downDoc(docId);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(new HttpHeaders()).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/list/{boardId}")
	public List<DocDao> listFile(@PathVariable int boardId) {
		return docSevice.listDoc(boardId);
	}

}