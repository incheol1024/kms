package com.devworker.kms.component;

import com.devworker.kms.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public interface FileHandler {

    String DEFUALT_ROOT_PATH = System.getProperty("java.io.tmpdir");

//    String DefaultTemporaryDirectory = System.getProperty("java.io.tmpdir") + File.separator + "kms." + System.currentTimeMillis();

    Path DEFAULT_TEMPORARY_UPLOAD_PATH = Paths.get(DEFUALT_ROOT_PATH, "kms", CommonUtil.getCurrentUser(), "upload");

    Path DEFAULT_TEMPORARY_DOWNLOAD_PATH = Paths.get(DEFUALT_ROOT_PATH, "kms", CommonUtil.getCurrentUser(), "download");

    String processUploadFile(File file);

    File processDownloadFile(String key);

    boolean deleteFile(String key);

    static String getUploadTemporaryDirectory() {
        return checkTemporaryDirectory(DEFAULT_TEMPORARY_UPLOAD_PATH);
    }

    static String getDownloadTemporaryDirectory() {
        return checkTemporaryDirectory(DEFAULT_TEMPORARY_DOWNLOAD_PATH);
    }

    private static String checkTemporaryDirectory(Path directoryPath) {

        try {
            if (!Files.exists(directoryPath))
                return Files.createDirectories(directoryPath).toRealPath().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryPath.toString();
    }
}
