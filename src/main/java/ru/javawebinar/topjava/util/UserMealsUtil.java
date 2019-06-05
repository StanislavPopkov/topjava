package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
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
        List<UserMealWithExceed> result = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        System.out.println(result);
        List<UserMealWithExceed> resultLamb = getFilteredWithExceededLambd(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        System.out.println(resultLamb);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCalories = new HashMap<>();
        for(UserMeal userMeal: mealList){
            int caloriesUserMeal = mapCalories.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories();
            mapCalories.put(userMeal.getDateTime().toLocalDate(), caloriesUserMeal);
        }
        List<UserMealWithExceed> listExceed = new ArrayList<>();
        for(UserMeal userMeal: mealList){
            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)){
                LocalDate userDate = userMeal.getDateTime().toLocalDate();
                int userCalories = mapCalories.get(userDate);
                if(userCalories <= caloriesPerDay){
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false);
                    listExceed.add(userMealWithExceed);
                }
                else  {
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
                    listExceed.add(userMealWithExceed);
                }
            }
        }
        return listExceed;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededLambd(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCalories = new HashMap<>();
        mealList.forEach((n) -> {
            int caloriesUserMeal = mapCalories.getOrDefault(n.getDateTime().toLocalDate(), 0) + n.getCalories();
            mapCalories.put(n.getDateTime().toLocalDate(), caloriesUserMeal);
        } );
        List<UserMealWithExceed> listExceed2 = new ArrayList<>();
        mealList.forEach((n) -> {
            if(TimeUtil.isBetween(n.getDateTime().toLocalTime(), startTime, endTime)){
            LocalDate userDateLamb = n.getDateTime().toLocalDate();
            int userCaloriesLamb = mapCalories.get(userDateLamb);
            if(userCaloriesLamb <= caloriesPerDay){
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(n.getDateTime(), n.getDescription(), n.getCalories(), false);
                listExceed2.add(userMealWithExceed);
            }
            else  {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(n.getDateTime(), n.getDescription(), n.getCalories(), true);
                listExceed2.add(userMealWithExceed);
            }
        }});
        return listExceed2;
    }
}
