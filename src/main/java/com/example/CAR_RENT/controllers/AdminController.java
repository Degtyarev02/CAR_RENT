package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.Review;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.ApplicationService;
import com.example.CAR_RENT.service.CarService;
import com.example.CAR_RENT.service.ReviewService;
import com.example.CAR_RENT.service.UserService;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ReviewService reviewService;

    /**
     * Контроллер, который отвечает за панель администрации
     *
     * @param model       модель, для передачи объектов во вью
     * @param findingName параметр, который необходим для фильтрации пользователей
     * @param currentUser текущий пользователь
     * @return страница админа
     */
    @GetMapping("/")
    public String adminPage(Model model, @RequestParam(defaultValue = "") String findingName, @AuthenticationPrincipal User currentUser) {
        List<User> users;
        //Если параметры не передавались, то найти всех пользователей
        if (findingName.equals("")) {
            users = userService.findAll();
            //Иначе найди пользователя по имени
        } else {
            users = userService.findAllByUsername(findingName);
        }
        model.addAttribute("users", users);

        //В модель добавляется автомобиль, с самым большим количеством аренд
        model.addAttribute("mostPopularCar", carService.findFirstByOrderByNumberOfRentsDesc());

        //В модель добавляются все заявки за последнии пол года
        List<Application> allApplicationForAHalfOfYear =
                applicationService.
                        findAllByStartTimeBetween(
                                LocalDateTime.now().minusMonths(6),
                                LocalDateTime.now());
        model.addAttribute("allApplicationForAHalfOfYear", allApplicationForAHalfOfYear);

        //Переменная, которая хранит в себе сумму денег по всем арендам за пол года
        Integer sumProfit = allApplicationForAHalfOfYear.stream().mapToInt(Application::getTotalPrice).sum();

        model.addAttribute("allSumForAHalfOfYear", sumProfit);
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("currentUser", currentUser);
        return "admin_page";
    }

    /**
     * Метод, который доступен только админу. Удаляет выбранный отзыв
     *
     * @param review  удадяемый отзыв
     * @param referer ссылка, откуда был произведен запрос (необходима для редиректа)
     * @param car     автомобиль, у которого удаляется отзыв
     * @return редирект на страницу, откуда был произведен запрос
     */
    @PostMapping("/delete/{car}/{review}")
    public String deleteReview(@PathVariable Review review, @RequestHeader(required = false) String referer, @PathVariable Car car) {
        reviewService.removeReview(review, car);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        return "redirect:" + components.getPath();
    }

    @PostMapping("/activity/{user}")
    public String changeUserAccountActivity(@PathVariable @NotNull User user) {
        userService.changeActivity(user);
        return "redirect:/admin/";
    }
}
