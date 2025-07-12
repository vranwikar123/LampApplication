package com.example.LampControllerApp.LampApplication.controller;

import com.example.LampControllerApp.LampApplication.models.DeviceStaticInfo;
import com.example.LampControllerApp.LampApplication.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lampapp/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceStaticInfo addDeviceInformation(@RequestBody DeviceStaticInfo deviceStaticInfo) {
        return deviceService.addDeviceInfo(deviceStaticInfo);
    }


    //API to add multiple devices data in device info table
    @PostMapping("/add/multipleDevices")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DeviceStaticInfo> addMultipleDevices(@RequestBody List<DeviceStaticInfo> deviceList) {
        return deviceService.addMultipleDevices(deviceList);
    }

    //API to get list of all device from device table
    @GetMapping("/all")
    public List<DeviceStaticInfo> getAllDevices() {
        return deviceService.getAllDevices();
    }
}
