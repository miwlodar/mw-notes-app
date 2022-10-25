package io.github.miwlodar.util;

import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TestUtils {
    public static User createUser(long id, String userName, String firstName, String lastName, String password, String email, Role role) {
        User user = new User();

        user.setId(id);
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(List.of(role));

        return user;
    }

    public static Role createRole(long id, String name) {
        Role role = new Role();

        role.setId(id);
        role.setName(name);

        return role;
    }

    public static Note createNote(long id, String owner, String title, String content) {

        Note note = new Note();

        note.setId(id);
        note.setOwner(owner);
        note.setTitle(title);
        note.setContent(content);
        Timestamp timestamp = Timestamp.from(Instant.now());
        note.setCreated(timestamp);
        note.setLastEdited(timestamp);

        return note;
    }
}
