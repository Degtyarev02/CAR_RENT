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

/**
 * Контроллер для профиля пользователя
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarRepo carRepo;


    /**
     * Метод, который возвращает вью для пользователя и одновременно проверяет активные аренды
     *
     * @param user  текущий пользователь
     * @param model модель, для передачи данных на фронт
     * @return возвращает страницу пользователя
     */
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
        //В список кладем все аренды пользователя и разворачиваем список для отображения по убыванию даты
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

    /**
     * Метод для добавления денег на счет
     *
     * @param currentUser текущий пользователь
     * @param balance     баланс, который необходимо добавить
     * @return редирект на профиль
     */
    @PostMapping("/balance")
    public String balance(@AuthenticationPrincipal User currentUser, Integer balance) {

        //Если баланс указан верно, то добавить пользователю на счет
        if (balance != null && balance > 0 && balance < 1000000) {
            currentUser.setBalance(currentUser.getBalance() + balance);
            userRepo.save(currentUser);
        }
        return "redirect:/profile";
    }


}
