package com.devworker.kms.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.exception.board.FileNotSavedException;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;

/**
 * @author Incheol
 *
 */
@Component
public class DocComponent {

	@Autowired
	private DocRepo docRepo;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier(value = "amazonS3")
	private FileHandler fileHandler;

	@Value("${file.upload.tmp}")
	private String tmpUpload;
	
	@Value("${file.download.tmp}")
	private String tmpDownload;
	
	public DocComponent(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	/**
	 * 글 또는 댓글에 이미지 첨부 시 수행되는 메소드 입니다.
	 * 
	 * @param boardId
	 * @param cmtId
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public FileTransactionDto addDoc(
		List<MultipartFile> files) throws Exception {
		String userName = userService.getUser(CommonUtil.getCurrentUser()).getName();
		String fileTransactKey = "";
		int fileCount = 0;

		for (MultipartFile file : files) {
			File tmpFile = new File(tmpUpload + File.separator + file.getName());
		//	File tmpFile = new File("D:" + File.separator + "app" + File.separator + file.getName());
			file.transferTo(tmpFile);
			FileDto fileDto = FileDto.builder()
					.setFile(tmpFile)
					.setFileExt(FilenameUtils.getExtension(file.getName()))
					.setFileName(file.getName())
					.setFileSize(file.getSize())
					.build();

			DocDao docDao = new DocDao();
			/*
			 * docDao.setDocPath(fileDto.getKey());
			 * docDao.setDocSize(fileDto.getFileSize());
			 * docDao.setDocName(fileDto.getFileName());
			 * docDao.setDocSize(fileDto.getFileSize());
			 * docDao.setDocExt(fileDto.getFileExt()); docDao.setDocUserId(userName);
			 */			 
			docDao.setUpEntity(fileDto);
			docDao.setDocUserId(userName);
			fileDto = fileHandler.processUploadFile(fileDto);
			docDao.setDocPath(fileDto.getKey());
			if (docRepo.save(docDao) == null)
				throw new FileNotSavedException("File Not Saved Database");

			fileTransactKey = FileTransactionUtil.putFileInfo(fileTransactKey, docDao.getDocId());
			fileCount += 1;
		}

		FileTransactionDto fileTransactionDto = new FileTransactionDto();
		fileTransactionDto.setFileTransactKey(fileTransactKey);
		fileTransactionDto.setFileCount(fileCount);
		return fileTransactionDto;
	}
	

	/**
	 * @param boardId
	 * @return
	 */
	public List<DocDao> listDoc(int boardId) {
		BoardDao boardDao = new BoardDao();
		boardDao.setBoardId(boardId);
		return docRepo.findByBoardId(boardDao);
	}

	/**
	 * 게시판글 또는 댓글의 이미지를 수정 시 수행되는 서비스 메소드 입니다.
	 * 
	 * @param boardId
	 * @param cmtId
	 * @param multiPartFile
	 * @return transactKey type of String
	 * @throws Exception
	 */
	@Transactional
	public String updateDoc(int boardId, int cmtId, List<MultipartFile> multiPartFile) throws Exception {
		BoardDao boardDao = new BoardDao();
		boardDao.setBoardId(boardId);
		CommentDao commentDao = new CommentDao();
		commentDao.setCmtId(cmtId);
		DocDao docDao = new DocDao();
		docDao.setBoardId(boardDao);
		docDao.setCmtId(commentDao);

		String key = "";

		for (MultipartFile file : multiPartFile) {
			File tmpFile = new File(tmpUpload + File.separator + file.getName());
			file.transferTo(tmpFile);
			
			FileDto fileDto = FileDto.builder()
					.setFile(tmpFile)
					.setFileExt(FilenameUtils.getExtension(tmpFile.getName()))
					.setFileName(tmpFile.getName())
					.setFileSize(tmpFile.length())
					.build();
			
			
			fileDto = fileHandler.processUploadFile(fileDto);
			docDao.setDocPath(fileDto.getKey());
			docDao.setDocSize(fileDto.getFileSize());
			docDao.setDocUserId(CommonUtil.getCurrentUser());

			if (docRepo.save(docDao) == null)
				throw new FileNotFoundException("File Not Saved Database");

			key = FileTransactionUtil.putFileInfo(key, docDao.getDocId());
		}
		return key;
	}

	public void deleteDoc(long docId) throws Exception {
		Optional<DocDao> opDocDao = docRepo.findById(docId);
		if (!opDocDao.isPresent())
			throw new RuntimeException("Doc is not found");

		DocDao docDao = opDocDao.get();
		fileHandler.deleteFile(docDao.getDocPath());
		docRepo.delete(docDao);
	}

	public FileDto downDoc(long docId) {
		Optional<DocDao> optionalDocDao = docRepo.findById(docId);
		DocDao docDao = optionalDocDao.orElseThrow(() -> new RuntimeException("docId is not Exist"));
		FileDto fileDto = fileHandler.processDownloadFile(
				FileDto.builder()
				.setKey(docDao.getDocPath())
				.setFile(new File(tmpDownload + File.separator + docDao.getDocPath()))
				.build()); 
		
		return FileDto.builder()
					.setFile(fileDto.getFile())
					.setFileExt(docDao.getDocExt())
					.setFileName(docDao.getDocName())
					.setFileSize(docDao.getDocSize())
					.build();
	}

	public void rollbackFileTransaction(String fileTransactKey) throws Exception {
		List<Long> fileList = FileTransactionUtil.getFileList(fileTransactKey);
		for (Long fList : fileList) {
			deleteDoc(fList);
		}

	}

}
