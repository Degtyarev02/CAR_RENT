package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.Review;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.CarService;
import com.example.CAR_RENT.service.ReviewService;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    CarService carService;

    @Autowired
    ReviewService reviewService;

    /**
     * Метод для отправки отзывов на автомобиль
     *
     * @param car         автомобиль, для которого пишется отзыв
     * @param currentUser автор отзыва
     * @param review      отзыв, который валидируется согласно модели
     * @return редирект на страницу машины
     */
    @PostMapping("/review/{car}")
    public String sendReview(@PathVariable Car car,
                             @AuthenticationPrincipal User currentUser,
                             @Valid Review review,
                             BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            review.setAuthor(currentUser);
            reviewService.save(review);
            car.getReviews().add(review);
            carService.saveCar(car);
        }
        return "redirect:/car/" + car.getId();
    }
}
