package io.github.miwlodar.db;

import io.github.miwlodar.entity.Role;
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
    @DisplayName("Method findByName works properly")
    void findAll() throws Exception {
        List<Role> roles = List.of(rolesRepository.findByName("ROLE_USER").get());
        assertEquals(1, roles.size());
        List<Role> roles2 = List.of(rolesRepository.findByName("ROLE_USER").get(), rolesRepository.findByName("ROLE_ADMIN").get());
        assertEquals(2, roles2.size());

        Role userRole = roles2.get(0);
        assertEquals(1, userRole.getId());
        assertEquals("ROLE_USER", userRole.getName());

        Role adminRole = roles2.get(1);
        assertEquals(2, adminRole.getId());
        assertEquals("ROLE_ADMIN", adminRole.getName());

        assertNotNull(userRole.getId());
        assertNotNull(adminRole.getId());
    }
}
