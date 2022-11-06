package com.library.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.library.constants.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    @Bean(ApplicationConstants.S3_CLIENT)
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

}
