package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {

    void create(Meal meal);

    void delete(int id) throws NotFoundException;

    MealTo get(int id, int caloriesUser) throws NotFoundException;

    void update(Meal meal);

    List<MealTo> getAll(int userId, int caloriesUser);

    List<MealTo> getAllFiltered(int userId, int caloriesUser, LocalDateTime start, LocalDateTime end);
}