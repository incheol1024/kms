package com.devworker.kms.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devworker.kms.service.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.UserDao;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.dic.LikeType;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.exception.board.CommentNotFoundException;
import com.devworker.kms.exception.board.DocNotFoundException;
import com.devworker.kms.exception.board.FileTransactionException;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.common.CommentRepo;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;

/**
 * Comment Service 클래스 입니다. Comment와 관련 된 CRUD 메소드가 구현되어 있습니다.
 * 
 * @author Hwang In Cheol
 */

@Service
public class CommentComponent {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocComponent docService;

	@Autowired
	DocRepo docRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	@Qualifier("fileHandlerImplLocal")
	FileHandler fileHandler;

	/**
	 * Comment(댓글) 등록 시 호출 되는 메소드입니다.
	 * 
	 * @param commentDao
	 * @return
	 * @throws Exception
	 */
	public CommentDao addComment(CommentDao commentDao) throws Exception {
		String userId = CommonUtil.getCurrentUser();
		if (userId == null)
			throw new NotExistException("userId");

		Optional<UserDao> optionalUserDao = userRepo.findById(userId);
		commentDao.setCmtUserId(optionalUserDao.get().getName());
		return commentRepo.save(commentDao);
	}

	/**
	 * @param commentDao      commentDao 객체입니다.
	 * @param fileTransactKey 파일 트랜잭션 처리를 위한 키 값입니다.
	 * @param fileCount       트랜잭션 처리를 위해 파일 갯수를 비교하기 위한 값입니다.
	 * @return 트랜잭션이 정상 처리 되면 등록된 CommentDao 객체를 리턴합니다.
	 * @throws Exception 여러가지 예외를 던질 수 있습니다. 호출하는 컨트롤러에서 예외를 구분하여 처리해야 합니다.
	 */
	public CommentDto addComment(long boardId, String cmtContents, String fileTransactKey, int fileCount)
			throws Exception {
		String userId = CommonUtil.getCurrentUser();
		Optional<UserDao> optionalUserDao = userRepo.findById(userId);

		CommentDao commentDao = new CommentDao(new BoardDao(boardId), cmtContents);
		commentDao.setCmtUserId(optionalUserDao.get().getName());
		CommentDao savedComment = commentRepo.save(commentDao);

		if (!FileTransactionUtil.isSameTransaction(fileTransactKey, fileCount)) {

			/*
			 * 파일 rollback 과정(파일에 대한 DB정보와 물리적파일 삭제)에서 예외가 발생할 수 있음. 어떻게 처리할 것인지 생각해봐야함.
			 */
			docService.rollbackFileTransaction(fileTransactKey);

			/*
			 * FileTransactionException 예외 발생 checked Exception 이며, 최종적으로 컨트롤러에서 해당 예외를 처리
			 * 해야하는데
			 */
			throw new FileTransactionException();
		}

		/*
		 * KMS_DOC 테이블에 해당 이미지 정보 commentId 업데이트 필요 수행하려면 1. docId 를 메모리에서 가져온다. 2.
		 * docId 를 가지고 업데이트한다. 3. 업데이트 확인 되면 메모리 해제 후 정상 리턴한다. 4. 업데이트 실패하면 파일트랜잭션 롤백
		 * 메소드 호출한다.
		 */

		List<Long> fileList = FileTransactionUtil.getFileList(fileTransactKey);

		DocDao savedDoc = null;
		for (Long docId : fileList) {
			Optional<DocDao> docDao = docRepo.findById(docId.longValue());
			savedDoc = docDao.orElseThrow(() -> new DocNotFoundException());
			savedDoc.setCmtId(savedComment);
			DocDao tmpDoc = null;
			if ((tmpDoc = docRepo.save(savedDoc)) == null)
				docService.rollbackFileTransaction(fileTransactKey);
		}

		FileTransactionUtil.removeFileInfoMemory(fileTransactKey);
		// 동일 트랜잭션에서 파일이 처리되었는지 확인되면 해당 키에 매핑되는 메모리 해제함.

		// 조건2. 파일 등록처리가 완료 되면 메모리를 해제해야 한다.
		// 조건3. 파일 등록 처리를 하다가 예외가 발생하면 메모리를 해제해야한다.

		return new CommentDto(savedComment, savedDoc);
		// return savedCommentDao;
	}

	public List<CommentDto> findByBoardId(BoardDao boardId) throws Exception {
		List<CommentDao> comments = commentRepo.findByBoardId(boardId);
		List<CommentDto> resultComments = new ArrayList<CommentDto>();
		CommentDto commentDto = null;
		List<DocDao> docs = null;
		
		for (CommentDao comment : comments) {
			docs = docRepo.findByCmtId(comment);
			if (docs.isEmpty()) {
				commentDto = new CommentDto(comment);
				resultComments.add(commentDto);
				continue;
			}
			// 여러개의 파일들을 처리할 수 있도록 수정 필요.
			DocDao doc = docs.get(0);
			commentDto = new CommentDto(comment, doc);
			resultComments.add(commentDto);
		}

		return resultComments;
	}

	/**
	 * Comment(댓글) 수정 시 호출 되는 메소드입니다.
	 * 
	 * @param
	 * @return CommentDao
	 */
	public CommentDao updateComment(CommentDao commentDao) throws Exception {

		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());
		CommentDao newComment = opComment.get();
		newComment.setCmtContents(commentDao.getCmtContents());

		CommentDao newCommentDao = commentRepo.save(newComment);

		if (newCommentDao == null)
			throw new RuntimeException();

		return newCommentDao;
	}

	/**
	 * Comment(댓글) 삭제 시 호출되는 메소드입니다.
	 * 
	 * @param cmtId must not be {@literal null}. Comment ID 값이 되야 합니다.
	 * @throws Exception
	 * 
	 */
	public void deleteComment(Integer cmtId) throws Exception {
		Optional<CommentDao> opComment = commentRepo.findById(cmtId);
		CommentDao commentDao = opComment
				.orElseThrow(() -> new CommentNotFoundException("comment is not found cmtid = " + cmtId));
		List<DocDao> docs = docRepo.findByCmtId(commentDao);

		for (DocDao doc : docs) {
			docService.deleteDoc(doc.getDocId());
		}
		commentRepo.deleteById(cmtId);
	}

	/**
	 * 댓글의 좋아요 버튼 기능입니다.
	 * 
	 * @param commentDao must not be {@literal null}.
	 * @return CommentDao
	 */
	public CommentDao updateCommentLike(int cmtId) {

		Optional<CommentDao> opComment = commentRepo.findById(cmtId);

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();
		oldComment.setCmtLike(LikeType.LIKE.getLikeValue());

		return commentRepo.save(oldComment);
	}

	/**
	 * 댓글의 싫어요 버튼 기능입니다.
	 * 
	 * @param commentDao must not be {@literal null}.
	 * @return CommentDao
	 */
	public CommentDao updateCommentUnLike(CommentDao commentDao) {
		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();
		oldComment.setCmtUnlike(LikeType.UNLIKE.getLikeValue());

		return commentRepo.save(oldComment);

	}

}
