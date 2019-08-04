package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class AdminRestController extends AbstractUserController {

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(User user, int id) {
        super.update(user, id);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }
}