package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.ApplicationService;
import com.example.CAR_RENT.service.CarService;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

/**
 * Контроллер для создания аренды
 */

@Controller
public class RentController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    UserRepo userRepo;

    /**
     * Метод создает новую аренду, (или проделвает текущую) проверяя дотаточно ли у пользователя денег на счету и не занята ли машина
     *
     * @param car  - машина, которую арендует пользователь
     * @param user - пользователь
     * @return редирект на страницу с машиной в случае безуспешной аренды, иначе редирект на профиль
     */
    @PostMapping("/rent/{car}")
    public String rentCar(@PathVariable Car car, @AuthenticationPrincipal User user, Model model) {
        //Ищем, существует ли активная аренда с текущим пользователем
        Application application = applicationService.findAllByClientAndActive(user, true);
        if (application == null) {
            //Проверяем, доступна ли арендуемая машина
            if (!car.isInRent()) {
                //Проверяем, есть ли у пользователя достаточно денег на счету
                if (user.getBalance() - car.getPriceForHour() < 0) {
                    model.addAttribute("car", car);
                    model.addAttribute("currentUser", user);
                    model.addAttribute("balanceError", "Недостаточно средств на счету");
                    return "carview";
                }
                //Создаем новую аренду
                applicationService.saveNewApplication(application, user, car);
            }
        }
        return "redirect:/profile";
    }
}
