package com.example.CAR_RENT.service;

import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.service.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarService {

    @Autowired
    CarRepo carRepo;

    public void saveCar(Car car){
        carRepo.save(car);
    }

    public List<Car> findAll(){
        return carRepo.findAll();
    }

    public Car findFirstByOrderByNumberOfRentsDesc() {
        return carRepo.findFirstByOrderByNumberOfRentsDesc();
    }
}
