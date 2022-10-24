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
        jdbcTemplate.execute("insert into notes (id, title, content, owner, created, last_edited) VALUES (1, 'Example note', 'Exemplary content of an exemplary note','mike@gmail.com', NOW(), NOW());");
    }

    @AfterEach
    void cleanDb() {
        jdbcTemplate.execute("delete from notes;");
    }

    @Test
    @DisplayName("NotesRepository retrieves all notes")
    void findAll() throws Exception {
        List<Note> notes = notesRepository.findAll();
        assertEquals(1, notes.size());
        Note retrievedNote = notes.get(0);
        assertEquals(1, retrievedNote.getId());
        assertEquals("Example note", retrievedNote.getTitle());
        assertEquals("Exemplary content of an exemplary note", retrievedNote.getContent());
        assertEquals("mike@gmail.com", retrievedNote.getOwner());
        assertNotNull(retrievedNote.getId());
    }

    @Test
    @DisplayName("UserRepository retrieves user by ID")
    void findById() throws Exception {
        Note retrievedNote = notesRepository.findById(1L).get();
        assertEquals(1, retrievedNote.getId());
        assertEquals("Example note", retrievedNote.getTitle());
        assertEquals("Exemplary content of an exemplary note", retrievedNote.getContent());
        assertEquals("mike@gmail.com", retrievedNote.getOwner());
        assertNotNull(retrievedNote.getId());
    }
}
