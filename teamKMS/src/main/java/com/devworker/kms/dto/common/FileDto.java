package com.devworker.kms.dto.common;

public class FileDto {

	private final String key;

	private final long fileSize;

	private final String fileName;

	private final String fileExt;

	private FileDto() {
		throw new AssertionError();
	}

	private FileDto(FileDtoBuilder fileDtoBuilder) {
		this.key = fileDtoBuilder.getKey();
		this.fileSize = fileDtoBuilder.getFileSize();
		this.fileName = fileDtoBuilder.getFileName();
		this.fileExt = fileDtoBuilder.getFileExt();
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

	public static class FileDtoBuilder {

		private String key;

		private long fileSize;

		private String fileName;

		private String fileExt;

		private FileDtoBuilder() {
			throw new AssertionError();
		}

		public FileDtoBuilder(String key) {
			this.key = key;
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
