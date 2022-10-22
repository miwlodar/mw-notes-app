//interface using JpaRepository and adding a couple of useful methods used in the service implementation
package io.github.miwlodar.db;

import io.github.miwlodar.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByOrderByTitleAsc();

    List<Note> findAllByOwnerOrderByTitleAsc(String owner);

    // search by title or content of the note
    List<Note> findByTitleContainsOrContentContainsAllIgnoreCase(String title, String content);

}
