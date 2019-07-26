package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/dataMealsDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100006, UserTestData.USER_ID);
        assertThat(meal).isEqualTo(MealTestData.mealTest);

    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(100001, 100000);


    }

    @Test
    public void getBetweenDates() {
        List<Meal> listMeal = service.getBetweenDates(LocalDate.parse("2016-06-21",
                MealTestData.getOurDateTimeFormatterDate()), LocalDate.parse("2016-07-25", MealTestData.getOurDateTimeFormatterDate()
                ), 100001);
        System.out.println(listMeal.size());
        assertThat(listMeal).isNotEmpty();
    }


    @Test
    public void getAll() {
        List<Meal> listMeal = service.getAll(100001);
        assertThat(listMeal).isNotEmpty();
    }

    @Test
    public void update() {
        service.update(MealTestData.mealTest2, 100005);


    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.now(), "Тайм брекфаст", 700);
        Meal mealCreated = service.create(meal, SecurityUtil.authUserId());
        System.out.println(mealCreated);
        assertThat(meal).isEqualTo(mealCreated);

    }
}