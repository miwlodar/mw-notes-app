package io.github.miwlodar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.miwlodar.entity.Note;

public interface NotesRepository extends JpaRepository<Note, Integer> {

	// that's it ... no need to write any code LOL!

	
	// add a method to sort by id //ADD TITLE!!!!!!!
	List<Note> findAllByOrderByTitleAsc();
	
	// search by content of the note //ADD TITLE!!!!!!!!!!!
	List<Note> findByTitleContainsOrContentContainsAllIgnoreCase(String title, String content);

}
