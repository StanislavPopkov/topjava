package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll Meal");

        int userId = SecurityUtil.authUserId();
        int caloriesUser = SecurityUtil.authUserCaloriesPerDay();
        return service.getAll(userId, caloriesUser);
    }

    public List<MealTo> getAll(String startDate, String endDate, String startTime, String endTime ) {
        log.info("getAll Meal with filter");
        int userId = SecurityUtil.authUserId();
        int caloriesUser = SecurityUtil.authUserCaloriesPerDay();
        if(startDate == null && startDate.equals("") && endDate == null && endDate.equals("") && startTime == null &&
                startTime.equals("") && endTime == null && endTime.equals("")){
                    return service.getAll(userId, caloriesUser);
        }
        StringBuffer start = new StringBuffer(startDate).append("").append(startTime);
        StringBuffer end = new StringBuffer(endDate).append("").append(endTime);
        LocalDateTime startLocalDateTime = LocalDateTime.parse(start, DateTimeUtil.getDateTimeFormatter());
        LocalDateTime endLocalDateTime = LocalDateTime.parse(end, DateTimeUtil.getDateTimeFormatter());
        return service.getAllFiltered(userId, caloriesUser, startLocalDateTime, endLocalDateTime);
    }

    public MealTo get(int id) {
        log.info("get {} Meal", id);
        if(id != SecurityUtil.authUserId()){
            throw new NotFoundException("Not match userId with MealId " + id);
        }
        int caloriesUser = SecurityUtil.authUserCaloriesPerDay();
        return service.get(id, caloriesUser);
    }

    public void create(Meal meal) {
        log.info("create {} Meal", meal);
        checkNew(meal);
        meal.setUserId(SecurityUtil.authUserId());
        service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {} Meal", id);
        if(id != SecurityUtil.authUserId()){
            throw new NotFoundException("Not match userId with MealId " + id);
        }
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

}