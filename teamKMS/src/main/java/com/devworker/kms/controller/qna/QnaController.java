package com.devworker.kms.controller.qna;

import java.util.List;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.service.qna.QnaService;

@RestController
@RequestMapping("/qna")
public class QnaController {

    private Logger logger = LoggerFactory.getLogger(QnaController.class);

    @Autowired
    QnaService qnaService;

    @GetMapping("/{menuId}")
    public Page<BoardDto> findQnaPage(@PathVariable int menuId, Pageable pageable) {

        logger.debug("menuId = {}", menuId);
        logger.debug("Pageable = {}", pageable);
        return qnaService.getQnaPage(menuId, pageable);
        //return qnaService.getFirstPageList(menuId);
    }

    @PostMapping("/register/{menuId}")
    public BoardDetailDto createQuestion(@RequestBody BoardDao boardDao, @PathVariable int menuId) {
        logger.debug("BoardDao message Converting {} ", boardDao);
        logger.debug("PathVariable data binding {} ", menuId);
        return qnaService.createQuestion(boardDao, menuId);
    }

    @GetMapping("/answer/{id}")
    public BoardDetailDto getQnaById(@PathVariable long id) {
        return qnaService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable long id) {
        logger.debug("{}", id);
        qnaService.deleteQuestion(id);
    }

    @PutMapping("/update/{id}")
    public BoardDto updateQnaById(@PathVariable long id) {
        //서비스 레이어와 함께 구현 필요
        return null;
    }


}
