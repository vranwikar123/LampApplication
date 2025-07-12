package com.example.LampControllerApp.LampApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeviceUsageReportResponse {
    private List<DeviceReportDTO> report;
    private double totalCost;
}

