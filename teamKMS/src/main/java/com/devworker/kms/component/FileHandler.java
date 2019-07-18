package com.devworker.kms.component;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface FileHandler {

    String DefaultTemporaryDirectory = System.getProperty("java.io.tmpdir") + File.separator + "kms." + System.currentTimeMillis();

    String processUploadFile(File file);

    File processDownloadFile(String key);

    boolean deleteFile(String key);

    static String getUploadTemporaryDirectory() {
        return getCanonicalPath(checkTemporaryDirectory(DefaultTemporaryDirectory + File.separator + "upload"));
    }

    static String getDownloadTemporaryDirectory() {
        return getCanonicalPath(checkTemporaryDirectory(DefaultTemporaryDirectory + File.separator + "download"));
    }

    private static String getCanonicalPath(File file) {

        String path = "";
        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private static File checkTemporaryDirectory(String directory) {

        File file = new File(DefaultTemporaryDirectory);
        if (!file.exists())
            file.mkdir();

        File tempFile = new File(directory);
        if (!tempFile.exists())
            new File(directory).mkdir();

        return tempFile;
    }
}
