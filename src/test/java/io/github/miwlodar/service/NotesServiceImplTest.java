package io.github.miwlodar.service;

import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotesServiceImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NotesService notesService;

    @Autowired
    private UsersRepository usersRepository;

//    @BeforeEach
//    public void createTestUser() {
//        jdbcTemplate.execute("insert into users (username,password,first_name,last_name,email) VALUES ('johnny','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','John','Bravo','johnny@gmail.com');");
//
//        List<User> users = usersRepository.findAll();
//        User retrievedUser = users.get(0);
//
//        jdbcTemplate.execute("insert into users_roles (user_id,role_id) VALUES (retrievedUser, 1);");
//    }

//        List<User> users = usersRepository.findAll();
//        assertEquals(1, users.size());
//        User retrievedUser = users.get(0);
//        assertEquals("eh", retrievedUser.getUserName());
//        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedUser.getPassword());
//        assertEquals("Mike", retrievedUser.getFirstName());
//        assertEquals("Smith", retrievedUser.getLastName());
//        assertEquals("mike@gmail.com", retrievedUser.getEmail());
//        assertNotNull(retrievedUser.getId());
//
//        INSERT INTO `users_roles` (user_id,role_id)
//        VALUES
//                (1, 2),

    @Test
//    @WithMockUser(username = "johnny", roles = {"ROLE_USER", "ROLE_ADMIN"})
    public void testFindAll() { // aby ten test zadzialal trzeba jeszcze zrobic usera "olaf" o emailu marty@gmail.com oraz role dla niego
        jdbcTemplate.execute("INSERT INTO notes (title, content, owner, created, last_edited) VALUES ('Lorem ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit','johnny@gmail.com', NOW(), NOW());");

        List<Note> notes = notesService.findAll();
        assertEquals(1, notes.size());
    }
}