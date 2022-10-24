package io.github.miwlodar.db;

import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.Role;
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
class RolesRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RolesRepository rolesRepository;

    @BeforeEach
    void addUser() {
        jdbcTemplate.execute("insert into roles (name) VALUES ('ROLE_USER');");
        jdbcTemplate.execute("insert into roles (name) VALUES ('ROLE_ADMIN');");
    }

    @Test
    @DisplayName("RolesRepository retrieves all records")
    void findAll() throws Exception {
        List<Role> roles = rolesRepository.findAll();
        assertEquals(2, roles.size());

        Role userRole = roles.get(0);
        assertEquals(1, userRole.getId());
        assertEquals("ROLE_USER", userRole.getName());

        Role adminRole = roles.get(1);
        assertEquals(2, adminRole.getId());
        assertEquals("ROLE_ADMIN", adminRole.getName());

        assertNotNull(userRole.getId());
        assertNotNull(adminRole.getId());
    }
}
