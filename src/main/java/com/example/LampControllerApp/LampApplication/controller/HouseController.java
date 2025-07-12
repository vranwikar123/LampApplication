package com.example.LampControllerApp.LampApplication.controller;

import com.example.LampControllerApp.LampApplication.models.House;
import com.example.LampControllerApp.LampApplication.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lampapp/house")
public class HouseController
{
    @Autowired
    private HouseService houseService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public House addHouseInformation(@RequestBody House house)
    {
        return houseService.addHouseInfo(house);
    }


    //API to add multiple devices data in device info table
    @PostMapping("/add/multipleDevices")
    @ResponseStatus(HttpStatus.CREATED)
    public List<House> addMultipleDevices(@RequestBody List<House> houseList)
    {
        return houseService.addMultipleHouse(houseList);
    }

    //API to get list of all device from device table
    @GetMapping("/all")
    public List<House> getAllHouses()
    {
        return houseService.getAllHouse();
    }
}
