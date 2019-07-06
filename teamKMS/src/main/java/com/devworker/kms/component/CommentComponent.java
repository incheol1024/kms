package com.devworker.kms.component;

import com.devworker.kms.dic.LikeType;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.exception.board.CommentNotFoundException;
import com.devworker.kms.repo.common.CommentRepo;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.FileTransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    UserService userService;

    @Autowired
    BoardComponent boardComponent;

    /**
     * Comment(댓글) 등록 시 호출 되는 메소드입니다.
     * @return
     */
    public CommentDto addComment(CommentDto commentDto) {
        return commentRepo.save(CommentDao.valueOf(commentDto)).getCommentDto();
    }

    /**
     * @return 트랜잭션이 정상 처리 되면 등록된 CommentDto 객체를 리턴합니다.
     */
    public CommentDto addCommentAndFile(CommentDto commentDto) {

        String fileTransactKey = commentDto.getFileTransactKey();
        int fileCount = commentDto.getFileCount();

        List<Long> docIds = FileTransactionUtil.findSameTransaction(fileTransactKey, fileCount);
        CommentDao commentDao = CommentDao.valueOf(commentDto);
        docIds.stream().forEach((docId) -> {
            commentDao.addDoc(new DocDao(docId));
        });

        return commentRepo.save(commentDao).getCommentDto();
    }

    /**
     * Comment(댓글) 수정 시 호출 되는 메소드입니다.
     *
     * @param
     * @return CommentDao
     */
    public CommentDto updateComment(CommentDao commentDao) {
        Optional<CommentDao> optionalCommentDao = commentRepo.findById(commentDao.getCmtId());
        CommentDao comment = optionalCommentDao.orElseThrow(CommentNotFoundException::new);
        comment.setCmtContents(commentDao.getCmtContents());
        return commentRepo.save(comment).getCommentDto();
    }

    /**
     * Comment(댓글) 삭제 시 호출되는 메소드입니다.
     *
     * @param cmtId must not be {@literal null}. Comment ID 값이 되야 합니다.
     */
    public void deleteComment(long cmtId) {
        Optional<CommentDao> optionalCommentDao = commentRepo.findById(cmtId);
        CommentDao commentDao = optionalCommentDao
                .orElseThrow(() -> new CommentNotFoundException("comment is not found cmtid = " + cmtId));

        List<DocDao> docDaos = commentDao.getDocDaos();
        if (!docDaos.isEmpty())
            docDaos.parallelStream().forEach((docDao) -> {docComponent.deleteDoc(docDao.getDocId());});

        commentRepo.delete(commentDao);
    }

    /**
     * 댓글의 좋아요 버튼 기능입니다.
     *
     * @return CommentDto
     */
    public CommentDto updateCommentLike(long cmtId) {
        Optional<CommentDao> opComment = commentRepo.findById(cmtId);
        CommentDao oldComment = opComment.orElseThrow(CommentNotFoundException::new);
        oldComment.setCmtLike(LikeType.LIKE.getLikeValue());
        return commentRepo.save(oldComment).getCommentDto();
    }

    public Page<CommentDto> findCommentsByPage(BoardDao boardId, Pageable pageable) {
        return commentRepo.findByBoardIdEquals(boardId, pageable).map(CommentDao::getCommentDto);
    }

}
