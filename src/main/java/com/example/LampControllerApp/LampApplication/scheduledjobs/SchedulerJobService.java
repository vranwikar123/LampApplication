package com.example.LampControllerApp.LampApplication.scheduledjobs;

import com.example.LampControllerApp.LampApplication.models.DeviceUsageInfo;
import com.example.LampControllerApp.LampApplication.repository.DeviceUsageInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class SchedulerJobService {

    @Autowired
    private DeviceUsageInfoDao deviceUsageInfoDao;

    /**
     * @param deviceUsageInfoList
     * @param deviceUsageInfoMap
     */
    private static void createDeviceNameToDeviceUsageMap(List<DeviceUsageInfo> deviceUsageInfoList, Map<String, List<DeviceUsageInfo>> deviceUsageInfoMap) {
        for (DeviceUsageInfo deviceUsageInfo : deviceUsageInfoList) {
            String deviceName = deviceUsageInfo.getDeviceInfo().getDeviceName();
            if (deviceUsageInfoMap.get(deviceName) == null) {
                List<DeviceUsageInfo> deviceUsageInfos = new ArrayList<>();
                deviceUsageInfos.add(deviceUsageInfo);
                deviceUsageInfoMap.put(deviceName, deviceUsageInfos);
            } else {
                List<DeviceUsageInfo> usageInfos = deviceUsageInfoMap.get(deviceName);
                usageInfos.add(deviceUsageInfo);
            }
        }
    }

    /**
     * @param today
     * @param tomorrow
     */
    public void updateDayClosingEntries(LocalDateTime today, LocalDateTime tomorrow) {

        Map<String, List<DeviceUsageInfo>> deviceUsageInfoMap = new HashMap<>();
        List<DeviceUsageInfo> deviceUsageInfoList = deviceUsageInfoDao.findByDateRange(today, tomorrow);
        deviceUsageInfoList.sort(Comparator.comparing(DeviceUsageInfo::getTimeStamp));

        createDeviceNameToDeviceUsageMap(deviceUsageInfoList, deviceUsageInfoMap);
        updateUnmatchedOnEntries(today, tomorrow, deviceUsageInfoMap);

    }

    /**
     * @param today
     * @param tomorrow
     * @param deviceUsageInfoMap
     */
    private void updateUnmatchedOnEntries(LocalDateTime today, LocalDateTime tomorrow, Map<String, List<DeviceUsageInfo>> deviceUsageInfoMap) {

        for (String deviceName : deviceUsageInfoMap.keySet()) {
            //process only if odd entries exist for device, which mean unmatched ON entry
            List<DeviceUsageInfo> deviceUsageInfos = deviceUsageInfoMap.get(deviceName);
            int size = deviceUsageInfoMap.get(deviceName).size();

            if (size % 2 == 1) {
                List<DeviceUsageInfo> toBeSavedObjects = new ArrayList<>();
                DeviceUsageInfo deviceUsageInfo = deviceUsageInfos.get(size - 1);

                DeviceUsageInfo onEntry = new DeviceUsageInfo();
                DeviceUsageInfo offEntry = new DeviceUsageInfo();

                offEntry.setDeviceInfo(deviceUsageInfo.getDeviceInfo());
                offEntry.setUser(deviceUsageInfo.getUser());
                offEntry.setTimeStamp(today.toLocalDate().atTime(23, 59, 59));
                offEntry.setDeviceCurrentStatus("OFF");

                onEntry.setDeviceInfo(deviceUsageInfo.getDeviceInfo());
                onEntry.setUser(deviceUsageInfo.getUser());
                onEntry.setTimeStamp(tomorrow.toLocalDate().atTime(LocalTime.MIN));
                onEntry.setDeviceCurrentStatus("ON");

                toBeSavedObjects.add(offEntry);
                toBeSavedObjects.add(onEntry);

                deviceUsageInfoDao.saveAll(toBeSavedObjects);
            }
        }
    }
}
