package com.example.LampControllerApp.LampApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="device_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStaticInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceName;
    private String deviceCode;
    private Integer powerUsage; // rupees per watts

    @OneToMany(mappedBy = "deviceInfo")
    private List<DeviceUsageInfo> deviceUsageInfos = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "house_id")
    private House house;

}
