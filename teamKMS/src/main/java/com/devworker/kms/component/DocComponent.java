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
import java.util.stream.Stream;

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
        String fileTransactKey = UUID.randomUUID().toString();
        Stream<FileDto> fileDtoStream = streamOfFileDto(files.stream());
        List<Long> successDocIdList = streamOfDocIds(fileDtoStream).collect(Collectors.toList());

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

        DocDao docDao = DocDao.valueOf(fileDto);
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
     * @param multiPartFile
     * @return transactKey type of String
     */
    @Transactional
    public DocDao updateDoc(long docId, MultipartFile multiPartFile) {

        File file = makeTempFile(multiPartFile);
        String key = fileHandler.processUploadFile(file);
        FileDto fileDto = makeFileDto(file, key);

        DocDao orgDoc = docRepo.getOne(docId);
        String orgKey = orgDoc.getDocPath();

        orgDoc.setUpEntity(fileDto);
        DocDao updatedDoc = docRepo.save(orgDoc);

        fileHandler.deleteFile(orgKey);

        return updatedDoc;
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

    public void deleteDoc(long docId) {
        Optional<DocDao> opDocDao = docRepo.findById(docId);
        if (!opDocDao.isPresent())
            throw new RuntimeException("Doc is not found");

        DocDao docDao = opDocDao.get();
        fileHandler.deleteFile(docDao.getDocPath());
        docRepo.delete(docDao);
    }

    private FileDto makeFileDto(File file, String key) {
        return FileDto.newInstance(
                file,
                key,
                file.length(),
                file.getName(),
                FilenameUtils.getExtension(file.getName())
        );

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


    private Stream<FileDto> streamOfFileDto(Stream<MultipartFile> multipartFileStream) {
        return multipartFileStream
                .map(multipartFile -> makeTempFile(multipartFile))
                .map((file) -> {
                    String key = fileHandler.processUploadFile(file);
                    return FileDto.newInstance(
                            file,
                            key,
                            file.length(),
                            file.getName(),
                            FilenameUtils.getExtension(file.getName()));
                });
    }

    private Stream<Long> streamOfDocIds(Stream<FileDto> fileDtoStream) {
        String userName = userService.getUser(CommonUtil.getCurrentUser()).getName();
        return fileDtoStream
                .map(fileDto -> {
                    DocDao docDao = DocDao.valueOf(fileDto);
                    docDao.setDocUserId(userName);
                    docDao.setDocPath(fileDto.getKey());
                    return docRepo.save(docDao).getDocId();
                });
    }


}
