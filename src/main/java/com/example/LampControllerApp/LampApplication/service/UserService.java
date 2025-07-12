package com.example.LampControllerApp.LampApplication.service;

import com.example.LampControllerApp.LampApplication.models.User;
import com.example.LampControllerApp.LampApplication.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserDao userInfoDao;

    //logic to add user info in user table
    public User addUserInfo(User userInfo)
    {
        User user = new User();

        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        userInfoDao.save(user);

        return user;

    }

    //logic to add multiple users
    public List<User> addMultipleUsers(List<User> userList)
    {
        return userInfoDao.saveAll(userList);
    }

    //logic to get list of all users
    public List<User> getAllUsers()
    {
        return userInfoDao.findAll();
    }
}
