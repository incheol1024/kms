package com.devworker.kms.dto.common;

import java.io.File;

public class FileDto {

	private final File file;

	private final String key;

	private final long fileSize;

	private final String fileName;

	private final String fileExt;

	private FileDto() {
		throw new AssertionError();
	}

	private FileDto(FileDtoBuilder fileDtoBuilder) {
		this.file = fileDtoBuilder.getFile();
		this.key = fileDtoBuilder.getKey();
		this.fileSize = fileDtoBuilder.getFileSize();
		this.fileName = fileDtoBuilder.getFileName();
		this.fileExt = fileDtoBuilder.getFileExt();
	}

	public File getFile() {
		return file;
	}

	public String getKey() {
		return key;
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
	
	public static FileDtoBuilder builder() {
		return new FileDto.FileDtoBuilder();
	}

	public static class FileDtoBuilder {

		private File file;

		private String key;

		private long fileSize;

		private String fileName;

		private String fileExt;

		private FileDtoBuilder() {
			//throw new AssertionError();
		}

		public File getFile() {
			return file;
		}

		public FileDtoBuilder setFile(File file) {
			this.file = file;
			return this;
		}

		public String getKey() {
			return key;
		}

		public FileDtoBuilder setKey(String key) {
			this.key = key;
			return this;
		}

		public long getFileSize() {
			return fileSize;
		}

		public FileDtoBuilder setFileSize(long fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public String getFileName() {
			return fileName;
		}

		public FileDtoBuilder setFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public String getFileExt() {
			return fileExt;
		}

		public FileDtoBuilder setFileExt(String fileExt) {
			this.fileExt = fileExt;
			return this;
		}

		public FileDto build() {
			return new FileDto(this);
		}

	}

}
