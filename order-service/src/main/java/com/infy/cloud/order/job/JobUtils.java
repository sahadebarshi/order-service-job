package com.infy.cloud.order.job;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobUtils
{

  public static JobDetail buildJobDetails(final Class jobClass)
  {
      final JobDataMap jobDataMap = new JobDataMap();
      jobDataMap.put("job1", "Order-Job");
      return JobBuilder
              .newJob(jobClass)
              .withIdentity("job1")
              .setJobData(jobDataMap)
              .build();
  }

  public static CronTrigger tiriggerDetails(String jobTiming)
  {
      return   newTrigger()
              .withIdentity("job1", "Order-Job")
              .withSchedule(cronSchedule(jobTiming))
              .build();
  }
}
