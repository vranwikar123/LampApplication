package com.example.LampControllerApp.LampApplication.controller;

import com.example.LampControllerApp.LampApplication.models.User;
import com.example.LampControllerApp.LampApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lampapp/user")
public class UserController
{
    @Autowired
    private UserService userService;

    //API to add user info in user table
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUserInformation(@RequestBody User userInfo)
    {
        return userService.addUserInfo(userInfo);
    }

    //API to add multiple users in user table
    @PostMapping("/add/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> addMultipleUsers(@RequestBody List<User> userList)
    {
        return userService.addMultipleUsers(userList);
    }

    //API to get all user list from user table
    @GetMapping("/user/all")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

}
