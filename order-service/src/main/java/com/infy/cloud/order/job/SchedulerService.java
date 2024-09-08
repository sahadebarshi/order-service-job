package com.infy.cloud.order.job;

import com.infy.cloud.order.config.OrderConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private final Scheduler scheduler;

    @Autowired
    private final OrderConfig orderConfig;

    public SchedulerService(Scheduler scheduler, OrderConfig orderConfig) {
        this.scheduler = scheduler;
        this.orderConfig = orderConfig;
    }

    @PostConstruct
    public void init()
    {
        try{
            LOG.info("STARTING QUARTZ SCHEDULER.....");
            scheduler.scheduleJob(JobUtils.buildJobDetails(OrderJob.class),
                    JobUtils.tiriggerDetails(orderConfig.jobSchedule));
            scheduler.start();
        }
        catch (SchedulerException e)
        {
           LOG.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDestroy()
    {
        try{
            LOG.info("SHUTTING DOWN QUARTZ SCHEDULER....");
            scheduler.shutdown();
        }
        catch (SchedulerException e)
        {
            LOG.error(e.getMessage(), e);
        }
    }
}
