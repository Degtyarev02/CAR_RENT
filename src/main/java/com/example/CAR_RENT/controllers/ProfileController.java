package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarRepo carRepo;

    @GetMapping
    public String getProfile(@AuthenticationPrincipal User user, Model model) {

        //Находим активную аренду у пользователя
        Application activeApp = applicationRepo.findAllByClientAndActive(user, true);
        //Проверяем, существует ли такая аренда
        if (activeApp != null) {
            //Если срок аренды превышен, закрываем аренду
            if (ChronoUnit.MINUTES.between(activeApp.getStartTime(), LocalDateTime.now()) >= 60) {
                activeApp.setActive(false);
                activeApp.getCar().setInRent(false);
                applicationRepo.save(activeApp);
                carRepo.save(activeApp.getCar());
            }
        }
        List<Application> applicationList = applicationRepo.findAllByClient(user);
        Collections.reverse(applicationList);
        model.addAttribute("applications", applicationList);
        model.addAttribute("currentUser", user);

        return "profile";
    }

    @GetMapping("/edit")
    public String editView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String editInfo(@AuthenticationPrincipal User user, String firstname, String lastname) {
        user.setFirstName(firstname);
        user.setLastName(lastname);
        userRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/balance")
    public String balance(@AuthenticationPrincipal User currentUser, Integer balance) {
        if (balance != null && balance > 0 && balance < 1000000) {
            currentUser.setBalance(currentUser.getBalance() + balance);
            userRepo.save(currentUser);
        }
        return "redirect:/profile";
    }


}
