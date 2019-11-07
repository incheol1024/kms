package com.devworker.kms.component;

import com.devworker.kms.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;

@Component
public interface FileHandler {

    String DEFAULT_PATH = System.getProperty("java.io.tmpdir");

    Path DEFAULT_TEMPORARY_UPLOAD_PATH = Paths.get(DEFAULT_PATH, "kms", CommonUtil.getCurrentUser(), "upload");

    Path DEFAULT_TEMPORARY_DOWNLOAD_PATH = Paths.get(DEFAULT_PATH, "kms", CommonUtil.getCurrentUser(), "download");

    String processUploadFile(File file);

    File processDownloadFile(String key);

    boolean deleteFile(String key);

    static String getUploadTemporaryDirectory() {
        return checkTemporaryDirectory(DEFAULT_TEMPORARY_UPLOAD_PATH);
    }

    static String getDownloadTemporaryDirectory() {
        return checkTemporaryDirectory(DEFAULT_TEMPORARY_DOWNLOAD_PATH);
    }

    static String checkTemporaryDirectory(Path directoryPath) {

        try {
            if (!Files.exists(directoryPath))
                return Files.createDirectories(directoryPath).toRealPath().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryPath.toString();
    }
}
