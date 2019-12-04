package com.devworker.kms.component;

import com.devworker.kms.dto.common.FileTransactionDto;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.repo.common.DocRepo;
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

import static java.nio.file.StandardOpenOption.READ;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

public class DocComponentTest {

    /**
     * Filelds used @BeforeClass for initialize only once
     */

    private static List<MultipartFile> files;

    private static Path testPath;


    /**
     * Filelds used @Before for initialize every time unit @Test Method
     */
    private DocComponent docComponent;

    private DocRepo docRepo;

    private FileHandler fileHandler;

    private File testFile;

    private DocDao testDocDao;


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

        for(int i = 0; i < 1000; i++) {
            files.add(mockMultipartFile);
        }
    }

    @Before
    public void setUp() throws Exception {
        fileHandler = Mockito.mock(FileHandler.class);
        docRepo = Mockito.mock(DocRepo.class);
        testFile = Mockito.mock(File.class);
        testDocDao = Mockito.mock(DocDao.class);
    }

    @Test
    public void notNullObjectTest() {
        assertThat(docComponent).isNotNull();
        assertThat(fileHandler).isNotNull();
    }

    @Test
    public void addDocs() {
        BDDMockito.given(fileHandler.processUploadFile(testFile)).willReturn("return file key");
        Mockito.when(fileHandler.processUploadFile(testFile)).thenReturn("return file key");
        Mockito.when(docRepo.save(ArgumentMatchers.any(DocDao.class))).thenReturn(testDocDao);

        DocComponent docComponent = new DocComponent(fileHandler, docRepo);
        FileTransactionDto fileTransactionDto = docComponent.addDocs(files);

        Assertions.assertThat(fileTransactionDto)
                .isNotNull()
                .isInstanceOf(FileTransactionDto.class)
                .matches(fileTransactionDto1 -> CoreMatchers.notNullValue().matches(fileTransactionDto1.getFileTransactKey()))
                .matches(fileTransactionDto1 -> fileTransactionDto1.getFileCount() == files.size())
        ;
    }

    @Test
    public void downDocTest() {

    }

    @Test
    public void multipartFileTest() throws FileNotFoundException, IOException {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file1", new FileInputStream(testFile));
        MultipartFile multiPartFile = mockMultipartFile;

        assertThat(multiPartFile.getOriginalFilename()).isEqualTo(testFile.getName());
        multiPartFile.transferTo(new File("D:/app/multiPartTest.txt"));

    }

    @Test
    @WithMockUser(username = "USER")
    public void makeTempFile() throws IllegalStateException, IOException {

        assertThat(testFile).isFile();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file1", new FileInputStream(testFile));
//		DocComponent docComponent = new DocComponent();
        File actual = docComponent.makeTempFile(mockMultipartFile);

        assertThat(actual).isFile();
        System.out.println("file name ~~ " + actual.getName());
    }

    @Test
    @WithMockUser(username = "USER")
    public void addDocTest() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("multipart", "zzz", "image/png", "aaa".getBytes());
        FileItemFactory fileItemFactory = new DiskFileItemFactory(10240, new File("D:\\app\\download"));
        FileItem fileItem = fileItemFactory.createItem("1557298263287w1jl3O7KfY", ContentType.IMAGE_PNG.getMimeType(), true, "aaa");
        fileItem.getOutputStream();
        MultipartFile multipartFile1 = new CommonsMultipartFile(fileItem);
        docComponent.addDoc(multipartFile1);
    }

    @Test
    @WithMockUser(username = "USER")
    public void updateDocTest() {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipart", "mockupTest.png", MediaType.IMAGE_PNG.getType(), "asdfasdf".getBytes());

        DocDao docDao = docRepo.getOne(500L);
        docDao.setDocName(mockMultipartFile.getOriginalFilename());
        docDao.setDocExt(FilenameUtils.getExtension(mockMultipartFile.getOriginalFilename()));
        docDao.setDocSize(mockMultipartFile.getSize());


    }


}
