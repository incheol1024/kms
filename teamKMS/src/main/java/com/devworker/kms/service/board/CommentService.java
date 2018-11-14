package com.devworker.kms.service.board;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dto.board.CommentDto;
import com.devworker.kms.exception.CommentNotSavedException;
import com.devworker.kms.exception.FileNotSavedException;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileUploadUtil;

/**
 * @author Hwang In Cheol
 */
@Service
public class CommentService {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocRepo docRepo;

	@Autowired
	FileHandler fileHandler;

	public boolean updateComment(CommentDao commentDao) {
		if (commentRepo.save(commentDao) != null)
			return true;

		return false;
	}

	public boolean deleteComment(CommentDao commentDao) {
		commentRepo.delete(commentDao);
		return true;
	}

	public List<CommentDao> findByBoardId(Integer id) {
		commentRepo.findById(id);
		return null;
	}

	public CommentDto addComment(CommentDto commentDto) {

		if (commentDto.getMultiPartFile() == null) {
			CommentDao commentDao = new CommentDao(commentDto);

			if (commentRepo.save(commentDao) == null)
				throw new CommentNotSavedException();

			return commentDto;
		}

		File tmpFile = new File(
				"E:/app/" + commentDto.getMultiPartFile().getOriginalFilename());
		try {
			commentDto.getMultiPartFile().transferTo(tmpFile);

			String filePath = fileHandler.uploadFile(tmpFile);
			commentDto.setFilePath(filePath);
			commentDto.setCmtUserId(CommonUtil.getCurrentUser());

			CommentDao commentDao = new CommentDao(commentDto);
			DocDao docDao = new DocDao(commentDto);

			if (commentRepo.save(commentDao) == null)
				throw new CommentNotSavedException();

			if (docRepo.save(docDao) == null)
				docRepo.delete(docDao);

		} catch (IllegalStateException | IOException e1) {
			throw new FileNotSavedException();
		}

		return commentDto;
	}

}
