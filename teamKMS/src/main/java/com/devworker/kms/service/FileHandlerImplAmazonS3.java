package com.devworker.kms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.util.FileUtil;
import com.devworker.kms.util.StringKeyUtil;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

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
		boolean putSuccess = s3Client.putObject((putObjectRequestBuilder) -> {
			putObjectRequestBuilder.bucket(bucket).key(key).build();
		}, RequestBody.fromFile(file)).sdkHttpResponse().isSuccessful();

		if (putSuccess) {
			return key;
		}
		throw new RuntimeException();

	}

	@Override
	public File downloadFile(String key) {

		File getFile = new File(tmpDown + File.separator + key);
		ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject((getObjectReqeustBuilder) -> {
			getObjectReqeustBuilder.bucket(bucket).key(key).build();
		});
		
		try(FileOutputStream fileOutputStream = new FileOutputStream(getFile)) {
			long fileSize = responseInputStream.transferTo(fileOutputStream);
			
		} catch (IOException e) {
			throw new  RuntimeException();
		}
		return getFile;
	}
	
	@Override
	public File processDownloadFile(String key) {
		return null;
		
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
