package com.devworker.kms.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class FileHandlerRetryCallback implements FileHandler {

    @Autowired
    FileHandler fileHandler;

    @Value("${file.handle.retry.number}")
    int retryNumber;

    private static Logger logger = LoggerFactory.getLogger(FileHandlerRetryCallback.class);

    @Override
    public String processUploadFile(File file) {
        String retryKey;
        for (int i = 0; i < retryNumber; i++) {
            logger.warn("retrying {} upload file", i);
            retryKey = fileHandler.processUploadFile(file);
            if (Objects.nonNull(retryKey))
                return retryKey;
        }

        logger.error("Fail upload file retry number={}", retryNumber);
        throw new RuntimeException("Fail upload file retry number=" + retryNumber);
    }

    @Override
    public File processDownloadFile(String key) {
        File file;
        for (int i = 0; i < retryNumber; i++) {
            logger.warn("retrying {} download file", i);
            file = fileHandler.processDownloadFile(key);
            if (Objects.nonNull(file))
                return file;
        }

        logger.error("Fail download file retry number={}", retryNumber);
        throw new RuntimeException("Fail download file retry number= " + retryNumber);
    }

    @Override
    public boolean deleteFile(String key) {
        boolean result;
        for (int i = 0; i < retryNumber; i++) {
            logger.warn("retrying {} delete file", i);
            result = fileHandler.deleteFile(key);
            if (result == true)
                return true;
        }

        logger.error("Fail delete file retry number={}", retryNumber);
        throw new RuntimeException("Fail delete file retry number=" + retryNumber);
    }


}
