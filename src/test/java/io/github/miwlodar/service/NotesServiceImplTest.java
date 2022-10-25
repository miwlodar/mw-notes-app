package io.github.miwlodar.service;

import io.github.miwlodar.db.NotesRepository;
import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;
import io.github.miwlodar.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        //creating 2 roles - USER and ADMIN
        Role roleUser = TestUtils.createRole(1L, "ROLE_USER");
        Role roleAdmin = TestUtils.createRole(2L, "ROLE_ADMIN");

        //creating 2 users - 1 with role ADMIN and 1 with role USER
        User user = TestUtils.createUser(1L, "johnny", "John", "Bravo", "'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'", "johnny@gmail.com", roleUser);
        User userAdmin = TestUtils.createUser(2L, "mickey", "Mike", "Smith", "'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'", "mike@gmail.com", roleAdmin);

        //creating 3 notes - 2 for USER and 1 for ADMIN
        Note note = TestUtils.createNote(1L, user.getEmail(), "Lorem ipsum", "Lorem ipsum dolor sit amet");
        Note note2 = TestUtils.createNote(2L, user.getEmail(), "Lorem ipsum3", "Lorem ipsum dolor sit amet3");
        Note noteAdmin = TestUtils.createNote(3L, userAdmin.getEmail(), "Lorem ipsum by Admin", "Lorem ipsum dolor sit amet. Admin stuff.");

        //mocking the DB (usersRepository and notesRepository) responses
        when(usersRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(usersRepository.findByUserName(userAdmin.getUserName())).thenReturn(userAdmin);
        when(notesRepository.findAllByOwnerOrderByTitleAsc(user.getEmail())).thenReturn(List.of(note, note2));
        when(notesRepository.findAllByOwnerOrderByTitleAsc(userAdmin.getEmail())).thenReturn(null);
        when(notesRepository.findAllByOrderByTitleAsc()).thenReturn(List.of(noteAdmin, note, note2));
        when(notesRepository.findById(1L)).thenReturn(java.util.Optional.of(note));
        when(notesRepository.findById(2L)).thenReturn(java.util.Optional.of(note2));
        when(notesRepository.findById(3L)).thenReturn(java.util.Optional.of(noteAdmin));
    }

    @Test
    @DisplayName("Retrieve notes owned by USER")
    @WithMockUser(username = "johnny", roles = {"USER"})
    public void FindAllRegular() {

        List<Note> notes = notesService.findAll();
        assertEquals(2, notes.size()); //expected 2 notes for USER added above

    }

    @Test
    @DisplayName("Retrieve all the notes for ADMIN")
    @WithMockUser(username = "mickey", roles = {"ADMIN"})
    public void FindAllAdmin() {

        List<Note> notes = notesService.findAll();
        assertEquals(3, notes.size()); ////expected 3 notes for ADMIN added above (2 notes added for USER and 1 for exclusively for ADMIN)

    }

    @Test
    @DisplayName("Find note by ID")
    @WithMockUser(username = "mickey", roles = {"ADMIN"})
    public void FindById() {

        Note note = notesService.findById(1L);
        assertEquals("Lorem ipsum", note.getTitle());

    }

    @Test
    @DisplayName("Save note")
    @WithMockUser(username = "mickey", roles = {"ADMIN"})
    public void save() {

        Role roleUser = TestUtils.createRole(1L, "ROLE_USER");

        User user3 = TestUtils.createUser(1L, "johnny", "John", "Bravo", "'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'", "johnny@gmail.com", roleUser);

        Note note3 = TestUtils.createNote(2L, user3.getEmail(), "Lorem ipsum3", "Lorem ipsum dolor sit amet3");

        notesService.save(note3);

        verify(notesRepository, times(1)).save(note3);

    }

    @Test
    @DisplayName("Delete note by ID")
    @WithMockUser(username = "mickey", roles = {"ADMIN"})
    public void DeleteById() {

        notesService.deleteById(1L);

        verify(notesRepository, times(1)).deleteById(1L);

    }

    @Test
    @DisplayName("Non-admin user can't delete notes of sb else")
    @WithMockUser(username = "johnny", roles = {"USER"})
    public void nonAdminCannotDeleteOther() {

        assertThrows(AccessDeniedException.class, () -> notesService.deleteById(3L));
        verify(notesRepository, times(0)).deleteById(3L);

    }
}
