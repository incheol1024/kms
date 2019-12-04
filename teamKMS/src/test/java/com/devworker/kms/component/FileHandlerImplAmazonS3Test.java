package com.devworker.kms.component;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(value = SpringJUnit4ClassRunner.class)
public class FileHandlerImplAmazonS3Test {

    @Autowired
    @Qualifier("fileHandlerImplAmazonS3")
    FileHandler fileHandler;

    private static File testFile;
    private static String downloadKey;

    private static MultipartFile multipartFile;

    @Autowired
    ApplicationContext applicationContext;

    @Rule
    public ExpectedException expectedException;

    @BeforeClass
    public static void setUpClass()  throws IOException{
        testFile = Paths.get("D:/app/upload", "java.jpg").toFile();
        downloadKey = "1575287924226atputamjyy";
        multipartFile = new MockMultipartFile("test.png", new FileInputStream(testFile));

    }

    @Before
    public void setUp() {
        expectedException = ExpectedException.none();
    }

    @Test
    public void notNullBeanTest() {
        assertThat(fileHandler).isNotNull().isExactlyInstanceOf(FileHandlerImplAmazonS3.class);
    }

    @Test
    public void processDownloadFileTest() {
        File file = fileHandler.processDownloadFile(downloadKey);
        Assertions.assertThat(file)
                .exists()
                .canRead()
                .isNotNull()
                .isFile()
                .isInstanceOfSatisfying(File.class, actual -> {
                    Assertions.assertThat(actual.length()).isEqualTo(file.length());
                })
                .isInstanceOf(File.class)
                ;
    }

    @Test
    public void processUploadFileTest() throws Exception {
        String result = fileHandler.processUploadFile(testFile);
        System.out.println(result);
        Assertions.assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .isExactlyInstanceOf(String.class)
                .doesNotContainOnlyWhitespaces()
        ;
    }

    @Test
    public void deleteFileTest()  {
        boolean isDelete = fileHandler.deleteFile("1575283421728rolvpvnwuu");

        Assertions.assertThat(isDelete)
                .isTrue()
                .isNotNull()
                .isExactlyInstanceOf(Boolean.class);

        //삭제 검증, 조회
        expectedException.expect(NoSuchKeyException.class);
        expectedException.expectMessage(CoreMatchers.containsString("The specified key does not exist"));
        fileHandler.processDownloadFile("1575283421728rolvpvnwuu"); // NoSuchKeyExeception 발생
    }

    @Test
    public void shouldException() {

    }
}
