package com.example.LampControllerApp.LampApplication.DTO;
import com.example.LampControllerApp.LampApplication.models.House;
import com.example.LampControllerApp.LampApplication.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ReportRequest {
    // Getters and setters
        private LocalDate startDate;
        private LocalDate endDate;
        private House house;
        private double ratePerWatt;

}


