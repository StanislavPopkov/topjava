package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> listExceed = new ArrayList<>();
        Map<String, Integer> mapCalories = new HashMap<>();
        for(UserMeal userMeal: mealList){
            int caloriesUserMeal = mapCalories.getOrDefault(userMeal.getDateTime().toLocalDate().toString(), 0) + userMeal.getCalories();
            mapCalories.put(userMeal.getDateTime().toLocalDate().toString(), caloriesUserMeal);
        }
        for(UserMeal userMeal: mealList){
            System.out.println(userMeal.getDateTime().toLocalDate().toString());
            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)){
                String userDate = userMeal.getDateTime().toLocalDate().toString();
                int userCalories = mapCalories.get(userDate);
                if(userCalories <= caloriesPerDay){
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false);
                    listExceed.add(userMealWithExceed);
                }
                else if(userCalories >= caloriesPerDay) {
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
                    listExceed.add(userMealWithExceed);
                }
            }
        }
        return listExceed;
    }
}
