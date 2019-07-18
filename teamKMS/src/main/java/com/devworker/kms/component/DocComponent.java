package com.devworker.kms.component;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.exception.board.FileNotSavedException;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Incheol
 */
@Component
public class DocComponent {

    @Autowired
    private DocRepo docRepo;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "amazonS3")
    private FileHandler fileHandler;

    private Logger logger = LoggerFactory.getLogger(DocComponent.class);

    public DocComponent(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * 글 또는 댓글에 이미지 첨부 시 수행되는 메소드 입니다.
     *
     * @param files
     * @return
     * @throws Exception
     */
    @Transactional
    public FileTransactionDto addDocs(List<MultipartFile> files) {

        String userName = userService.getUser(CommonUtil.getCurrentUser()).getName();
        String fileTransactKey = UUID.randomUUID().toString();

        List<Long> successDocIdList =
                files.stream()
                        .map(multipartFile -> makeTempFile(multipartFile))
                        .map((file) -> {
                                String key = fileHandler.processUploadFile(file);
                                return FileDto.newInstance(
                                        file,
                                        key,
                                        file.length(),
                                        file.getName(),
                                        FilenameUtils.getExtension(file.getName()));
                        })
                        .peek(fileDto -> logger.info("After S3 uploaded fileDto {}", fileDto.toString()))
                        .map(fileDto -> {
                            DocDao docDao = new DocDao();
                            docDao.setUpEntity(fileDto);
                            docDao.setDocUserId(userName);
                            docDao.setDocPath(fileDto.getKey());
                            return docRepo.save(docDao).getDocId();
                        })
                        .collect(Collectors.toList());

        FileTransactionUtil.putFileInfo(fileTransactKey, successDocIdList);
        FileTransactionDto fileTransactionDto = new FileTransactionDto();
        fileTransactionDto.setFileCount(successDocIdList.size());
        fileTransactionDto.setFileTransactKey(fileTransactKey);
        return fileTransactionDto;
    }

    @Transactional
    public DocDao addDoc(MultipartFile multipartFile) {

        FileDto fileDto = FileDto.newInstance(makeTempFile(multipartFile),
                multipartFile.getSize(),
                multipartFile.getOriginalFilename(),
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()));

        fileDto.setKey(fileHandler.processUploadFile(fileDto.getFile()));

        DocDao docDao = new DocDao();
        docDao.setUpEntity(fileDto);
        docDao.setDocUserId(userService.getUser(CommonUtil.getCurrentUser()).getName());
        return docRepo.save(docDao);
    }

    /**
     * @param boardId
     * @return
     */
    public List<DocDao> listDoc(int boardId) {
        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(boardId);
//        return docRepo.findByBoardId(boardDao);
        return null;
    }

    /**
     * 게시판글 또는 댓글의 이미지를 수정 시 수행되는 서비스 메소드 입니다.
     *
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
//        docDao.setBoardId(boardDao);
//        docDao.setCmtId(commentDao);

        String fileTransactKey = "";

        for (MultipartFile file : multiPartFile) {
            File tmpFile = new File(FileHandler.getUploadTemporaryDirectory() + File.separator + file.getName());
            file.transferTo(tmpFile);

            FileDto fileDto = FileDto.newInstance(makeTempFile(file),
                    file.getSize(),
                    file.getOriginalFilename(),
                    FilenameUtils.getExtension(file.getOriginalFilename()));

            fileDto.setKey(fileHandler.processUploadFile(fileDto.getFile()));
            docDao.setUpEntity(fileDto);
            docDao.setDocUserId(CommonUtil.getCurrentUser());

            if (docRepo.save(docDao) == null)
                rollbackFileTransaction(fileTransactKey);

            fileTransactKey = FileTransactionUtil.putFileInfo(fileTransactKey, docDao.getDocId());
        }
        return fileTransactKey;
    }

    public void deleteDoc(long docId) {
        Optional<DocDao> opDocDao = docRepo.findById(docId);
        if (!opDocDao.isPresent())
            throw new RuntimeException("Doc is not found");

        DocDao docDao = opDocDao.get();
        fileHandler.deleteFile(docDao.getDocPath());
        docRepo.delete(docDao);
    }

    public FileDto downDoc(long docId) {
        Optional<DocDao> optionalDocDao = docRepo.findById(docId);
        DocDao docDao = optionalDocDao.orElseThrow(() -> new RuntimeException("docId is not Exist"));

        return FileDto.newInstance(
                fileHandler.processDownloadFile(docDao.getDocPath()),
                docDao.getDocSize(),
                docDao.getDocName(),
                docDao.getDocExt());
    }

    public void rollbackFileTransaction(String fileTransactKey) {
        List<Long> fileList = FileTransactionUtil.getFileList(fileTransactKey);
        fileList.parallelStream().forEach(this::deleteDoc);
        throw new FileNotSavedException("File Not Saved Database");
    }

    public File makeTempFile(MultipartFile file) {
        File tmpFile = new File(FileHandler.getUploadTemporaryDirectory() + File.separator + file.getOriginalFilename());
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFile;
    }

    public Map<Long, String> getDocEntry(CommentDao commentDao) {
        Map<Long, String> docEntry = new HashMap<>();
//        docRepo.findByCmtId(commentDao)
//                .stream()
//                .forEach(docDao -> docEntry.put(docDao.getDocId(), docDao.getDocName()));
        return docEntry;
    }

    public List<DocDao> findByCmtId(CommentDao commentDao) {
//        return docRepo.findByCmtId(commentDao);
        return null;
    }


}
