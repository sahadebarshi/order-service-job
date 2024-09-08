package com.infy.cloud.order.controller;

import com.infy.cloud.order.OrderServiceApplication;
import com.infy.cloud.order.job.SchedulerService;
import com.infy.cloud.order.service.UploadToS3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    SchedulerService schedulerService;

    @GetMapping(value = "/stop", produces = "application/json")
    public String stopJob() {
        schedulerService.preDestroy();
        return "QUARTZ JOB STOPPED....";
    }

    @GetMapping(value = "/start", produces = "application/json")
    public String startJob() {
        OrderServiceApplication.restart();
        return "QUARTZ JOB STARTED....";
    }

    @GetMapping(value = "/upload", produces = "application/json")
    public String testUpload() {
        UploadToS3.uploadFile();
        return "FILE UPLOADED...";
    }
}
