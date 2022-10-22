package io.github.miwlodar.service;

import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.User;
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

    @Test
    @WithMockUser(username = "olaf", roles = {"ROLE_USER", "ROLE_ADMIN"})
    public void testFindAll() { // aby ten test zadzialal trzeba jeszcze zrobic usera "olaf" o emailu marty@gmail.com oraz role dla niego
        jdbcTemplate.execute("INSERT INTO notes (title, content, owner, created, last_edited) VALUES ('Lorem ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit','marty@gmail.com', NOW(), NOW());");

        List<Note> notes = notesService.findAll();
        assertEquals(1, notes.size());
    }

}