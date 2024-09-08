package com.infy.cloud.order.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.infy.cloud.order.util.aws.AwsS3ClientConfig;
import lombok.extern.slf4j.Slf4j;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
public class UploadToS3 {

    public static void uploadFile() {
        String path = System.getProperty("user.dir") + File.separator + "temp" + File.separator;
        log.info("FILE PATH: "+path);
        AmazonS3 amazonS3Client = AwsS3ClientConfig.getS3Client();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.addUserMetadata("User1", "Demo");
        File file = new File(path);
        String bucketName = "book-store-bucket";
        /*if(amazonS3Client.doesBucketExist(bucketName)) {
            //log.info("BUCKET LIST: \n"+amazonS3Client.listBuckets());
        }*/
        synchronized (file)
        {
            if(file.listFiles().length > 0)
            {
                try {
                    String fileName = Arrays.stream(file.listFiles()).findFirst().get().getName();
                    File accessFile = new File(path+fileName);
                    FileInputStream input = new FileInputStream(accessFile);
                    PutObjectRequest request = new PutObjectRequest(bucketName, "orders/" + fileName, input, objectMetadata);
                    amazonS3Client.putObject(request);
                    log.info("FILE UPLODED TO BUCKET: "+bucketName);
                    accessFile.delete();
                    log.info("FILE "+fileName +" DELETED FROM DIR");
                }
                catch (Exception e)
                {
                    log.error(e.getMessage() , e);
                }
            }
            else{
                log.info("FILE DOES NOT EXIST IN : "+path);
            }

        }

        //System.out.println("NUMBER OF FILES------> :" + Path.of(file.toURI());

    }
}
