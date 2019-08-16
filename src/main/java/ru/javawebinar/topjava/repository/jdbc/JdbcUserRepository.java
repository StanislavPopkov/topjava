package ru.javawebinar.topjava.repository.jdbc;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final ResultSetExtractor<List<User>> resultSetExtractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id") // the column name you expect the user id to be on
                    .newResultSetExtractor(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            Set<Role> roleSet = user.getRoles();
            for(Role role: roleSet){
                jdbcTemplate.update("INSERT INTO user_roles(role, user_id) VALUES(?, ?)", role.toString(), newKey);
            }

        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
            Set<Role> roleSet = user.getRoles();
            int id = user.getId();
            for(Role role: roleSet){
                jdbcTemplate.update("UPDATE user_roles SET role=? WHERE user_id=?", role.toString(), id);
            }
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.calories_per_day, u.enabled, u.registered,"
                +" ur.role FROM users AS u INNER JOIN user_roles AS ur ON u.id = ur.user_id WHERE u.id=?;", resultSetExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        //List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.calories_per_day, u.enabled, u.registered,"
                +" ur.role FROM users AS u INNER JOIN user_roles AS ur ON u.id = ur.user_id WHERE u.email=?;", resultSetExtractor, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.calories_per_day, u.enabled, u.registered,"
                +" ur.role FROM users AS u INNER JOIN user_roles AS ur ON u.id = ur.user_id ORDER BY name, email", resultSetExtractor);
    }
}
