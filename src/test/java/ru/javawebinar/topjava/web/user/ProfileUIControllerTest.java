package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.topjava.web.user.ProfileRestController.REST_URL;

class ProfileUIControllerTest extends AbstractControllerTest {

    @Test
    void saveRegister() throws DataIntegrityViolationException {
        User newUser = new User(null, "New", "user@yandex.ru", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        userService.create(newUser);
    }
}