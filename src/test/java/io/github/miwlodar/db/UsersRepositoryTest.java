package io.github.miwlodar.db;

import io.github.miwlodar.entity.User;
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

    @Test
    void testExample() throws Exception { // testFindAll
        assertEquals(0, usersRepository.count());

//        User user = new User();
//        user.setId(300L);
//        user.setUserName("mike");
//        user.setPassword("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su");
//        user.setFirstName("Mike");
//        user.setLastName("Smith");
//        user.setEmail("mike@gmail.com");
//        usersRepository.save(user);
        jdbcTemplate.execute("insert into users (username,password,first_name,last_name,email) VALUES ('eh','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Mike','Smith','mike@gmail.com');");
        // ^ before all

        List<User> users = usersRepository.findAll();
        assertEquals(1, users.size());
        User retrievedUser = users.get(0);
        assertEquals("eh", retrievedUser.getUserName());
        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedUser.getPassword());
        assertEquals("Mike", retrievedUser.getFirstName());
        assertEquals("Smith", retrievedUser.getLastName());
        assertEquals("mike@gmail.com", retrievedUser.getEmail());
        assertNotNull(retrievedUser.getId());
    }

    // add testFindByUserName - bo find all juz jest powyzej :))

}