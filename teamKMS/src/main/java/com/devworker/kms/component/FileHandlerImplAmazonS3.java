package com.devworker.kms.component;

import com.devworker.kms.aspect.ExecutionTimeLogging;
import com.devworker.kms.util.StringKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.FileOutputStream;

@Component
@Qualifier(value = "amazonS3")
@Primary
public class FileHandlerImplAmazonS3 implements FileHandler {

    @Value(value = "${amazon.s3.bucket}")
    private String bucket;

    @Autowired
    FileHandler fileHandlerRetryCallback;

    /*public  FileHandlerImplAmazonS3() {}

    @Autowired
    public FileHandlerImplAmazonS3(FileHandler fileHandlerRetryCallback) {
        this.fileHandlerRetryCallback = fileHandlerRetryCallback;
    }*/

    private static final S3Client s3Client = getS3Client();

    private static Logger logger = LoggerFactory.getLogger(FileHandlerImplAmazonS3.class);

    private String uploadFile(File file) throws Exception {

        String key = StringKeyUtil.generateUniqueKey();
        boolean putSuccess = s3Client
                .putObject((putObjectRequestBuilder) -> {
                            putObjectRequestBuilder.bucket(bucket).key(key).build();
                        },
                        RequestBody.fromFile(file)).sdkHttpResponse().isSuccessful();

        if (putSuccess)
            return key;

        throw new Exception("Fail upload file. file Name=" + file.getCanonicalPath());
    }

    private File downloadFile(String key, File file) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client
                .getObject((getObjectRequestBuilder) -> getObjectRequestBuilder.bucket(bucket).key(key).build());
        responseInputStream.transferTo(fileOutputStream);

        if (responseInputStream.response().sdkHttpResponse().isSuccessful())
            return file;

        throw new Exception("Fail download file. key=" + key);
    }

    @Override
    @ExecutionTimeLogging
    public String processUploadFile(File file) {
        String key = null;
        try {
            key = uploadFile(file);
        } catch (Exception e) {
            logger.error("{}", e);
            e.printStackTrace();
            fileHandlerRetryCallback.processUploadFile(file);
        }
        return key;
    }

    @Override
    public File processDownloadFile(String key) {
        File tmp = new File(FileHandler.getDownloadTemporaryDirectory() + File.separator + key);
        try {
            tmp = downloadFile(key, tmp);
        } catch (Exception e){
            logger.error("{}", e);
            e.printStackTrace();
        }
        return tmp;
    }


    @Override
    public boolean deleteFile(String key) {

        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject((deleteObjectlBuilder) -> {
            deleteObjectlBuilder.bucket(bucket).key(key).build();
        });
        return deleteObjectResponse.sdkHttpResponse().isSuccessful();

    }

    private static S3Client getS3Client() {
        AwsCredentialsProvider awsCredentialsProvider = ProfileCredentialsProvider.create();
        return S3Client.builder().credentialsProvider(awsCredentialsProvider).region(Region.AP_NORTHEAST_2).build();
    }

}
