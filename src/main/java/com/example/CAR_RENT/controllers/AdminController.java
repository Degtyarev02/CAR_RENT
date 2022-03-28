package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    ApplicationRepo applicationRepo;


    //Написать комментарии!!!!
    @GetMapping("/")
    public String adminPage(Model model, @RequestParam(defaultValue = "") String findingName){
        List<User> users;
        if (findingName.equals("")) {
            users = userRepo.findAll();
            //Иначе найди пользователя по имени
        } else {
            users = userRepo.findAllByUsername(findingName);
        }
        model.addAttribute("users", users);
        model.addAttribute("mostPopularCar", carRepo.findFirstByOrderByNumberOfRentsDesc());
        List<Application> allApplicationForAHalfOfYear =
                applicationRepo.
                findAllByStartTimeBetween(
                        LocalDateTime.now().minusMonths(6),
                        LocalDateTime.now());
        model.addAttribute("allApplicationForAHalfOfYear", allApplicationForAHalfOfYear);

        Integer sumProfit = allApplicationForAHalfOfYear.stream().mapToInt(Application::getTotalPrice).sum();

        model.addAttribute("allSumForAHalfOfYear", sumProfit);
        model.addAttribute("cars", carRepo.findAll());
        return "admin_page";
    }
}
