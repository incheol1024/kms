package com.devworker.kms.service.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dto.board.FileDto;
import com.devworker.kms.dto.board.FileTransactionDto;
import com.devworker.kms.exception.board.FileNotSavedException;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;

/**
 * @author Incheol
 *
 */
@Service
public class DocService {

	@Autowired
	DocRepo docRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	@Qualifier(value = "fileHandlerImplLocal")
	FileHandler fileHandler;

	/**
	 * 글 또는 댓글에 이미지 첨부 시 수행되는 메소드 입니다.
	 * @param boardId
	 * @param cmtId
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public FileTransactionDto addDoc(
			//BoardDao boardId, 
			//int cmtId,
			List<MultipartFile> files) throws Exception {
		Optional<UserDao> optionalUser = userRepo.findById(CommonUtil.getCurrentUser());
		UserDao user = optionalUser.get();
		String fileTransactKey = "";
		int fileCount = 0;
		
		for (MultipartFile file : files) {
			FileDto fileDto = fileHandler.processUploadFile(file);
			DocDao docDao = new DocDao();
			//CommentDao commentDao = new CommentDao();
			//commentDao.setCmtId(cmtId);
			//docDao.setBoardId(null);
			//docDao.setCmtId(null);
			docDao.setDocPath(fileDto.getPath());
			docDao.setDocSize(fileDto.getSize());
			docDao.setDocUserId(user.getName());

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
		/*
		 * DocDao 객체를 생성하는데.. 업데이트 할 DocDao 객체만 가져온다.
		 * DocDao 객체만 가져와서.
		 * 
		*/
		String key="";
		
		for (MultipartFile file : multiPartFile) {
			FileDto fileDto = fileHandler.processUploadFile(file);
			docDao.setDocPath(fileDto.getPath());
			docDao.setDocSize(fileDto.getSize());
			docDao.setDocUserId(CommonUtil.getCurrentUser());
			
			if(docRepo.save(docDao) == null) 
				throw new FileNotFoundException("File Not Saved Database");
			
			key =  FileTransactionUtil.putFileInfo(key, docDao.getDocId());
		}
		return key;
	}

	public void deleteDoc(int docId) throws Exception {
		Optional<DocDao> opDocDao = docRepo.findById(docId);
		if (!opDocDao.isPresent())
			throw new RuntimeException("Doc is not found");  

		DocDao docDao = opDocDao.get();
		fileHandler.deleteFile(docId);
		docRepo.delete(docDao);
	}

	public File downDoc(String trim) {
		// TODO Auto-generated method stub
		return null;
	}

	public void rollbackFileTransaction(String fileTransactKey) throws Exception {
		List<Integer> fileList = FileTransactionUtil.getFileList(fileTransactKey);
		for (Integer fList : fileList) {
			deleteDoc(fList);
		}

	}

}
