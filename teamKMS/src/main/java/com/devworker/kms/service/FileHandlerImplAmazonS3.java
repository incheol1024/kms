package com.devworker.kms.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.util.StringKeyUtil;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Component
public class FileHandlerImplAmazonS3 implements FileHandler {

	private S3Client s3Client;

	@Value(value = "${amazon.s3.bucket}")
	private String bucket;
	
	@Value(value = "${amazon.s3.download.tmp}")
	private String tmpDown;

	@Autowired
	public FileHandlerImplAmazonS3(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public String getFilePath(String fileKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFile(File file) {

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

		File getFile = new File(tmpDown + File.pathSeparator + key);
		GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest, ResponseTransformer.toFile(getFile));

		boolean getSuccess = getObjectResponse.sdkHttpResponse().isSuccessful();

		if (getSuccess)
			return getFile;

		throw new RuntimeException();
	}

	@Override
	public List<DocDao> processUploadFile(int boardId, List<MultipartFile> file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocDao> processUploadFile(BoardDao boardId, int CommentId, List<MultipartFile> file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDto processUploadFile(int boardId, int CommentId, MultipartFile file) throws Exception {
		
		
		return null;
	}

	@Override
	public FileDto processUploadFile(MultipartFile file) throws Exception {
		
		
		return null;
	}

	@Override
	public void deleteFile(long docId) throws Exception {
		// TODO Auto-generated method stub

	}

}
