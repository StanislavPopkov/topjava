package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);
    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        List<MealTo> mealList = MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealList);
        return "meals";
    }

    @GetMapping("/meals/delete")
    public String delete(@RequestParam(value="id", required=true) Integer id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }


    @GetMapping("/meals/create")
    public String createGet(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("create", "create");
        return "mealForm";
    }
    @PostMapping("/meals/create")
    public String createPost(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        service.create(meal, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/update")
    public String updateGet(@RequestParam(value="id") Integer id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/meals/update")
    public String updatePost(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(Integer.parseInt(request.getParameter("id")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/filter")
    public String filter(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        //версия с полным LocalDateTime. В отличии от реализации в оригинале, исправляет фичу фильтрации только по LocalTime
        //в MealsUtil в которой мы могли взять время 00:01 - 01:00 и не получить ни одной фильтрованной MealTo, хотя
        //Localdate попадает в диапазон.
        LocalDateTime startDateTime = LocalDateTime.parse((request.getParameter("startDate")+" "
                +request.getParameter("startTime")), DateTimeUtil.DATE_TIME_FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse((request.getParameter("endDate")+" "
                +request.getParameter("endTime")), DateTimeUtil.DATE_TIME_FORMATTER) ;

        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        List<MealTo> mealList = MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startDateTime, endDateTime);
        model.addAttribute("meals", mealList);
        return "meals";
    }
}
