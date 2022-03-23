package com.example.CAR_RENT.service.repos;

import com.example.CAR_RENT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ReviewsRepo extends JpaRepository<Review, Long> {
}
