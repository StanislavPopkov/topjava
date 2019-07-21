package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        if(repository.isEmpty() == false) {
            List<User> users = new ArrayList<>(repository.values());
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            }.thenComparing(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getRegistered().compareTo(o2.getRegistered());
                }
            }));
            return users;
        }
        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        for (Map.Entry<Integer, User> pair : repository.entrySet()) {
            if(pair.getValue().getEmail().equals(email)) return pair.getValue();
        }

//        repository.forEach((k, v) ->{
//            if(v.getEmail().equals(email));
//        });
        return null;
    }
}
