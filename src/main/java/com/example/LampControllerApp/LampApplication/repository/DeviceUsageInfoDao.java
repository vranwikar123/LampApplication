package com.example.LampControllerApp.LampApplication.repository;

import com.example.LampControllerApp.LampApplication.models.DeviceUsageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceUsageInfoDao extends JpaRepository<DeviceUsageInfo, Long> {

    @Query("SELECT d FROM DeviceUsageInfo d WHERE d.timeStamp BETWEEN :start AND :end")
    List<DeviceUsageInfo> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT du FROM DeviceUsageInfo du JOIN du.deviceInfo dInfo JOIN dInfo.house hs WHERE hs.id = :houseId AND du.timeStamp BETWEEN :start AND :end")
    List<DeviceUsageInfo> findByDateRangeForHouse(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("houseId") long houseId);
}