package com.devworker.kms.service.board;

import java.io.File;
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
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dto.board.FileDto;
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

	@Transactional
	public List<DocDao> addDoc(BoardDao boardId, int cmtId, List<MultipartFile> files) throws Exception {
		Optional<UserDao> optionalUser = userRepo.findById(CommonUtil.getCurrentUser());
		UserDao user = optionalUser.get();
		List<DocDao> docList = new ArrayList<DocDao>();
		String key = "";

		for (MultipartFile file : files) {
			FileDto fileDto = fileHandler.processUploadFile(file);
			DocDao docDao = new DocDao();
			docDao.setBoardId(boardId);
			docDao.setCmtId(cmtId);
			docDao.setDocPath(fileDto.getPath());
			docDao.setDocSize(fileDto.getSize());
			docDao.setDocUserId(user.getName());

			if (docRepo.save(docDao) == null)
				throw new FileNotSavedException("File Not Saved Database");

			key = FileTransactionUtil.putFileInfo(key, docDao.getDocId());
			// 값에 DocId 대신에 DocDao 넣는것을 고려해보자.

			// 파일을 하나하나 저장하는데.. 댓글 저장 트랜잭션을 처리하기 위해서...
			// 저장이 완료된 파일들은 메모리에 올려두고.. 하나씩 메모리에 올려두고..
			// 나중에 댓글이 등록이 되면.. 댓글에 해당하는 파일들만 등록 처리해야함
			// 조건1. 메모리에 올라간 파일 정보와 댓글정보가 같은 키값을 갖고 있어야 한다.
			docList.add(docDao);
		}
		return docList;
	}

	public List<DocDao> listDoc(int boardId) {
		return docRepo.findByBoardId(boardId);
	}

	public DocDao updateDoc(int boardId, int cmtId, List<MultipartFile> multiPartFile) {
		return null;
	}

	public void deleteDoc(int docId) throws Exception {
		Optional<DocDao> opDocDao = docRepo.findById(docId);
		if (!opDocDao.isPresent())
			throw new RuntimeException("Doc is not found"); // 지정 예외 구현 해야함.

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
