package com.devworker.kms.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.util.FileUtil;
import com.devworker.kms.util.StringKeyUtil;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Component
@Qualifier(value = "amazonS3")
@Primary
public class FileHandlerImplAmazonS3 implements FileHandler, BeanFactoryAware {

	@Value(value = "${amazon.s3.bucket}")
	private String bucket;

	@Value(value = "${file.download.tmp}")
	private String tmpDown;

	@Value(value = "${file.upload.tmp}")
	private String tmpUpload;
	
	private BeanFactory beanFactory;

	private String uploadFile(File file) {

		String key = StringKeyUtil.generateUniqueKey();
		boolean putSuccess = getS3Client().putObject((putObjectRequestBuilder) -> {
			putObjectRequestBuilder.bucket(bucket).key(key).build();
		}, RequestBody.fromFile(file)).sdkHttpResponse().isSuccessful();

		if (putSuccess) {
			return key;
		}
		throw new RuntimeException();

	}

	private GetObjectResponse downloadFile(String key, File file) {

	//	File getFile = new File(tmpDown + File.separator + key);
		ResponseInputStream<GetObjectResponse> responseInputStream = getS3Client().getObject((getObjectReqeustBuilder) -> {
			getObjectReqeustBuilder.bucket(bucket).key(key).build();
		});

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			responseInputStream.transferTo(fileOutputStream);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return responseInputStream.response();
	}

	@Override
	public FileDto processDownloadFile(FileDto fileDto) {
		
		File getTmpFile = new File(tmpDown + File.separator + fileDto.getKey());
		GetObjectResponse getObjectResponse = downloadFile(fileDto.getKey() ,getTmpFile);
		
		if(getObjectResponse.sdkHttpResponse().isSuccessful()) 
			return FileDto.builder().setFile(getTmpFile).build();

		throw new RuntimeException();
	}

	@Override
	public FileDto processUploadFile(FileDto fileDto) throws Exception {

		String key = uploadFile(fileDto.getFile());
		return FileDto.builder().setKey(key).build();
	}

	@Override
	public boolean deleteFile(String key) throws Exception {

		DeleteObjectResponse deleteObjectResponse = getS3Client().deleteObject((deleteObjectlBuilder) -> {
			deleteObjectlBuilder.bucket(bucket).key(key).build();
		});

		return deleteObjectResponse.sdkHttpResponse().isSuccessful();
	}
	
	public S3Client getS3Client() {
		return beanFactory.getBean(S3Client.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
