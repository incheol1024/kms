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
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dto.board.FileDto;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;

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
	public List<DocDao> addDoc(BoardDao boardObj, CommentDao cmtObj, List<MultipartFile> files) throws Exception {
		Optional<UserDao> optionalUser = userRepo.findById(CommonUtil.getCurrentUser());
		UserDao user = optionalUser.get();
		List<DocDao> docList = new ArrayList<DocDao>();

		for (int i = 0; i < files.size(); i++) {
			FileDto fileDto = fileHandler.processUploadFile(files.get(i));
			DocDao docDao = new DocDao();
			docDao.setBoardId(boardObj);
			// docDao.setCmtId(cmtId); // 데이터베이스에 컬럼 추가 필요 DocDao에도 추가 필요
			docDao.setDocPath(fileDto.getPath());
			docDao.setDocSize(fileDto.getSize());
			docDao.setDocUserId(user.getName());

			if (docRepo.save(docDao) == null) 
				throw new RuntimeException();
			
			docList.add(docDao);
		}
		return docList;
	}

	public List<DocDao> listDoc(BoardDao boardObj) {
		return docRepo.findByBoardId(boardObj);
	}

	public DocDao updateDoc(BoardDao boardObj, CommentDao cmtObj, List<MultipartFile> multiPartFile) {

		return null;
	}

	public void deleteDoc(int docId) {

	}

	public File downDoc(String trim) {
		// TODO Auto-generated method stub
		return null;
	}

}
