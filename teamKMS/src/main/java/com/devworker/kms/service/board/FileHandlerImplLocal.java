package com.devworker.kms.service.board;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.board.DocDao;
import com.devworker.kms.dto.board.FileDto;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.DocRepo;

@Component
@Primary
public class FileHandlerImplLocal implements FileHandler {

	@Autowired
	UserRepo userRepo;

	@Autowired
	DocRepo docRepo;

	@Value("${fileDestPath}")
	private String FILE_PATH;

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
		//return new File(FILE_PATH + fileKey);
		return new File(fileKey);
		
	}

	@Override
	public List<DocDao> processUploadFile(int boardId, List<MultipartFile> file) throws Exception {
		return null;

	}

	@Override
	public FileDto processUploadFile(MultipartFile file) throws Exception {

		File destFile = new File(FILE_PATH + file.getOriginalFilename());
		file.transferTo(destFile);
		FileDto fileDto = new FileDto();
		fileDto.setPath(destFile.getAbsolutePath());
		fileDto.setSize((int) destFile.length());
		
		return fileDto;
	}

	@Override
	public List<DocDao> processUploadFile(BoardDao boardId, int CommentId, List<MultipartFile> file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDto processUploadFile(int boardId, int CommentId, MultipartFile file) throws Exception {
		return null;
	}

	@Override
	public void deleteFile(long docId) throws Exception {
		Optional<DocDao> opDoc = docRepo.findById(docId);
		DocDao doc = opDoc.get();
		String docPath = doc.getDocPath();
		
		File file = new File(docPath);
		if(file.exists()) {
			file.delete();
		}
	}


}
