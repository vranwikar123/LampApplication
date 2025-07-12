package com.example.LampControllerApp.LampApplication.scheduledjobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BusinessDayClosingJob
{
    @Autowired
    private SchedulerJobService schedulerJobService;

    @Scheduled(cron = "0 59 23 * * *") // Cron expression to trigger at 11:59 PM every day.
    public void execute()
    {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        schedulerJobService.updateDayClosingEntries(today, tomorrow);
    }
}
