//package io.github.miwlodar.db;
//
//import io.github.miwlodar.entity.Note;
//import io.github.miwlodar.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//class NotesRepositoryTest {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private NotesRepository notesRepository;
//
//    @BeforeEach
//    void addUser() {
//        jdbcTemplate.execute("insert into notes (username,password,first_name,last_name,email) VALUES ('eh','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Mike','Smith','mike@gmail.com');");
//    }
//
//    @Test
//    void repositoryWorksProperly() throws Exception { // testFindAll
//        assertEquals(0, notesRepository.count());
//
////        User user = new User();
////        user.setId(300L);
////        user.setUserName("mike");
////        user.setPassword("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su");
////        user.setFirstName("Mike");
////        user.setLastName("Smith");
////        user.setEmail("mike@gmail.com");
////        usersRepository.save(user);
//        // ^ before all
//
//        List<Note> notes = notesRepository.findAll();
//        assertEquals(1, notes.size());
//        Note retrievedNote = notes.get(0);
//        assertEquals("eh", retrievedNote.getUserName());
//        assertEquals("$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su", retrievedNote.getPassword());
//        assertEquals("Mike", retrievedNote.getFirstName());
//        assertEquals("Smith", retrievedNote.getLastName());
//        assertEquals("mike@gmail.com", retrievedNote.getEmail());
//        assertNotNull(retrievedNote.getId());
//    }
//
////    @Test
//
//    // add testFindByUserName - bo find all juz jest powyzej :))
//}