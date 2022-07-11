package io.github.miwlodar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.miwlodar.entity.Note;

public interface NotesRepository extends JpaRepository<Note, Integer> {

	// that's it ... no need to write any code LOL!

	
	// add a method to sort by title
	List<Note> findAllByOrderByTitleAsc();
	
	// search by title or content of the note
	List<Note> findByTitleContainsOrContentContainsAllIgnoreCase(String title, String content);

	// search by id and owner of the note
	List<Note> findByIdContainsAndOwnerContainsAllIgnoreCase(int id, String owner);

	// search by owner
	List<Note> findByOwnerContainsAllIgnoreCase(String owner);

}
