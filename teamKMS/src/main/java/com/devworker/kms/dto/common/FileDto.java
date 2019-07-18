package com.devworker.kms.dto.common;

import java.io.File;

public class FileDto {

    private final File file;

    private String key;

    private final long fileSize;

    private final String fileName;

    private final String fileExt;

    private FileDto() {
        throw new AssertionError();
    }

    private FileDto(File file, String key, long fileSize, String fileName, String fileExt) {
        this.file = file;
        this.key = key;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileExt = fileExt;
    }

    public static FileDto newInstance(String key) {
        return newInstance(null, key, 0, null, null);
    }

    public static FileDto newInstance(File file, long fileSize, String fileName, String fileExt) {
        return newInstance(file, null, fileSize, fileName, fileExt);
    }

    public static FileDto newInstance(File file, String key, long fileSize, String fileName, String fileExt) {
        FileDto fileDtoSample = new FileDto(file, key, fileSize, fileName, fileExt);
        return fileDtoSample;
    }

    public File getFile() {
        return file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExt() {
        return fileExt;
    }


}
