package io.github.miwlodar.db;

import io.github.miwlodar.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    void addUser() {
        jdbcTemplate.execute("insert into users (username,password,first_name,last_name,email) VALUES ('mickey','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Mike','Smith','mike@gmail.com');");
    }

    @AfterEach
    void cleanDb() {
        jdbcTemplate.execute("delete from users;");
    }

    @Test
    @DisplayName("UsersRepository retrieves all users")
    void findAll() throws Exception {
        List<User> users = usersRepository.findAll();
        assertEquals(1, users.size());
        User retrievedUser = users.get(0);
        assertEquals("mickey", retrievedUser.getUserName());
        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedUser.getPassword());
        assertEquals("Mike", retrievedUser.getFirstName());
        assertEquals("Smith", retrievedUser.getLastName());
        assertEquals("mike@gmail.com", retrievedUser.getEmail());
        assertNotNull(retrievedUser.getId());
    }

    @Test
    @DisplayName("UsersRepository retrieves user by ID")
    void findById() throws Exception {
        User retrievedUser = usersRepository.findById(2L).get();
        assertEquals("mickey", retrievedUser.getUserName());
        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedUser.getPassword());
        assertEquals("Mike", retrievedUser.getFirstName());
        assertEquals("Smith", retrievedUser.getLastName());
        assertEquals("mike@gmail.com", retrievedUser.getEmail());
        assertNotNull(retrievedUser.getId());
    }

    @Test
    @DisplayName("Method findByUserName works properly")
    void findByUserName() throws Exception {
        User retrievedUser = usersRepository.findByUserName("mickey");
        assertEquals("mickey", retrievedUser.getUserName());
        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedUser.getPassword());
        assertEquals("Mike", retrievedUser.getFirstName());
        assertEquals("Smith", retrievedUser.getLastName());
        assertEquals("mike@gmail.com", retrievedUser.getEmail());
        assertNotNull(retrievedUser.getId());
    }
}
