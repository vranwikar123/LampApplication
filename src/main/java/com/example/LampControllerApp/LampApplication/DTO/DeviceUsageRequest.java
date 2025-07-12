package com.example.LampControllerApp.LampApplication.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DeviceUsageRequest {

    // Getters & Setters
        private int userId;
        private int deviceId;
        private String deviceCurrentStatus;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dateTime;

}


