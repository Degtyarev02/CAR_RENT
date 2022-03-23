package com.example.CAR_RENT.service.repos;

import com.example.CAR_RENT.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CarRepo extends JpaRepository<Car, Long> {
    List<Car> findAllByCategory(String category);
}
