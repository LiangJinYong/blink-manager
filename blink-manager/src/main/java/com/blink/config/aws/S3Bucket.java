package com.blink.config.aws;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;
import net.bbchat.aws.AbstractS3Bucket;
import net.bbchat.aws.S3PutObject;


@Slf4j
@Component
public class S3Bucket extends AbstractS3Bucket {

    private final ResourceLoader resourceLoader;

    private final AmazonS3 s3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket ;

    public S3Bucket(ResourceLoader resourceLoader, AmazonS3 s3) {
        this.resourceLoader = resourceLoader;
        this.s3 = s3;
    }

    @Override
    public Upload upload(String keyPrefix, InputStream file, String filename) throws InterruptedException {
        TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3).build();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        try {
            objectMetadata.setContentLength(file.available());
        } catch (IOException e) {
            log.error("S3Bucket.upload", e);
        }

        S3PutObject putObject = new S3PutObject(file).setKeyPrefix(keyPrefix).setFilename(filename).setMetadata(objectMetadata);
        PutObjectRequest request = putObject.toPutObjectRequest(bucket);
        return tm.upload(request);
    }

    @Override
    public Resource download(String key)  {
        //String format = key.startsWith("/") ? "s3://s3.amazonaws.com/%s%s" : "s3://s3.amazonaws.com/%s/%s";
        return null;
    }

    public FileResource downloadFromBucket(String key) throws IOException {
        int lastIndex = key.lastIndexOf("/");
        lastIndex = (lastIndex > -1) ? lastIndex + 1 : lastIndex;
        String filename = (lastIndex > 0) ? key.substring(lastIndex) : key;

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object s3Object = s3.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        return new FileResource(bytes, filename, key);
    }

    public void delete(String key) {
        s3.deleteObject(bucket, key);
    }

}
