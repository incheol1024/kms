package com.devworker.kms.component;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.util.StringKeyUtil;
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
import java.io.IOException;
import java.nio.file.Path;

@Component
@Qualifier(value = "amazonS3")
@Primary
public class FileHandlerImplAmazonS3 implements FileHandler {

    @Value(value = "${amazon.s3.bucket}")
    private String bucket;

    private static final S3Client s3Client = getS3Client();

    private String uploadFile(File file) {

        String key = StringKeyUtil.generateUniqueKey();
        boolean putSuccess = s3Client
                .putObject((putObjectRequestBuilder) -> {
                            putObjectRequestBuilder.bucket(bucket).key(key).build();
                        },
                        RequestBody.fromFile(file)).sdkHttpResponse().isSuccessful();

        if (putSuccess)
            return key;

        throw new RuntimeException();
    }

    private GetObjectResponse downloadFile(String key, File file) {


        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client
                    .getObject((getObjectReqeustBuilder) -> {
                        getObjectReqeustBuilder.bucket(bucket).key(key).build();
                    });
            responseInputStream.transferTo(fileOutputStream);

            return responseInputStream.response();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String processUploadFile(File file) {
        return uploadFile(file);
    }

    @Override
    public File processDownloadFile(String key) {

        File tmp = new File(FileHandler.getDownloadTemporaryDirectory() + File.separator + key);
        GetObjectResponse getObjectResponse = downloadFile(key, tmp);

        if (getObjectResponse != null && getObjectResponse.sdkHttpResponse().isSuccessful())
            return tmp;

        throw new RuntimeException("Fail to download file, key =" + key);
    }

    /*
    @Override
    public FileDto processUploadFile(FileDto fileDto) {
        String key = uploadFile(fileDto.getFile());
        fileDto.setKey(key);
        return fileDto;
    }

    @Override
    public FileDto processDownloadFile(FileDto fileDto) {

        File getTmpFile = new File(FileHandler.getDownloadTemporaryDirectory() + fileDto.getKey());
        GetObjectResponse getObjectResponse = downloadFile(fileDto.getKey(), getTmpFile);

        if (getObjectResponse != null && getObjectResponse.sdkHttpResponse().isSuccessful())
            return FileDto.builder().setFile(getTmpFile).build();

        throw new RuntimeException();
    }

*/


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
