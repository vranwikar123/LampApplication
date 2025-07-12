package com.example.LampControllerApp.LampApplication.repository;

import com.example.LampControllerApp.LampApplication.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseDao extends JpaRepository<House, Long> {

}
