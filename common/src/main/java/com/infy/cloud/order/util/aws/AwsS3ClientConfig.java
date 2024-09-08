package com.infy.cloud.order.util.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


public class AwsS3ClientConfig {

    public static AmazonS3 getS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAZQ3DNNWAC4WOEXOE",
                "47kwILMxZ/dKyzK1xDnmIuCNMc0hT0MEmFbZ0t3W"
        );

        return   AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }


}
