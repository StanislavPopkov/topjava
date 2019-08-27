package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.SecurityRequestPostProcessors;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExcess;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;

class MealUIControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/ajax/profile/meals/";

    @Autowired
    private MealService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getWithExcess(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID)
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), MEAL1));
    }


    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID)
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(START_SEQ), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void create() throws Exception {
        Meal created = getCreated();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .param("dateTime",created.getDateTime().toString())
                .param("description", created.getDescription())
                .param("calories", String.valueOf(created.getCalories()))
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())));



        List<Meal> list = service.getAll(START_SEQ);
        LocalDateTime local = created.getDateTime();
        for(Meal meal : list){
            if(meal.getDateTime().isEqual(local)){
                System.out.println("Найдено");
                created.setId(meal.getId());
            }
        }

        assertMatch(service.getAll(START_SEQ), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .param("id", String.valueOf(updated.getId()))
                .param("dateTime",updated.getDateTime().toString())
                .param("description", updated.getDescription())
                .param("calories", String.valueOf(updated.getCalories()))
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())));

        assertMatch(service.get(MEAL1_ID, START_SEQ), updated);
    }


    @Test
    void getBetween() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2015-05-30").param("startTime", "07:00")
                .param("endDate", "2015-05-31").param("endTime", "11:00")
                .with(SecurityRequestPostProcessors.userDetailsService(USER.getEmail())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createWithExcess(MEAL4, true), createWithExcess(MEAL1, false)));
    }


}