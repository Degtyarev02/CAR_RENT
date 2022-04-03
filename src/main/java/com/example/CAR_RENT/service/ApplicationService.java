package com.example.CAR_RENT.service;


import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.CarService;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ApplicationService {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    public List<Application> findAllByStartTimeBetween(LocalDateTime minusMonths, LocalDateTime now) {
        return findAllByStartTimeBetween(minusMonths, now);
    }

    public Application findAllByClientAndActive(User user, boolean b) {
        return applicationRepo.findAllByClientAndActive(user, b);
    }

    public void save(Application activeApp) {
        applicationRepo.save(activeApp);
    }

    public List<Application> findAllByClient(User user) {
        return applicationRepo.findAllByClient(user);
    }

    public void closeApplication(Application activeApp) {
        activeApp.setActive(false);
        activeApp.getCar().setInRent(false);
        save(activeApp);
        carService.saveCar(activeApp.getCar());
    }

    public List<Application> findAllByActive(boolean b) {
        return applicationRepo.findAllByActive(b);
    }

    //Метод для создания новой заявки на аренду
    public void saveNewApplication(Application application, User user, Car car) {
        application = new Application();
        application.setActive(true);
        application.setClient(user);
        application.setStartTime(LocalDateTime.now());
        application.setEndTime(LocalDateTime.now().plusHours(1));
        application.setTotalPrice(car.getPriceForHour());
        application.setCar(car);
        car.setInRent(true);
        car.setNumberOfRents(car.getNumberOfRents() + 1);
        user.setBalance(user.getBalance() - car.getPriceForHour());
        save(application);
        carService.saveCar(car);
        userService.save(user);
    }
}
