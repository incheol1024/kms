package com.devworker.kms.component;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.entity.common.BoardDao;
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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Incheol
 */
@Component
public class DocComponent {

    //    @Autowired
    private DocRepo docRepo;

    //    @Autowired
//    @Qualifier(value = "amazonS3")
    private FileHandler fileHandler;


    private Logger logger = LoggerFactory.getLogger(DocComponent.class);

    public DocComponent(
            @Autowired
            @Qualifier(value = "amazonS3")
                    FileHandler fileHandler,
            @Autowired
                    DocRepo docRepo) {
        this.fileHandler = fileHandler;
        this.docRepo = docRepo;
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

        List<Long> successDocIdList =
                files.stream()
                        .peek(multipartFile -> logger.info("file name={}, size={}", multipartFile.getOriginalFilename(), multipartFile.getSize()))
                        .map(this::storeFile) //파일 저장 후 FileDto 로 Mapping 함
                        .map(this::insertEntity) // FileDto 를 이용하여 DB에 저장 후 DocId로 Mapping 함
                        .collect(Collectors.toList());

        FileTransactionUtil.putFileInfo(fileTransactKey, CommonUtil.getCurrentUser(), successDocIdList);
        FileTransactionDto fileTransactionDto = new FileTransactionDto();
        fileTransactionDto.setFileCount(successDocIdList.size());
        fileTransactionDto.setFileTransactKey(fileTransactKey);
        return fileTransactionDto;
    }

    private long insertEntity(FileDto fileDto) {
        DocDao docDao = DocDao.valueOf(fileDto);
        docDao.setDocUserId(CommonUtil.getCurrentUser());
        return docRepo.save(docDao).getDocId();
    }

    private FileDto storeFile(MultipartFile multipartFile) {
        File file = this.makeTempFile(multipartFile);
        String key = fileHandler.processUploadFile(file);

        return null;
/*
        return FileDto.newInstance(
                file,
                key,
                multipartFile.getSize(),
                multipartFile.getOriginalFilename(),
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
*/
    }


    @Transactional
    public DocDao addDoc(MultipartFile multipartFile) {
        DocDao docDao = DocDao.valueOf(storeFile(multipartFile));
        docDao.setDocUserId(CommonUtil.getCurrentUser());
        return docRepo.save(docDao);
    }

    /**
     * 게시판글 또는 댓글의 이미지를 수정 시 수행되는 서비스 메소드 입니다.
     *
     * @param multiPartFile
     * @return transactKey type of String
     */
    @Transactional
    public DocDao updateDoc(long docId, MultipartFile multiPartFile) {
        DocDao orgDoc = docRepo.findById(docId).orElse(new DocDao());
        StringBuilder orgKey = new StringBuilder(orgDoc.getDocPath());
        FileDto fileDto = storeFile(multiPartFile);
        DocDao willUpdate = DocDao.valueOf(fileDto);
        willUpdate = docRepo.save(willUpdate);
        fileHandler.deleteFile(orgKey.toString());
        return willUpdate;
    }

    public FileDto downDoc(long docId) {
        Optional<DocDao> optionalDocDao = docRepo.findById(docId);
        DocDao docDao = optionalDocDao.orElseThrow(() -> new RuntimeException("docId is not Exist"));
        return null;
/*
        return FileDto.newInstance(
                fileHandler.processDownloadFile(docDao.getDocPath()),
                docDao.getDocSize(),
                docDao.getDocName(),
                docDao.getDocExt());
*/
    }

    public void deleteDoc(long docId) {
        Optional<DocDao> opDocDao = docRepo.findById(docId);
        if (!opDocDao.isPresent())
            throw new RuntimeException("Doc is not found");

        DocDao docDao = opDocDao.get();
        fileHandler.deleteFile(docDao.getDocPath());
        docRepo.delete(docDao);
    }

    public void deleteDocs(List<Long> docIds) {
        docIds.stream().forEach(this::deleteDoc);
    }

    private FileDto makeFileDto(File file, String key) {
        return FileDto.builder()
                .file(file)
                .key(key)
                .fileExt(FilenameUtils.getExtension(file.getName()))
                .fileSize(file.length())
                .fileName(file.getName())
                .build();

        /*
        return FileDto.newInstance(
                file,
                key,
                file.length(),
                file.getName(),
                FilenameUtils.getExtension(file.getName())
        );
*/

    }

    public void rollbackFileTransaction(String fileTransactKey) {
        List<Long> fileList = FileTransactionUtil.getFileList(fileTransactKey);
        fileList.parallelStream().forEach(this::deleteDoc);
        throw new FileNotSavedException("File Not Saved Database");
    }

    public File makeTempFile(MultipartFile file) {
        Path tmpPath = Paths.get(FileHandler.getUploadTemporaryDirectory(), file.getOriginalFilename());
        try {
            file.transferTo(tmpPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpPath.toFile();
    }


}
