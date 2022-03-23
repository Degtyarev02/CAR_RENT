package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Контроллер главной страницы, отображает все доступные автомобили и каждый в отдельности
 */
@Controller
public class MainController {

    @Autowired
    CarRepo carRepo;

    @Autowired
    ReviewsRepo reviewsRepo;

    @GetMapping("/")
    public String greeting(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("american_muscle", carRepo.findAllByCategory("american_muscle"));
        model.addAttribute("sportcar", carRepo.findAllByCategory("sportcar"));
        model.addAttribute("supercar", carRepo.findAllByCategory("supercar"));
        model.addAttribute("currentUser", currentUser);
        return "greeting";
    }

    @GetMapping("/car/{car}")
    public String carView(@PathVariable Car car, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("car", car);
        model.addAttribute("currentUser", user);
        return "carview";
    }

}
