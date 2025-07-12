package com.example.LampControllerApp.LampApplication.service;

import com.example.LampControllerApp.LampApplication.DTO.DeviceReportDTO;
import com.example.LampControllerApp.LampApplication.DTO.DeviceUsageReportResponse;
import com.example.LampControllerApp.LampApplication.DTO.DeviceUsageRequest;
import com.example.LampControllerApp.LampApplication.DTO.ReportRequest;
import com.example.LampControllerApp.LampApplication.models.DeviceStaticInfo;
import com.example.LampControllerApp.LampApplication.models.DeviceUsageInfo;
import com.example.LampControllerApp.LampApplication.models.User;
import com.example.LampControllerApp.LampApplication.repository.DeviceStaticInfoDao;
import com.example.LampControllerApp.LampApplication.repository.DeviceUsageInfoDao;
import com.example.LampControllerApp.LampApplication.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LampApplicationService {

    @Value("${esp32.ipaddress}")
    private String ESP32_IP;

    @Autowired
    private DeviceUsageInfoDao deviceUsageInfoDao;

    @Autowired
    private DeviceStaticInfoDao deviceStaticInfoDao;

    @Autowired
    private UserDao userInfoDao;

    private static double generateReportsAndGetTotalCost(Map<String, List<DeviceUsageInfo>> deviceNameToUsageMap, double rate, double totalCost, List<DeviceReportDTO> reportList) {
        for (String deviceName : deviceNameToUsageMap.keySet()) {
            List<DeviceUsageInfo> entries = deviceNameToUsageMap.get(deviceName);
            entries.sort(Comparator.comparing(DeviceUsageInfo::getTimeStamp));

            long totalSeconds = 0;

            for (int i = 0; i < entries.size() - 1; i++) {
                DeviceUsageInfo onDeviceUsageInfo = entries.get(i);
                DeviceUsageInfo offDeviceUsageInfo = entries.get(i + 1);

                LocalDateTime startTime = onDeviceUsageInfo.getTimeStamp();
                LocalDateTime endTime = offDeviceUsageInfo.getTimeStamp();
                totalSeconds += Duration.between(startTime, endTime).getSeconds();
            }

            double usageHours = totalSeconds / 3600.0;
            double power = entries.get(0).getDeviceInfo().getPowerUsage();
            double consumed = usageHours * power;
            double cost = consumed * rate / 1000;
            totalCost += cost;
            totalCost = Math.round(totalCost * 100.0) / 100.0;

            DeviceReportDTO deviceReportDTO = new DeviceReportDTO(deviceName, usageHours, consumed, cost);
            reportList.add(deviceReportDTO);
        }

        return totalCost;
    }

    private static void createDeviceToDeviceUsageMap(List<DeviceUsageInfo> usageInfos, Map<String, List<DeviceUsageInfo>> deviceNameToUsageMap) {
        for (DeviceUsageInfo deviceUsageInfo : usageInfos) {
            String devName = deviceUsageInfo.getDeviceInfo().getDeviceName();
            if (deviceNameToUsageMap.get(devName) == null) {
                List<DeviceUsageInfo> deviceUsageInfos = new ArrayList<>();
                deviceUsageInfos.add(deviceUsageInfo);
                deviceNameToUsageMap.put(devName, deviceUsageInfos);
            } else {
                deviceNameToUsageMap.get(devName).add(deviceUsageInfo);
            }
        }
    }

    //ESP32 fetch and post
    public ResponseEntity<String> addUsageInfo(DeviceUsageRequest request) {
        String action = request.getDeviceCurrentStatus().toLowerCase();
        User userData = userInfoDao.findById((long) request.getUserId()).orElseThrow();
        DeviceStaticInfo device = deviceStaticInfoDao.findById((long) request.getDeviceId()).orElseThrow();

        // Create and save usage log
        DeviceUsageInfo usage = new DeviceUsageInfo();
        usage.setUser(userData);//userData is also local variable
        usage.setDeviceInfo(device);//device is local variable here
        usage.setDeviceCurrentStatus(action);
        usage.setTimeStamp(request.getDateTime());

        deviceUsageInfoDao.save(usage);

        return ResponseEntity.ok("Lamp turned " + action + " and logged successfully");

       /* try
        {
            String action = request.getDeviceCurrentStatus().toLowerCase();
            System.out.println(request.getDeviceCurrentStatus());
            // Validate action
            if (!action.equals("on") && !action.equals("off")) {
                return ResponseEntity.badRequest().body("Invalid action: must be 'on' or 'off'");
            }

           // Send HTTP GET to ESP32
           URL url = new URL(ESP32_IP + "/lamp/" + action);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            // Fetch user and device using IDs

            if(responseCode == HttpStatus.OK.value())
            {
                User userData = userInfoDao.findById((long) request.getUserId()).orElseThrow();
                DeviceStaticInfo device = deviceStaticInfoDao.findById((long) request.getDeviceId()).orElseThrow();

                // Create and save usage log
                DeviceUsageInfo usage = new DeviceUsageInfo();
                usage.setUser(userData);//userData is also local variable
                usage.setDeviceInfo(device);//device is local variable here
                usage.setDeviceCurrentStatus(action);
                usage.setTimeStamp(LocalDateTime.now());

                deviceUsageInfoDao.save(usage);

                return ResponseEntity.ok("Lamp turned " + action + " and logged successfully");
            }
            else
            {
                return ResponseEntity.status(500).body("Error: did not get success from ESP");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }*/
    }

    public DeviceUsageReportResponse generateDeviceUsageReport(ReportRequest request) {
        LocalDateTime start = request.getStartDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().atTime(23, 59, 59);
        double rate = request.getRatePerWatt();
        double totalCost = 0;

        List<DeviceUsageInfo> usageInfos = deviceUsageInfoDao.findByDateRangeForHouse(start, end, request.getHouse().getId());
        List<DeviceReportDTO> reportList = new ArrayList<>();
        Map<String, List<DeviceUsageInfo>> deviceNameToUsageMap = new HashMap<>();

        createDeviceToDeviceUsageMap(usageInfos, deviceNameToUsageMap);

        totalCost = generateReportsAndGetTotalCost(deviceNameToUsageMap, rate, totalCost, reportList);

        return new DeviceUsageReportResponse(reportList, totalCost);

        /*LocalDateTime start = request.getStartDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().plusDays(1).atStartOfDay();
        double rate = request.getRatePerWatt();

        List<DeviceUsageInfo> logs = deviceUsageInfoDao.findByDateRange(start, end);
        List<DeviceReportDTO> reportList = new ArrayList<>();
        Map<String, List<DeviceUsageInfo>> grouped = new HashMap<>();

        for(DeviceUsageInfo deviceUsageInfo : logs)
        {
           String devName = deviceUsageInfo.getDeviceInfo().getDeviceName();
           if(grouped.get(devName) == null)
           {
               grouped.put(devName, new ArrayList<>());
           }
           else
           {
               grouped.get(devName).add(deviceUsageInfo);
           }
        }

        double totalCost = 0;
        for (String deviceName : grouped.keySet())
        {
            List<DeviceUsageInfo> entries = grouped.get(deviceName);
            entries.sort(Comparator.comparing(DeviceUsageInfo::getTimeStamp));
            Map<LocalDate, List<DeviceUsageInfo>> lists = entries.stream().collect(Collectors.groupingBy(t->t.getTimeStamp().toLocalDate()));
            double totalSeconds = 0;
            LocalDateTime currentOn = null;

            for (DeviceUsageInfo log : entries)
            {
                if (log.getDeviceCurrentStatus().equalsIgnoreCase("on"))
                {
                    currentOn = log.getTimeStamp();
                }
                else if (log.getDeviceCurrentStatus().equalsIgnoreCase("off") && currentOn != null) {
                    LocalDateTime dayEnd = currentOn.toLocalDate().atTime(23, 59, 59);
                    LocalDateTime offTime = log.getTimeStamp().isAfter(dayEnd) ? dayEnd : log.getTimeStamp();
                    totalSeconds += Duration.between(currentOn, offTime).getSeconds();
                    currentOn = null;
                }
            }

            double usageHours = totalSeconds / 3600.0;
            double power = entries.get(0).getDeviceInfo().getPowerUsage();
            double consumed = usageHours * power;
            double cost = consumed * rate / 1000;
            totalCost += cost;
            totalCost = Math.round(totalCost * 100.0) / 100.0;

            DeviceReportDTO deviceReportDTO =  new DeviceReportDTO(deviceName, usageHours, consumed, cost);
            reportList.add(deviceReportDTO);
        }

        return new DeviceUsageReportResponse(reportList, totalCost);*/
    }


}












