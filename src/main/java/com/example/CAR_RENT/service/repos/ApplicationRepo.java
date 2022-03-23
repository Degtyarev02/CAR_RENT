package com.example.CAR_RENT.service.repos;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ApplicationRepo extends JpaRepository<Application, Long> {

    List<Application> findAllByClient(User user);

    List<Application> findAllByActive(boolean active);

    Application findAllByClientAndActive(User user, boolean active);

    Application findAllByCarAndActive(Car car, boolean active);
}
