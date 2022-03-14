package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class RentController {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarRepo carRepo;

    @PostMapping("/rent/{car}")
    public String rentCar(@PathVariable Car car, @AuthenticationPrincipal User user){
        Application application = applicationRepo.findAllByClientAndActive(user, true);
        if(application == null){
            application = new Application();
            application.setActive(true);
            application.setClient(user);
            application.setStartTime(LocalDateTime.now());
            application.setEndTime(LocalDateTime.now().plusHours(1));
            application.setTotalPrice(car.getPriceForHour());
            application.setCar(car);
            car.setInRent(true);
            applicationRepo.save(application);
            carRepo.save(car);

        }
        return "redirect:/";
    }
}
