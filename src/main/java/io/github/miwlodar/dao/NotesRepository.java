//interface using JpaRepository and adding a couple of useful methods used in the service implementation
package io.github.miwlodar.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.miwlodar.entity.Note;

public interface NotesRepository extends JpaRepository<Note, Long> {

	List<Note> findAllByOrderByTitleAsc();
	
	// search by title or content of the note
	List<Note> findByTitleContainsOrContentContainsAllIgnoreCase(String title, String content);

	// search by id of the note
	List<Note> findByIdContainsAllIgnoreCase(Long id);
}
