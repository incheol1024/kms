package com.devworker.kms.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Component
public class FileUploadUtil {

	private static final String BUCKET_NAME = "";
	private static final String ACCESS_KEY = "";
	private static final String SECRET_KEY = "";
	private AmazonS3 amazonS3;

	public boolean processRegistration(MultipartFile file)
			throws IllegalStateException, IOException {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		amazonS3 = new AmazonS3Client(awsCredentials);

		File fileTemp = new File("E:\\app\\" + file.getOriginalFilename());
		file.transferTo(fileTemp);

		return uploadFile(fileTemp);
	}

	public boolean uploadFile(File file) {

		if (amazonS3 != null) {
			PutObjectRequest putObjectRequest = null;
			PutObjectResult putResult = null;

			try {
				putObjectRequest = new PutObjectRequest(
						BUCKET_NAME + "/testDir"/* sub directory */, file.getName(),
						file);
				putObjectRequest
						.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
				putResult = amazonS3.putObject(putObjectRequest);
				System.out.println(putResult.getMetadata().getETag());

				return (putResult != null) ? true : false;
			} catch (AmazonServiceException ase) {
				ase.printStackTrace();
			} finally {
				amazonS3 = null;
			}
		}
		return false;
	}

}
