package com.example.LampControllerApp.LampApplication.service;

import com.example.LampControllerApp.LampApplication.models.DeviceStaticInfo;
import com.example.LampControllerApp.LampApplication.repository.DeviceStaticInfoDao;
import com.example.LampControllerApp.LampApplication.repository.HouseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceStaticInfoDao deviceStaticInfoDao;

    @Autowired
    private HouseDao houseDao;

    //logic to add device info in device table
    public DeviceStaticInfo addDeviceInfo(DeviceStaticInfo deviceStaticInfo) {
        DeviceStaticInfo staticInfo = new DeviceStaticInfo();

        staticInfo.setDeviceName(deviceStaticInfo.getDeviceName());
        staticInfo.setDeviceCode(deviceStaticInfo.getDeviceCode());
        staticInfo.setPowerUsage(deviceStaticInfo.getPowerUsage());

        staticInfo.setHouse(houseDao.getReferenceById(deviceStaticInfo.getHouse().getId()));
        deviceStaticInfoDao.save(staticInfo);

        return staticInfo;

    }

    //logic to add multiple devices
    public List<DeviceStaticInfo> addMultipleDevices(List<DeviceStaticInfo> deviceList) {
        return deviceStaticInfoDao.saveAll(deviceList);
    }

    //logic to get all devices list
    public List<DeviceStaticInfo> getAllDevices() {
        return deviceStaticInfoDao.findAll();
    }

}
