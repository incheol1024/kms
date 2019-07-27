package com.devworker.kms.controller.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.devworker.kms.dto.common.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    DocComponent docComponent;


    private Logger logger = LoggerFactory.getLogger(DocController.class);

    /**
     * @param multiPartFiles
     * @return fileTransactKey of String type
     * @throws Exception
     */
    @PostMapping("/upload/comment")
    public FileTransactionDto uploadFileOnComment(
            @RequestParam(name = "multiPartFile") List<MultipartFile> multiPartFiles) {
        multiPartFiles.stream().forEach((multipartFile) ->logger.info(multipartFile.getOriginalFilename()));
        return docComponent.addDocs(multiPartFiles);
    }

    @PostMapping("/upload/etc/{id}")
    public FileTransactionDto uploadFileOnEtc(
            @PathVariable long id,
            @RequestParam(name = "multiPartFile") List<MultipartFile> multipartFiles) throws RuntimeException {

        return null;
//        return docComponent.addDoc();
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
		return docComponent.addDoc(boardId, cmtId, multiPartFile);
	}
*/

    /**
     * @return ResponseEntity 로 리턴하고있지만.. 다른 방식으로 리턴하는게 좋아보임 아직은 모름.
     * @throws IOException
     */
    @GetMapping("/download/{docId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long docId) throws IOException {
        FileDto fileDto = docComponent.downDoc(docId);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileDto.getFile()));
        return ResponseEntity.ok().headers(new HttpHeaders()).contentLength(fileDto.getFileSize())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    @GetMapping("/list/{boardId}")
    public List<DocDao> listFile(@PathVariable int boardId) {
        return docComponent.listDoc(boardId);
    }

    @PutMapping("/update/{docId}")
    public DocDao updateFile(@PathVariable long docId, @RequestParam(name = "multiPartFile") MultipartFile multipartFile) {
        return docComponent.updateDoc(docId, multipartFile);
    }

}
