package com.example.LampControllerApp.LampApplication.controller;

import com.example.LampControllerApp.LampApplication.models.DeviceUsageInfo;
import com.example.LampControllerApp.LampApplication.repository.DeviceUsageInfoDao;
import com.example.LampControllerApp.LampApplication.scheduledjobs.SchedulerJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Autowired
    private DeviceUsageInfoDao deviceUsageInfoDao;

    @GetMapping("/code")
    @ResponseBody
    public String test() {

        String day1 = "2025-07-01 00:00:00";
        String day2 = "2025-07-03 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime today = LocalDateTime.parse(day1, formatter);
        LocalDateTime tomorrow = today.plusDays(1);
        List<DeviceUsageInfo> usageInfo = deviceUsageInfoDao.findByDateRangeForHouse(today, tomorrow, 1);

        /*String day1 = "2025-07-01 00:00:00";
        String day2 = "2025-07-03 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime today = LocalDateTime.parse(day1, formatter);
        LocalDateTime tomorrow = today.plusDays(1);*/

        //schedulerJobService.updateDayClosingEntries(today, tomorrow);

        return "Done!";
    }


}
