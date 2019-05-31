package com.devworker.kms.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.devworker.kms.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
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
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.common.CommentRepo;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;

/**
 * Comment Service 클래스 입니다. Comment와 관련 된 CRUD 메소드가 구현되어 있습니다.
 * 
 * @author Hwang In Cheol
 */

@Component
public class CommentComponent {

	Logger logger = LoggerFactory.getLogger(CommentComponent.class);
	
	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocComponent docComponent;

	@Autowired
	DocRepo docRepo;

	@Autowired
	UserService userService;
	
	@Autowired
	BoardComponent boardComponent;
	

	@Autowired
	@Qualifier("fileHandlerImplLocal")
	FileHandler fileHandler;

	/**
	 * Comment(댓글) 등록 시 호출 되는 메소드입니다.
	 *
	 * @return
	 * @throws Exception
	 */
	public CommentDto addComment(CommentDto commentDto) throws Exception {
		
		CommentDao comment = new CommentDao();
		comment.setUpEntity(commentDto);
		comment.setCmtUserId(getUserName());
		logger.debug("{}", comment);
		return new CommentDto(commentRepo.save(comment));
	}

	/**
	 * @return 트랜잭션이 정상 처리 되면 등록된 CommentDao 객체를 리턴합니다.
	 * @throws Exception 여러가지 예외를 던질 수 있습니다. 호출하는 컨트롤러에서 예외를 구분하여 처리해야 합니다.
	 */
	
	public CommentDto addCommentAndFile(CommentDto commentDto)
			throws Exception {
		
		long boardId = commentDto.getBoardId();
		String cmtContents = commentDto.getCmtContents();
		String fileTransactKey = commentDto.getFileTransactKey();
		int fileCount = commentDto.getFileCount();

		CommentDao comment = new CommentDao(new BoardDao(boardId), cmtContents);
		comment.setCmtUserId(getUserName());
		comment.setUpEntity(commentDto);
		CommentDao savedComment = commentRepo.save(comment);

		if (!FileTransactionUtil.isSameTransaction(fileTransactKey, fileCount)) {

			/*
			 * 파일 rollback 과정(파일에 대한 DB정보와 물리적파일 삭제)에서 예외가 발생할 수 있음. 어떻게 처리할 것인지 생각해봐야함.
			 */
			docComponent.rollbackFileTransaction(fileTransactKey);

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
				docComponent.rollbackFileTransaction(fileTransactKey);
		}

		FileTransactionUtil.removeFileInfoMemory(fileTransactKey);
		// 동일 트랜잭션에서 파일이 처리되었는지 확인되면 해당 키에 매핑되는 메모리 해제함.

		// 조건2. 파일 등록처리가 완료 되면 메모리를 해제해야 한다.
		// 조건3. 파일 등록 처리를 하다가 예외가 발생하면 메모리를 해제해야한다.

		return new CommentDto(savedComment);
		// return savedCommentDao;
	}

	public List<CommentDto> findByBoardId(BoardDao boardId) throws Exception {
		Pageable cmtPage = PageRequest.of(0, 3);
		Page<CommentDao> pageComment = commentRepo.findAll(cmtPage);

		List<CommentDao> comments = pageComment.getContent();
		List<CommentDto> resultComments = new ArrayList<CommentDto>();
		CommentDto commentDto = null;
		
		for (CommentDao comment : comments) {
			commentDto = new CommentDto(comment);
			resultComments.add(commentDto);
		}
		return resultComments;
	}


	public Page<CommentDto> findPageByBoardId(BoardDao boardId, Pageable pageable) {

		Page<CommentDao> commentDaoPage = commentRepo.findAll(pageable);
		List<CommentDao> commentDaos = commentDaoPage.getContent();
		List<CommentDto> commentDtos = new ArrayList<CommentDto>();

		for(CommentDao commentDao : commentDaos) {
			CommentDto commentDto = new CommentDto(commentDao);
			commentDtos.add(commentDto);
		}

		commentDaoPage.map( (CommentDao commentDao) -> {
			return new CommentDto(commentDao);
		});
			return null;
	}

	/**
	 * Comment(댓글) 수정 시 호출 되는 메소드입니다.
	 * 
	 * @param
	 * @return CommentDao
	 */
	public CommentDto updateComment(CommentDao commentDao) throws Exception {
		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());
		CommentDao Comment = opComment.get();
		Comment.setCmtContents(commentDao.getCmtContents());
		CommentDao newComment = commentRepo.save(Comment);
		return new CommentDto(newComment);
	}

	/**
	 * Comment(댓글) 삭제 시 호출되는 메소드입니다.
	 * 
	 * @param cmtId must not be {@literal null}. Comment ID 값이 되야 합니다.
	 * @throws Exception
	 * 
	 */
	public void deleteComment(long cmtId) throws Exception {
		Optional<CommentDao> opComment = commentRepo.findById(cmtId);
		CommentDao commentDao = opComment
				.orElseThrow(() -> new CommentNotFoundException("comment is not found cmtid = " + cmtId));
		List<DocDao> docs = docRepo.findByCmtId(commentDao);

		for (DocDao doc : docs) {
			docComponent.deleteDoc(doc.getDocId());
		}
		commentRepo.deleteById(cmtId);
	}

	/**
	 * 댓글의 좋아요 버튼 기능입니다.
	 * 
	 * @return CommentDao
	 */
	public CommentDao updateCommentLike(long cmtId) {

		Optional<CommentDao> opComment = commentRepo.findById(cmtId);

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();
		oldComment.setCmtLike(LikeType.LIKE.getLikeValue());

		return commentRepo.save(oldComment);
	}
	
	private String getUserName() {
		return userService.getUser(CommonUtil.getCurrentUser()).getName();
	}

	public Page<CommentDto> findCommentsByPage(BoardDao boardId, Pageable pageable) {
		Page<CommentDao> commentDaoPage = commentRepo.findAll(pageable);
		return commentDaoPage.map(commentDao -> new CommentDto(commentDao));
		//return null;
	}


	/**
	 * 댓글의 싫어요 버튼 기능입니다.
	 * 
	 * @param commentDao must not be {@literal null}.
	 * @return CommentDao
	 */
/*	
	public CommentDao updateCommentUnLike(CommentDao commentDao) {
		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();

		return commentRepo.save(oldComment);

	}

*/
	}
