package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class UserServiceDataJPATest extends UserServiceTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void create() throws Exception {
        super.create();
    }

    @Override
    public void duplicateMailCreate() throws Exception {
        super.duplicateMailCreate();
    }

    @Override
    public void delete() throws Exception {
        super.delete();
    }

    @Override
    public void deletedNotFound() throws Exception {
        super.deletedNotFound();
    }

    @Override
    public void get() throws Exception {
        super.get();
    }

    @Override
    public void getNotFound() throws Exception {
        super.getNotFound();
    }

    @Override
    public void getByEmail() throws Exception {
        super.getByEmail();
    }

    @Override
    public void update() throws Exception {
        super.update();
    }

    @Override
    public void getAll() throws Exception {
        super.getAll();
    }
}
