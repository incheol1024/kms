package com.devworker.kms.component;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

public class FileHandlerTest {

    @Test
    public void propStaticTest() {

        String tempDir = FileHandler.DEFUALT_ROOT_PATH;
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
        System.out.println(FileHandler.getDownloadTemporaryDirectory());
        Assertions.assertThat(FileHandler.getDownloadTemporaryDirectory()).isNotEmpty().startsWith("C:");
    }


}