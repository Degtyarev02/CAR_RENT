package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DescriptionController {

    @GetMapping("/description")
    public String description(@AuthenticationPrincipal User currentUser, Model model) {

        model.addAttribute("currentUser", currentUser);
        return "description";
    }

}
