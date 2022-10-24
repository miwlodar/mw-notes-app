package io.github.miwlodar.service;

import io.github.miwlodar.db.NotesRepository;
import io.github.miwlodar.db.RolesRepository;
import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;
import io.github.miwlodar.user.CreateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        //creating a new user
        User user = new User();

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

        //mocking the DB (usersRepository) responses
        when(usersRepository.findByUserName(user.getUserName())).thenReturn(user);
    }

    @Test
    @DisplayName("Find user by userName")
    public void findByUserName() {
        User foundUser = usersService.findByUserName("johnny");
        assertNotNull(foundUser);
    }

    //TODO test for saving the user
//    @Test
//    @DisplayName("Save the user")
//    public void saveUser() {
//        CreateUserDto userDto = new CreateUserDto();
//
//        userDto.setUserName("johnny");
//        userDto.setPassword("'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'");
//        userDto.setMatchingPassword("'$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su'");
//        userDto.setFirstName("John");
//        userDto.setLastName("Bravo");
//        userDto.setEmail("johnny@gmail.com");
//
//        when(rolesRepository.findByName("ROLE_USER").get()).thenReturn(new Role("ROLE_USER"));
////        when(usersRepository.save()).thenReturn()
//
//        usersService.save(userDto);
//        assertNotNull(userDto);
//    }
}
