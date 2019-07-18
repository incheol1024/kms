package com.devworker.kms.component;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

public class FileHandlerTest {

    @Test
    public void propStaticTest() {

        String tempDir = FileHandler.DefaultTemporaryDirectory;
        Assertions.assertThat(tempDir).isNotNull().isNotEmpty();
        System.out.println(tempDir);
    }

    @Test
    public void getUploadDirTest() {
        System.out.println(FileHandler.getUploadTemporaryDirectory());
        Assertions.assertThat(FileHandler.getUploadTemporaryDirectory()).isNotEmpty().startsWith("C:");
    }

    @Test
    public void getDownloadDirTest() {
        Assertions.assertThat(FileHandler.getDownloadTemporaryDirectory()).isNotEmpty().startsWith("C:");
    }


}