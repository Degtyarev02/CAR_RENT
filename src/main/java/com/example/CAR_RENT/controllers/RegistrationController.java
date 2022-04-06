package com.example.CAR_RENT.controllers;


import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.ExceptionService;
import com.example.CAR_RENT.service.MailSenderService;
import com.example.CAR_RENT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        //Если пароль у пользователя существует и не равен паролю для подтверждения,
        //то добавляем в модель ошибку
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordEqualsError", "Пароли не совпадают");
            return "registration";
        }

        //Собираем все ошибки и передаем в модель
        if (bindingResult.hasErrors()) {
            exceptionService.getErrorsFromBindingResult(model, bindingResult);
            return "registration";
        }

        //Получаем пользователя из БД передавая имя нового пользователя
        User userFromDB = userService.findByUsername(user.getUsername());
        //Если в бд такой пользователь существует, то выдаем сообщение об ошибке
        if (userFromDB != null) {
            model.addAttribute("userExistException", "Пользователь с таким ником уже существует");
            return "registration";
        }

        userService.saveNewUser(user);

        //Отправляем пользователю на почту ссылку на активацию
        String message = String.format("Привет %s, перейди по ссылке чтобы активировать аккаунт\n",
                user.getUsername());
        String href = String.format("http://localhost:8080/activate/%d/%s",
                user.getId(),
                user.getActivationCode());
        mailSenderService.send(message, href, user.getEmail(), "Регистрация аккаунта DiamondRent");

        return "redirect:/login";

    }

    @GetMapping("/activate/{user}/{code}")
    public String validateUser(@PathVariable(name = "user") User user, @PathVariable(name = "code") String code) {
        userService.saveValidateUser(user, code);
        return "login";
    }

    @GetMapping("/restore")
    public String restoreView() {
        return "restore_password";
    }

    @PostMapping("/restore")
    public String restorePassword(String username, String password, String password2, Model model) {
        User user = userService.findByUsername(username);
        if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            model.addAttribute("badCredentials", "Поля не могут быть пустыми");
            return "restore_password";
        }
        if (user == null) {
            model.addAttribute("badCredentials", "Невереное имя пользователя");
            return "restore_password";
        }
        if (!password.equals(password2)) {
            model.addAttribute("badCredentials", "Пароли не совпадают");
            return "restore_password";
        }
        UUID activationCode = UUID.randomUUID();
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(false);
        user.setActivationCode(activationCode.toString());
        userService.save(user);

        //Отправляем пользователю на почту ссылку на активацию
        String message = String.format("Привет %s, восстановление пароля прошло успешно, перейди по ссылке, чтобы активировать аккаунт\n",
                user.getUsername());
        String href = String.format("http://localhost:8080/activate/%d/%s",
                user.getId(),
                user.getActivationCode());
        mailSenderService.send(message, href, user.getEmail(), "Восстановление пароля DiamondRent");
        return "redirect:/login";
    }
}
