package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
@Service
public class MealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceImpl.class);

    private MealRepository repository;
    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Meal meal) {
        log.info("Create meal", meal);
        repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        log.info("Delete meal");
        repository.delete(id);
    }

    @Override
    public MealTo get(int id, int caloriesUser) throws NotFoundException {
        log.info("Get 1 meal");
        Meal meal = repository.get(id);
        checkNotFoundWithId(meal, id);

        if(meal != null){
            List<Meal> meals = new ArrayList<>();
            meals.add(meal);
            return MealsUtil.getFilteredExceededMY(meals, LocalDateTime.MIN,
                    LocalDateTime.MAX, caloriesUser).get(0);
        }
        return null;
    }

    @Override
    public void update(Meal meal) {
        log.info("Update meal", meal);
        Meal mealTest = repository.save(meal);
        if(mealTest == null) throw new NotFoundException("Updating meal not found in repository.");
    }

    @Override

    public List<MealTo> getAll(int userId, int caloriesUser) {
        log.info("GetAll meal", userId, caloriesUser);
        Collection<Meal> allMeals = repository.getAll();
        if(allMeals.isEmpty() != true ){
            List<Meal> listUser = new ArrayList<>();
            for(Meal meal : allMeals){
                if(meal.getUserId() == userId) listUser.add(meal);
            }
            if(!listUser.isEmpty()) {
                return MealsUtil.getFilteredExceededMY(listUser, LocalDateTime.MIN,
                        LocalDateTime.MAX, caloriesUser);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MealTo> getAllFiltered(int userId, int caloriesUser, LocalDateTime start, LocalDateTime end) {
        log.info("GetAll meal", userId, caloriesUser, start, end);
        Collection<Meal> allMeals = repository.getAll();
        if(allMeals.isEmpty() != true ){
            List<Meal> listUser = new ArrayList<>();
            for(Meal meal : allMeals){
                if(meal.getUserId() == userId) listUser.add(meal);
            }
            if(!listUser.isEmpty()) {
                return MealsUtil.getFilteredExceededMY(listUser, start,
                        end, caloriesUser);
            }
        }
        return Collections.emptyList();
    }
}