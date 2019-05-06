package com.devworker.kms.dto.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

public class FileDtoTest {

	private File testFile;

	@Before
	public void setUp() {
		this.testFile = new File("D:/app/test.png");

	}

	@Test
	public void fileDtoObjectTest() {

		FileDto fileDto1 = FileDto.builder().setFile(testFile).setFileName(testFile.getName())
				.setFileExt(FilenameUtils.getExtension(testFile.getName())).setFileSize(testFile.length()).build();
		
		FileDto fileDto2 = FileDto.builder().setFile(testFile).setFileName(testFile.getName())
				.setFileExt(FilenameUtils.getExtension(testFile.getName())).setFileSize(testFile.length()).build();
		

		assertThat(fileDto1).isNotEqualTo(fileDto2);
	}

}
