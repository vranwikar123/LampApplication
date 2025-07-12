package com.example.LampControllerApp.LampApplication.DTO;

import lombok.Getter;

@Getter
public class DeviceReportDTO {

    // Getters
    private final String deviceName;
        private final double usageHours;
        private final double powerConsumed;
        private final double cost;

        public DeviceReportDTO(String deviceName, double usageHours, double powerConsumed, double cost) {
            this.deviceName = deviceName;
            this.usageHours = usageHours;
            this.powerConsumed = powerConsumed;
            this.cost = cost;
        }

}


