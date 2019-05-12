package com.devworker.kms.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.dto.common.FileTransactionDto;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DocComponentTest {
	
	@Autowired
	DocComponent docComponent;
	
	@Mock
	FileHandler fileHandler;

	List<MultipartFile> files;
	long docId;
	File testFile;

	@Before
	public void setUp() throws Exception {

		testFile = new File("D:\\inzent\\run.sh.txt");
		
		FileInputStream fileInputStream1 = new FileInputStream(testFile);
		FileInputStream fileInputStream2 = new FileInputStream(testFile);
		FileInputStream fileInputStream3 = new FileInputStream(testFile);

		files = new ArrayList<MultipartFile>();
		files.add(new MockMultipartFile("file1", fileInputStream1));
		//files.add(new MockMultipartFile("file2", fileInputStream2));
		//files.add(new MockMultipartFile("file3", fileInputStream3));

		testFile = new File("D:\\inzent\\run.sh.txt");
		
		FileDto fileDto = FileDto.builder()
				.setFile(testFile)
				.setFileExt(FilenameUtils.getExtension(testFile.getName()))
				.setFileName(testFile.getName())
				.setFileSize(testFile.length())
				.build();
		
		
		FileDto resultFileDto = FileDto.builder()
				.setKey("randomKey")
				.build();
		
		BDDMockito.given(fileHandler.processUploadFile(fileDto)).willReturn(resultFileDto);
			
		docComponent = new DocComponent(fileHandler);
	}

	@Test
	public void notNullObjectTest() {
		assertThat(docComponent).isNotNull();
		assertThat(fileHandler).isNotNull();
	}

	@Test
	@WithMockUser(username = "USER")
	public void addDocTest() throws Exception {

		FileTransactionDto fileTransactionDto = docComponent.addDoc(files);
		assertThat(fileTransactionDto).isNotNull().extracting(FileTransactionDto::getFileCount).isEqualTo(files.size());
		assertThat(fileTransactionDto.getFileTransactKey()).isNotNull();

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
		DocComponent docComponent = new DocComponent(fileHandler);
		File actual =docComponent.makeTempFile(mockMultipartFile);
		
		assertThat(actual).isFile();
		System.out.println("file name ~~ " +actual.getName());
		
	}

}
