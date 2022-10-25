package io.github.miwlodar.db;

import io.github.miwlodar.entity.Note;
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
class NotesRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NotesRepository notesRepository;

    @BeforeEach
    void addUser() {
        jdbcTemplate.execute("insert into notes (title, content, owner, created, last_edited) VALUES ('BB Example note', 'Exemplary content of an exemplary note','mike@gmail.com', NOW(), NOW());");
        jdbcTemplate.execute("insert into notes (title, content, owner, created, last_edited) VALUES ('AA Example note', 'Exemplary content of an exemplary note2','johnny@gmail.com', NOW(), NOW());");
    }

    @AfterEach
    void cleanDb() {
        jdbcTemplate.execute("delete from notes;");
    }

    @Test
    @DisplayName("Method findAllByOrderByTitleAsc works properly")
    void findAllOrder() throws Exception {
        List<Note> notes = notesRepository.findAllByOrderByTitleAsc();
        assertEquals(2, notes.size());
        Note retrievedNote = notes.get(0);
        assertEquals("AA Example note", retrievedNote.getTitle());
        assertEquals("Exemplary content of an exemplary note2", retrievedNote.getContent());
        assertEquals("johnny@gmail.com", retrievedNote.getOwner());
        assertNotNull(retrievedNote.getId());
    }

    @Test
    @DisplayName("Method findAllByOwnerOrderByTitleAsc works properly")
    void findAllByOwner() throws Exception {
        List<Note> notes = notesRepository.findAllByOwnerOrderByTitleAsc("mike@gmail.com");
        assertEquals(1, notes.size());
        Note retrievedNote = notes.get(0);
        assertEquals("BB Example note", retrievedNote.getTitle());
        assertEquals("Exemplary content of an exemplary note", retrievedNote.getContent());
        assertEquals("mike@gmail.com", retrievedNote.getOwner());
        assertNotNull(retrievedNote.getId());
    }

    @Test
    @DisplayName("Method findByTitleContainsOrContentContainsAllIgnoreCase works properly")
    void findByContains() throws Exception {
        List<Note> notes = notesRepository.findByTitleContainsOrContentContainsAllIgnoreCase("BB", "BB");
        assertEquals(1, notes.size());
        Note retrievedNote = notes.get(0);
        assertEquals("BB Example note", retrievedNote.getTitle());
        assertEquals("Exemplary content of an exemplary note", retrievedNote.getContent());
        assertEquals("mike@gmail.com", retrievedNote.getOwner());
        assertNotNull(retrievedNote.getId());
    }
}
