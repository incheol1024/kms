package com.devworker.kms.service.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.devworker.kms.service.FileHandler;
import com.devworker.kms.service.FileHandlerImplAmazonS3;
import com.devworker.kms.util.StringKeyUtil;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.BucketLocationConstraint;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileHandlerImplAmazonS3Test {

	@Autowired
	S3Client s3ClientBean;
	
	@Autowired
	@Qualifier("fileHandlerImplAmazonS3")
	FileHandler fileHandler;
	
	
	static S3Client s3;
	static Region region;
	static List<String> myBuckets;
	
	static String bucket;
	static String key;
	
	static File testFile;

	@Before
	public void setUp() {
		region = Region.AP_NORTHEAST_2;
		s3 = S3Client.builder().region(region).build();
		myBuckets = new ArrayList<String>();
		myBuckets.add("incheol1024");
		myBuckets.add("kmsst");
		
		bucket = "kmsst";
		key = StringKeyUtil.generateUniqueKey();
		
		testFile = new File("D:/app/test.png");
	}

	@Test
	public void notNullBean() {
		assertThat(s3ClientBean).isNotNull();
		assertThat(fileHandler).isNotNull().isExactlyInstanceOf(FileHandlerImplAmazonS3.class);
	}
	
	@Test
	public void listBucketRequestTest() {
		synchronized (s3) {
			ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
			ListBucketsResponse listBucketResponse = s3.listBuckets(listBucketsRequest);
			listBucketResponse.buckets().stream().forEach(x -> System.out.println(x.name()));

			for (Bucket bucket : listBucketResponse.buckets()) {
				assertThat(myBuckets.contains(bucket.name())).isTrue();
			}

		}
	}

	@Test
	public void a_putObjectTest() {

		synchronized (s3) {
			PutObjectResponse putObjectResponse = s3.putObject(
					PutObjectRequest.builder().bucket(bucket).key(key).build(),
					RequestBody.fromFile(testFile));
			
			assertThat(putObjectResponse.sdkHttpResponse().statusCode()).isEqualTo(200);
			assertThat(putObjectResponse.sdkHttpResponse().isSuccessful()).isTrue();
		}

	}
	
	@Test
	public void b_getObjectTest() {
		
		synchronized (s3) {
			GetObjectRequest getObjectRequest = GetObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.build();
						
			GetObjectResponse getObjectResponse = s3.getObject(
					getObjectRequest, ResponseTransformer.toFile(new File("D:\\app\\" + key)));
			
			assertThat(getObjectResponse.sdkHttpResponse().isSuccessful()).isTrue();
			assertThat(getObjectResponse.sdkHttpResponse().statusCode()).isEqualTo(200);
		}
	}
	
	@Test
	public void c_deleteObjectTest() {
		
		synchronized (s3) {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.build();
			
			DeleteObjectResponse deleteObjectResponse = s3.deleteObject(deleteObjectRequest);
			
			assertThat(deleteObjectResponse.sdkHttpResponse().isSuccessful()).isTrue();
			assertThat(deleteObjectResponse.sdkHttpResponse().statusCode()).isEqualTo(204);
		}
	}
	
	

//	@Test
	public void createBucketTest() {

		synchronized (s3) {
			CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
					.bucket("incheolbuckettest")
					.createBucketConfiguration(CreateBucketConfiguration.builder()
							.locationConstraint(BucketLocationConstraint.AP_SOUTHEAST_1).build())
					.build();

			s3.createBucket(createBucketRequest);
		}
	}
	
	@Test
	public void uploadFileTest() {
		String retString = fileHandler.uploadFile(testFile);
		assertThat(retString).hasSameSizeAs(key).contains(key).hasSameHashCodeAs(key);
	}

}
