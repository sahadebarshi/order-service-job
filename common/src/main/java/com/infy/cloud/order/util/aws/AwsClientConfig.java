package com.infy.cloud.order.util.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AwsClientConfig {

    public static AmazonSQS getSqsClient() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAZQ3DNNWAC4WOEXOE",
                "47kwILMxZ/dKyzK1xDnmIuCNMc0hT0MEmFbZ0t3W"
        );

         return AmazonSQSClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
