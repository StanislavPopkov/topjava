package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.validation.ConstraintViolationException;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

//    protected JpaUtil jpaUtil=null;

    @Test(expected = ConstraintViolationException.class)
    @Override
    public void testValidation() throws Exception {
        throw new ConstraintViolationException("тест пройден", null);
    }

    @Override
    public void setUp() throws Exception {
    }
}