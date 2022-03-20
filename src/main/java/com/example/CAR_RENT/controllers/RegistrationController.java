package com.example.CAR_RENT.controllers;


import com.example.CAR_RENT.entity.Role;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.MailSenderService;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {

        //Если пароль у пользователя существует и не равен паролю для подтверждения,
        //то добавляем в модель ошибку
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordEqualsError", "Passwords are not equals");
            return "registration";
        }

        //Получаем пользователя из БД передавая имя нового пользователя
        User userFromDB = userRepo.findByUsername(user.getUsername());
        //Если в бд такой пользователь существует, то выдаем сообщение об ошибке
        if (userFromDB != null) {
            model.addAttribute("message", "User is already exist");
            return "registration";
        }

        //Создаем идентификационный номер для активации аккаунта
        UUID activationCode = UUID.randomUUID();
        //Аккаунту присваиваем статус неактивен (user расширяет userdetails,
        //проверка на активность при входе происходит под капотом)
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBalance(0);
        user.setActivationCode(activationCode.toString());
        userRepo.save(user);

        //Отправляем пользователю на почту ссылку на активацию
        String message = String.format("Привет %s, перейди по ссылке чтобы активировать аккаунт\nhttp://localhost:8080/activate/%d/%s",
                user.getUsername(),
                user.getId(),
                activationCode);
        mailSenderService.send(message, user.getEmail());

        return "redirect:/login";

    }

    //Метод для активации пользователя
    @GetMapping("/activate/{user}/{code}")
    public String validateUser(@PathVariable(name = "user") User user, @PathVariable(name = "code") String code) {
        if (user.getActivationCode().equals(code)) {
            //Если код пользователя равен коду в ссылке, то активируем пользователя и удаляем у него в бд код (на всякий случай)
            user.setActive(true);
            user.setActivationCode(null);
            userRepo.save(user);
        }
        return "login";

    }
}
