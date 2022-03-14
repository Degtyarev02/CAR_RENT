package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.service.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    CarRepo carRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("american_muscle", carRepo.findAllByCategory("american_muscle"));
        return "greeting";
    }


}
