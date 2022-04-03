package com.example.CAR_RENT.service.ScheduledService;

import com.example.CAR_RENT.service.ApplicationService;
import com.example.CAR_RENT.entity.Application;
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
    ApplicationService applicationService;

    @Autowired
    CarRepo carRepo;

    @Scheduled(cron = "0 0/15 * * * *")
    public void checkRent() {
        System.out.println("Scheduled запущен...");
        List<Application> applicationList = applicationService.findAllByActive(true);

        for (Application application : applicationList) {
            if (ChronoUnit.MINUTES.between(application.getStartTime(), LocalDateTime.now()) >= 60) {
                applicationService.closeApplication(application);
                System.out.println("Application " + application.getId() + " - закрыта");
            }
        }

    }
}
