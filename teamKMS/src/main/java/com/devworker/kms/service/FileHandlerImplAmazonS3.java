package com.devworker.kms.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.util.FileUtil;
import com.devworker.kms.util.StringKeyUtil;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class FileHandlerImplAmazonS3 implements FileHandler {

	private S3Client s3Client;

	@Value(value = "${amazon.s3.bucket}")
	private String bucket;

	@Value(value = "${file.download.tmp}")
	private String tmpDown;

	@Value(value = "${file.upload.tmp}")
	private String tmpUpload;

	@Autowired
	public FileHandlerImplAmazonS3(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	private String uploadFile(File file) {

		String key = StringKeyUtil.generateUniqueKey();
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().key(key).bucket(bucket).build();

		boolean putSuccess = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file)).sdkHttpResponse()
				.isSuccessful();

		if (putSuccess) {
			return key;
		}
		throw new RuntimeException();
	}

	@Override
	public File downloadFile(String key) {

		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();

		File getFile = new File(tmpDown + File.separator + key);
		GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest, ResponseTransformer.toFile(getFile));

		boolean getSuccess = getObjectResponse.sdkHttpResponse().isSuccessful();

		if (getSuccess)
			return getFile;

		throw new RuntimeException();
	}

	@Override
	public FileDto processUploadFile(MultipartFile multipartFile) throws Exception {
		File file = FileUtil.convertToFile(multipartFile, tmpUpload);
		String key = uploadFile(file);

		return new FileDto.FileDtoBuilder(key).setFileName(file.getName())
				.setFileExt(FilenameUtils.getExtension(file.getName())).setFileSize(FileUtils.sizeOf(file)).build();
	}

	@Override
	public boolean deleteFile(String key) throws Exception {

		DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject((deleteObjectlBuilder) -> {
			deleteObjectlBuilder.bucket(bucket).key(key).build();
		});

		return deleteObjectResponse.sdkHttpResponse().isSuccessful();
	}

}
