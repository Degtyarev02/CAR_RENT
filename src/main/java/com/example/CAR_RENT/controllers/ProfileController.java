package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String getProfile(@AuthenticationPrincipal User user, Model model) {

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


}
