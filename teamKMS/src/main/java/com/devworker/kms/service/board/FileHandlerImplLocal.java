package com.devworker.kms.service.board;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;

@Component
@Primary
public class FileHandlerImplLocal implements FileHandler {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	DocRepo docRepo;
	
	private static final String FILE_PATH = "D:/app/";

	@Override
	public String getFilePath(String fileKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFile(File file) {
		return null;
	}

	@Override
	public File downloadFile(String fileKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocDao> processUploadFile(int boardId, List<MultipartFile> file) throws Exception {
		return null;
		
	}

	@Override
	public List<DocDao> processUploadFile(int boardId, int CommentId, List<MultipartFile> file) throws Exception {
		System.out.println("local storage is called");
		if (file.isEmpty())
			throw new RuntimeException("등록 할 파일이 없습니다.");

		if (boardId < 0)
			throw new Exception("boarId가 잘못됐습니다. boardId: " + boardId );

		Optional<UserDao> optionalUser = userRepo.findById(CommonUtil.getCurrentUser());
		UserDao user = optionalUser.get();
		List<DocDao> docList = new ArrayList<DocDao>();

		for (int i = 0; i < file.size(); i++) {

			File tmpFile = new File(FILE_PATH + file.get(i).getOriginalFilename() + UUID.randomUUID());
			file.get(i).transferTo(tmpFile);

			DocDao docDao = new DocDao();
			docDao.setBoardID(boardId);
			docDao.setDocPath(tmpFile.getCanonicalPath());
			docDao.setDocSize((int) tmpFile.length());
			docDao.setDocUserId(user.getName());
			docList.add(docDao);

			if (docRepo.save(docDao) == null) {
				throw new Exception();
			}
		}
		return docList;
	}

}
