package com.infy.cloud.order.config;

import com.infy.cloud.order.controller.JobController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yaml")
public class OrderConfig {
    @Value("${spring.application.name}")
    public String name;

    @Value("${jobs.orderJob}")
    public String jobSchedule;

    @Value("${proxy.port:null}")
    public int proxyPort;

    @Bean
    public JobController jobController(){
        return new JobController();
    }

}
