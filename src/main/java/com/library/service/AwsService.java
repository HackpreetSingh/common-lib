package com.library.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class AwsService {

    @Autowired
    private AmazonS3 s3;

    /***
     *
     * @param bucketName denotes the name of the bucket
     * @param key denotes the file path along with filename
     * @param content denotes content of file to be saved
     * @throws IOException,AmazonS3Exception
     */
    public void saveToS3(String bucketName, String key, String content) throws Exception {
        InputStream s3InputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        s3.putObject(bucketName, key, s3InputStream, null);
    }

    /***
     *
     * @param bucketName denotes the name of the bucket
     * @param key denotes the file path along with filename
     * @return the contents of the file being read
     * @throws IOException,AmazonS3Exception
     */
    public String fetchFromS3(String bucketName, String key) throws Exception {
        S3Object s3Object = s3.getObject(bucketName, key);
        if (s3Object == null) {
            throw new AmazonS3Exception("S3 Object could not be fetched!!");
        }
        InputStream s3InputStream = s3Object.getObjectContent();
        if (s3InputStream == null) {
            throw new AmazonS3Exception("S3 Object contents could not be fetched!!");
        }
        return StreamUtils.copyToString(s3InputStream, StandardCharsets.UTF_8);
    }
}
