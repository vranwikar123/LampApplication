package com.example.LampControllerApp.LampApplication.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="device_usage_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUsageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_info_id")
    private DeviceStaticInfo deviceInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime timeStamp;

    private String deviceCurrentStatus;


}
