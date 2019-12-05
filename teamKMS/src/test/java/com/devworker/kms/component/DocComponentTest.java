package com.devworker.kms.component;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.util.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.entity.ContentType;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.matcher.StringMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.verification.VerificationMode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.READ;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DocComponentTest {

    /**
     * Filelds used @BeforeClass for initialize only once
     */

    private static List<MultipartFile> files;

    private static Path testPath;


    /**
     * Filelds used @Before for initialize every time unit @Test Method
     */
    private DocRepo docRepo;

    private FileHandler fileHandler;

    private File testFile;

    private DocDao testDocDao;

    private FileUtil mockFileUtil;

    @BeforeClass
    public static void setUpClass() throws IOException {
        testPath = Files.walk(Paths.get(""), 1, FileVisitOption.FOLLOW_LINKS)
                .filter(path -> path.toFile().isFile())
                .findAny()
                .orElse(Paths.get("pom.xml"))
                .toAbsolutePath();

        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "multipartFile",
                        testPath.getFileName().toString(),
                        "text/plain",
                        Files.newInputStream(testPath, READ));

        files = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            files.add(mockMultipartFile);
        }
    }

    @Before
    public void setUp() throws Exception {
        fileHandler = mock(FileHandler.class);
        docRepo = mock(DocRepo.class);
        testFile = mock(File.class);
        testDocDao = mock(DocDao.class);
        mockFileUtil = mock(FileUtil.class);
    }

    @Test
    public void notNullObjectTest() {
        assertThat(fileHandler).isNotNull();
    }

    @Test
    public void addDocs() {
        BDDMockito.given(fileHandler.processUploadFile(testFile)).willReturn("return file key");
        when(fileHandler.processUploadFile(testFile)).thenReturn("return file key");
        when(docRepo.save(ArgumentMatchers.any(DocDao.class))).thenReturn(testDocDao);

        DocComponent docComponent = new DocComponent(fileHandler, docRepo, mockFileUtil);
        FileTransactionDto fileTransactionDto = docComponent.addDocs(files);

        Assertions.assertThat(fileTransactionDto)
                .isNotNull()
                .isInstanceOf(FileTransactionDto.class)
                .matches(fileTransactionDto1 -> CoreMatchers.notNullValue().matches(fileTransactionDto1.getFileTransactKey()))
                .matches(fileTransactionDto1 -> fileTransactionDto1.getFileCount() == files.size())
        ;
    }

    @Test
    public void downDoc() {
        File mockDownFile = mock(File.class);

        when(mockDownFile.getPath()).thenReturn("D:/app/test.txt");
        when(mockFileUtil.getContentType(ArgumentMatchers.any(File.class))).thenReturn("text/plain");

        DocDao mockDocDao = mock(DocDao.class);
        when(mockDocDao.getDocId()).thenReturn(1L);
        when(mockDocDao.getDocPath()).thenReturn("doc path");
        when(mockDocDao.getDocExt()).thenReturn("doc Ext");
        when(mockDocDao.getDocSize()).thenReturn(1L);
        when(mockDocDao.getDocUserId()).thenReturn("doc user");
        when(mockDocDao.getDocName()).thenReturn("doc name");

        when(fileHandler.processDownloadFile(ArgumentMatchers.any(String.class))).thenReturn(mockDownFile);
        when(docRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockDocDao));

        DocComponent docComponent = new DocComponent(fileHandler, docRepo, mockFileUtil);
        FileDto actual = docComponent.downDoc(1L);

        verify(fileHandler, times(1)).processDownloadFile(anyString());
        verify(docRepo, times(1)).findById(anyLong());

        Assertions.assertThat(actual)
                .isNotNull()
                .isInstanceOf(FileDto.class)
                .isExactlyInstanceOf(FileDto.class)
                .matches(fileDto -> fileDto.getFileName().equals(mockDocDao.getDocName()))
                .matches(fileDto -> fileDto.getFile() == mockDownFile)
                .matches(fileDto -> fileDto.getFileSize() == mockDocDao.getDocSize())
                .matches(fileDto -> fileDto.getContentType() == "text/plain");

    }


    @Test
    public void deleteDoc() {
        DocDao mockDocDao = mock(DocDao.class);
        when(mockDocDao.getDocPath()).thenReturn("doc path");

        when(docRepo.findById(anyLong())).thenReturn(Optional.of(mockDocDao));
        when(fileHandler.deleteFile(anyString())).thenReturn(true);

        DocComponent docComponent = new DocComponent(fileHandler, docRepo, mockFileUtil);
        docComponent.deleteDoc(anyLong());

        verify(docRepo, times(1)).findById(anyLong());
        verify(fileHandler, times(1)).deleteFile(anyString());

    }




}
