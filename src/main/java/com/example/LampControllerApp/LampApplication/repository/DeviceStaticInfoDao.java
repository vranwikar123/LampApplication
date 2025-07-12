package com.example.LampControllerApp.LampApplication.repository;

import com.example.LampControllerApp.LampApplication.models.DeviceStaticInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStaticInfoDao extends JpaRepository<DeviceStaticInfo, Long>
{
}
