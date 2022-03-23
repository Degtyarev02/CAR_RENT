package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.UserRepo;
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

    @Autowired
    UserRepo userRepo;

    @PostMapping("/rent/{car}")
    public String rentCar(@PathVariable Car car, @AuthenticationPrincipal User user) {
        //Ищем, существует ли активная аренда с текущим пользователем
        Application application = applicationRepo.findAllByClientAndActive(user, true);
        if (application == null) {
            //Проверяем, доступна ли арендуемая машина
            if (!car.isInRent()) {
                //Проверяем, есть ли у пользователя достаточно денег на счету
                if (user.getBalance() - car.getPriceForHour() < 0) {
                    return "redirect:/rent" + car.getId();
                }
                application = new Application();
                application.setActive(true);
                application.setClient(user);
                application.setStartTime(LocalDateTime.now());
                application.setEndTime(LocalDateTime.now().plusHours(1));
                application.setTotalPrice(car.getPriceForHour());
                application.setCar(car);
                car.setInRent(true);
                user.setBalance(user.getBalance() - car.getPriceForHour());
                applicationRepo.save(application);
                carRepo.save(car);
                userRepo.save(user);
            }
        }
        return "redirect:/profile";
    }

    @PostMapping("/rent/extend/{application}")
    public String extendApp(@AuthenticationPrincipal User currentUser, @PathVariable Application application) {
        if (application != null) {
            if (currentUser.getBalance() - application.getCar().getPriceForHour() >= 0) {
                application.setStartTime(LocalDateTime.now());
                application.setEndTime(LocalDateTime.now().plusHours(1));
                currentUser.setBalance(currentUser.getBalance() - application.getCar().getPriceForHour());
                userRepo.save(currentUser);
                applicationRepo.save(application);
            }
        }
        return "redirect:/profile";
    }
}
