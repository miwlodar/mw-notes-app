package io.github.miwlodar.service;

import io.github.miwlodar.db.NotesRepository;
import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class NotesServiceImplTest {

    @Autowired
    private NotesService notesService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private NotesRepository notesRepository;

    @BeforeEach
    public void addingUsersAndNotes() {

        //creating 2 users - 1 with role ADMIN and 1 with role USER
        User user = new User();
        User userAdmin = new User();

        user.setId(1L);
        user.setUserName("johnny");
        user.setFirstName("John");
        user.setLastName("Bravo");
        user.setPassword("'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'");
        user.setEmail("johnny@gmail.com");
        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setName("ROLE_USER");
        user.setRoles(List.of(roleUser));

        userAdmin.setId(2L);
        userAdmin.setUserName("mickey");
        userAdmin.setFirstName("Mike");
        userAdmin.setLastName("Smith");
        userAdmin.setPassword("'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'");
        userAdmin.setEmail("mike@gmail.com");
        Role roleAdmin = new Role();
        roleAdmin.setId(2L);
        roleAdmin.setName("ROLE_ADMIN");
        userAdmin.setRoles(List.of(roleAdmin));

        //creating 3 notes - 2 for USER and 1 for ADMIN
        Note note = new Note();
        Note noteAdmin = new Note();
        Note note2 = new Note();

        note.setId(1L);
        note.setOwner(user.getEmail());
        note.setTitle("Lorem ipsum");
        note.setContent("Lorem ipsum dolor sit amet");
        note.setCreated(Timestamp.from(Instant.now()));
        note.setLastEdited(Timestamp.from(Instant.now()));

        noteAdmin.setId(2L);
        noteAdmin.setOwner(userAdmin.getEmail());
        noteAdmin.setTitle("Lorem ipsum by Admin");
        noteAdmin.setContent("Lorem ipsum dolor sit amet. Admin stuff.");
        noteAdmin.setCreated(Timestamp.from(Instant.now()));
        noteAdmin.setLastEdited(Timestamp.from(Instant.now()));

        note2.setId(3L);
        note2.setOwner(user.getEmail());
        note2.setTitle("Lorem ipsum3");
        note2.setContent("Lorem ipsum dolor sit amet3");
        note2.setCreated(Timestamp.from(Instant.now()));
        note2.setLastEdited(Timestamp.from(Instant.now()));

        //mocking the DB (usersRepository and notesRepository) responses
        when(usersRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(usersRepository.findByUserName(userAdmin.getUserName())).thenReturn(userAdmin);
        when(notesRepository.findAllByOwnerOrderByTitleAsc(user.getEmail())).thenReturn(List.of(note, note2));
        when(notesRepository.findAllByOwnerOrderByTitleAsc(userAdmin.getEmail())).thenReturn(null);
        when(notesRepository.findAllByOrderByTitleAsc()).thenReturn(List.of(noteAdmin, note, note2));
        when(notesRepository.findById(1L)).thenReturn(java.util.Optional.of(note));
//        when(notesRepository.deleteById(1L)).thenReturn(java.util.Optional.of(note)).thenReturn(null);
    }

    @Test
    @DisplayName("Retrieve notes owned by USER")
    @WithMockUser(username = "johnny", roles = {"USER"})
    public void FindAllRegular() {

        List<Note> notes = notesService.findAll();
        assertEquals(2, notes.size());
    }

    @Test
    @DisplayName("Retrieve all the notes for ADMIN")
    @WithMockUser(username = "mickey", roles = {"ADMIN", "USER"})
    public void FindAllAdmin() {

        List<Note> notes = notesService.findAll();
        assertEquals(3, notes.size());
    }

    @Test
    @DisplayName("Find note by ID")
    @WithMockUser(username = "mickey", roles = {"ADMIN", "USER"})
    public void FindById() {

        Note note = notesService.findById(1L);
        assertEquals("Lorem ipsum", note.getTitle());
    }

    //TODO: delete method (DB mock for return void)
//    @Test
//    @DisplayName("Delete note by ID")
//    @WithMockUser(username = "mickey", roles = {"ADMIN", "USER"})
//    public void DeleteById() {
//
//        List<Note> notes = notesService.findAll();
//        notesService.deleteById(1L);
//
//        assertEquals(2, notes.size());
//    }
}
