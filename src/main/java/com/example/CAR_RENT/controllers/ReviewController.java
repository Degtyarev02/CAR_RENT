package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.Review;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.ReviewsRepo;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @Autowired
    CarRepo carRepo;

    @Autowired
    ReviewsRepo reviewsRepo;

    /**
     * Метод для отправки отзывов на автомобиль
     *
     * @param car         автомобиль, для которого пишется отзыв
     * @param currentUser автор отзыва
     * @param reviewtext  текст отзыва
     * @return редирект на страницу машины
     */
    @PostMapping("/review/{car}")
    public String sendReview(@PathVariable Car car, @AuthenticationPrincipal User currentUser, String reviewtext) {
        Review review = new Review();
        review.setText(reviewtext);
        review.setAuthor(currentUser);
        reviewsRepo.save(review);
        car.getReviews().add(review);
        carRepo.save(car);
        return "redirect:/car/" + car.getId();
    }
}
