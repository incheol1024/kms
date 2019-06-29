package com.devworker.kms.component;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

public class FileHandlerTest {

    @Test
    public void propStaticTest() {

        String tempDir = FileHandler.tempDefaultDir;
        Assertions.assertThat(tempDir).isNotNull().isNotEmpty();
        System.out.println(tempDir);

    }

    @Test
    public void checkUploadDirTest() throws IOException {
        Assertions.assertThat(FileHandler.checkUploadDir()).exists().isDirectory();
    }

    @Test
    public void checkDownloadDirTest() throws IOException {
        Assertions.assertThat(FileHandler.checkDownloadDir()).exists().isDirectory();
    }


}