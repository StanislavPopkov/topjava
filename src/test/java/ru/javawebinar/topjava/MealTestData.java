package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTestData {
    private static final DateTimeFormatter OUR_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter OUR_DATE_TIME_FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final Meal mealTest = new Meal(100006, LocalDateTime.parse("2016-07-25 18:10:25",
            OUR_DATE_TIME_FORMATTER), "Завтрак", 1000);

    public static final Meal mealTest2 = new Meal(100006, LocalDateTime.parse("2016-07-25 18:10:25",
            OUR_DATE_TIME_FORMATTER), "ланч", 1200);

    public static DateTimeFormatter getOurDateTimeFormatter() {
        return OUR_DATE_TIME_FORMATTER;
    }

    public static DateTimeFormatter getOurDateTimeFormatterDate() {
        return OUR_DATE_TIME_FORMATTER_DATE;
    }
}
