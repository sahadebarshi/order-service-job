package com.infy.cloud.order.job;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.cloud.order.service.UploadToS3;
import com.infy.cloud.order.util.BookOrderDto;
import com.infy.cloud.order.util.CustomGson;
import com.infy.cloud.order.util.aws.AwsClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Slf4j
public class OrderJob implements Job
{
    private static final String AWS_SQS = "https://sqs.ap-south-1.amazonaws.com/654654139776/book-orders";
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      System.out.print("JOB IS EXECUTING HERE......\n");
        BookOrderDto readValue = null;
        FileWriter writer = null;
        String path = System.getProperty("user.dir") + File.separator +"temp" + File.separator;
      ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                .withWaitTimeSeconds(18)
                .withQueueUrl(AWS_SQS)
                .withMaxNumberOfMessages(10);

        List<Message> messages = AwsClientConfig.getSqsClient()
                                                .receiveMessage(receive_request)
                                                .getMessages();
        log.info("MESSAGE SIZE : "+messages.size());
        /*messages.stream().forEach(a-> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                BookOrderDto readValue = mapper.readValue(a.getBody(), BookOrderDto.class);
                //bookOrderDtoList.add(readValue);
            }
            catch (Exception e)
            {
                log.error("Error during mapping string to pojo object with reason " ,e);
            }
            });*/
        if(messages.size()>0) {
            AwsClientConfig.getSqsClient()
                    .changeMessageVisibility(AWS_SQS, messages.get(0).getReceiptHandle(), 120);
            ObjectMapper mapper = new ObjectMapper();
            try {
                readValue = mapper.readValue(messages.get(0).getBody(), BookOrderDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            try {
                File file
                        = new File(path + readValue.getOrderId() + ".txt");
                log.info("FILE PATH : " + file.getPath());
                file.createNewFile();
                writer = new FileWriter(file);
                writer.write(CustomGson.getgSon().toJson(readValue));
                writer.flush();
                writer.close();
                AwsClientConfig.getSqsClient().deleteMessage(AWS_SQS, messages.get(0).getReceiptHandle());
                log.info("DATA DELETED FROM SQS AND SAVED AS txt FILE..");
            }
            catch (Exception e)
            {
                log.error(e.getMessage(), e);
            }

        }
        else {
            log.info("NO MESSAGE AVAILABLE IN SQS..");
        }
        UploadToS3.uploadFile();

    }
}
