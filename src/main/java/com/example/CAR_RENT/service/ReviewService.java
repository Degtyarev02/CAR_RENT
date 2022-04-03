package com.example.CAR_RENT.service;

import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.Review;
import com.example.CAR_RENT.service.repos.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReviewService {

    @Autowired
    CarService carService;

    @Autowired
    ReviewsRepo reviewsRepo;


    public void removeReview(Review review, Car car){
        car.getReviews().remove(review);
        review.setAuthor(null);
        carService.saveCar(car);
        reviewsRepo.delete(review);
    }

    public void save(Review review) {
        reviewsRepo.save(review);
    }
}
