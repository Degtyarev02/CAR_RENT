package com.example.CAR_RENT.controllers;

import com.example.CAR_RENT.entity.Application;
import com.example.CAR_RENT.entity.Car;
import com.example.CAR_RENT.entity.Review;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.ApplicationRepo;
import com.example.CAR_RENT.service.repos.CarRepo;
import com.example.CAR_RENT.service.repos.ReviewsRepo;
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
    UserRepo userRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    ReviewsRepo reviewsRepo;

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
            users = userRepo.findAll();
            //Иначе найди пользователя по имени
        } else {
            users = userRepo.findAllByUsername(findingName);
        }
        model.addAttribute("users", users);

        //В модель добавляется автомобиль, с самым большим количеством аренд
        model.addAttribute("mostPopularCar", carRepo.findFirstByOrderByNumberOfRentsDesc());

        //В модель добавляются все заявки за последнии пол года
        List<Application> allApplicationForAHalfOfYear =
                applicationRepo.
                        findAllByStartTimeBetween(
                                LocalDateTime.now().minusMonths(6),
                                LocalDateTime.now());
        model.addAttribute("allApplicationForAHalfOfYear", allApplicationForAHalfOfYear);

        //Переменная, которая хранит в себе сумму денег по всем арендам за пол года
        Integer sumProfit = allApplicationForAHalfOfYear.stream().mapToInt(Application::getTotalPrice).sum();

        model.addAttribute("allSumForAHalfOfYear", sumProfit);
        model.addAttribute("cars", carRepo.findAll());
        model.addAttribute("currentUser", currentUser);
        return "admin_page";
    }

    @PostMapping("/delete/{car}/{review}")
    public String deleteReview(@PathVariable Review review, @RequestHeader(required = false) String referer, @PathVariable Car car) {
        car.getReviews().remove(review);
        review.setAuthor(null);
        carRepo.save(car);
        reviewsRepo.delete(review);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        return "redirect:" + components.getPath();
    }

    /**
     * Метод, который меняет статус аккаунта пользователя
     *
     * @param user пользователь, которому необходимо включить или отключить аккаунт
     * @return редирект на панель админа
     */
    @PostMapping("/activity/{user}")
    public String changeUserAccountActivity(@PathVariable @NotNull User user) {
        user.setActive(!user.isActive());
        userRepo.save(user);
        return "redirect:/admin/";
    }
}
