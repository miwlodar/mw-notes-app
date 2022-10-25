package io.github.miwlodar.service;

import io.github.miwlodar.db.NotesRepository;
import io.github.miwlodar.db.RolesRepository;
import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;
import io.github.miwlodar.user.CreateUserDto;
import io.github.miwlodar.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsersServiceImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsersService usersService;

    @MockBean
    private RolesRepository rolesRepository;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private NotesRepository notesRepository;

    @BeforeEach
    public void addingUserToRepo() {

        Role roleUser = TestUtils.createRole(1L, "ROLE_USER");

        User user = TestUtils.createUser(1L, "johnny", "John", "Bravo", "'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'", "johnny@gmail.com", roleUser);

        //mocking the DB (usersRepository) responses
        when(usersRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(rolesRepository.findByName("ROLE_USER")).thenReturn(java.util.Optional.of(roleUser));
    }

    @Test
    @DisplayName("Find user by userName")
    public void findByUserName() {
        User foundUser = usersService.findByUserName("johnny");
        assertNotNull(foundUser);
    }

    @Test
    @DisplayName("Save user")
    public void save() {

        CreateUserDto userDto = new CreateUserDto();

        userDto.setUserName("johnny");
        userDto.setPassword("pass123");
        userDto.setMatchingPassword("pass123");
        userDto.setFirstName("John");
        userDto.setLastName("Bravo");
        userDto.setEmail("johnny@gmail.com");

        usersService.save(userDto);

        verify(usersRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }
}
