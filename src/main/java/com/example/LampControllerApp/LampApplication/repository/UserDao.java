package com.example.LampControllerApp.LampApplication.repository;

import com.example.LampControllerApp.LampApplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
