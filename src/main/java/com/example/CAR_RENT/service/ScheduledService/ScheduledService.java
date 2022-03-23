package com.example.CAR_RENT.service.ScheduledService;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarRepo carRepo;

    @Scheduled(cron = "0 0/15 * * * *")
    public void checkRent() {
        System.out.println("Scheduled запущен...");
        List<Application> applicationList = applicationRepo.findAllByActive(true);

        for (Application application : applicationList) {
            if (ChronoUnit.MINUTES.between(application.getStartTime(), LocalDateTime.now()) >= 60) {
                application.setActive(false);
                application.getCar().setInRent(false);
                applicationRepo.save(application);
                carRepo.save(application.getCar());
                System.out.println("Application " + application.getId() + " - закрыта");
            }
        }

    }
}
