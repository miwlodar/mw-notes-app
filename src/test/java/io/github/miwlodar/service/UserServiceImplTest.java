package io.github.miwlodar.service;

import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void findByUserName() {
        jdbcTemplate.execute("insert into users (username,password,first_name,last_name,email) VALUES ('mike','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Mike','Smith','mike@gmail.com');");

        User user = userService.findByUserName("mike");
        assertNotNull(user);
    }

    // TODO: add testSave

}