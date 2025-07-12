package com.example.LampControllerApp.LampApplication.controller;

import com.example.LampControllerApp.LampApplication.DTO.DeviceUsageReportResponse;
import com.example.LampControllerApp.LampApplication.models.DeviceStaticInfo;
import com.example.LampControllerApp.LampApplication.models.DeviceUsageInfo;
import com.example.LampControllerApp.LampApplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.LampControllerApp.LampApplication.service.LampApplicationService;
import java.util.List;
import com.example.LampControllerApp.LampApplication.DTO.DeviceUsageRequest;
import com.example.LampControllerApp.LampApplication.DTO.ReportRequest;
import com.example.LampControllerApp.LampApplication.DTO.DeviceReportDTO;





@RestController
@RequestMapping("/lampapp")
public class LampApplicationController {

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String addDeviceTest() {
        return "Testing Application";
    }


    @Autowired
    private LampApplicationService lampApplicationService;

    //API to add device info in device static table

    //API to give command to Esp32 and also to store data in database
    @CrossOrigin(origins = "*")
    @PostMapping("/usage/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUsageInformation(@RequestBody DeviceUsageRequest request) {
        return lampApplicationService.addUsageInfo(request);
    }


    @PostMapping("/report/generate")
    public DeviceUsageReportResponse generateReport(@RequestBody ReportRequest request) {
        return lampApplicationService.generateDeviceUsageReport(request);
    }
}







