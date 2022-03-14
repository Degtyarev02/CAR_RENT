package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String validateLoginForm(Model model, User user) {
        User finduser = userRepo.findByUsername(user.getUsername());
        if (finduser != null) {
            return "greeting";
        } else {
            return "redirect:/login";
        }
    }

}
