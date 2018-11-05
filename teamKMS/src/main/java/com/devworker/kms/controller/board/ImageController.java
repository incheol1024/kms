package com.devworker.kms.controller.board;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final String BUCKET_NAME = "incheol1024";
    private static final String ACCESS_KEY = "AKIAIQLQIYATWBZFHUZQ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;

    @PostMapping("/upload")
	public String processRegistration(@RequestPart("profilePicture") MultipartFile profilePicture) throws IllegalStateException, IOException {
	
		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client(awsCredentials);
        
        File file = new File("E:\\app\\"+ profilePicture.getOriginalFilename());
		profilePicture.transferTo(file);
		
		uploadFile(file);
		
		return null;
	}
	
    public void uploadFile(File file) {
        if (amazonS3 != null) {
            try {
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME + "/testDir"/*sub directory*/, file.getName(), file);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file
 
            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }

}
