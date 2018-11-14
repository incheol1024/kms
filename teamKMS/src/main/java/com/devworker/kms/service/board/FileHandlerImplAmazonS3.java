package com.devworker.kms.service.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class FileHandlerImplAmazonS3 implements FileHandler {

	private static final String BUCKET_NAME = "incheol1024";
	private static final String ACCESS_KEY = "AKIAIQLQIYATWBZFHUZQ";
	private static final String SECRET_KEY = "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";

	@Override
	public String getFilePath(String fileKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFile(File file) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);

		if (amazonS3 != null) {
			PutObjectRequest putObjectRequest = null;

			try {
				putObjectRequest = new PutObjectRequest(BUCKET_NAME, file.getName(),
						file);

				putObjectRequest
						.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
				amazonS3.putObject(putObjectRequest);

				return putObjectRequest.getKey();
				// return (putResult != null) ? true : false;

			} catch (AmazonServiceException ase) {
				ase.printStackTrace();
			} finally {
				amazonS3 = null;
			}
		}
		return null;

	}

	@Override
	public File downloadFile(String fileKey) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonS3 s3 = new AmazonS3Client(awsCredentials);
		S3Object o = null;
		S3ObjectInputStream s3is = null;
		File file = null;

		try {
			o = s3.getObject(BUCKET_NAME, fileKey);
			s3is = o.getObjectContent();
			file = new File("E:/app/download.png");
			FileOutputStream fos = new FileOutputStream(file);

			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

}
