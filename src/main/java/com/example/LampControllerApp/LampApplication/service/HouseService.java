package com.example.LampControllerApp.LampApplication.service;

import com.example.LampControllerApp.LampApplication.models.House;
import com.example.LampControllerApp.LampApplication.models.User;
import com.example.LampControllerApp.LampApplication.repository.HouseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    @Autowired
    private HouseDao houseDao;

    public House addHouseInfo(House house)
    {
        House houseInfo = new House();

        houseInfo.setName(house.getName());
        houseInfo.setAddress(house.getAddress());
        houseInfo.setCity(house.getCity());

        return houseInfo;
    }

    /**
     * TODO: Sarakshi :- this method will not work. Test it
     * @param houseList
     * @return
     */
    public List<House> addMultipleHouse(List<House> houseList)
    {
        return houseDao.saveAll(houseList);
    }

    public List<House> getAllHouse()
    {
        return houseDao.findAll();
    }
}
