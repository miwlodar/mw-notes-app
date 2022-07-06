package io.github.mwlodar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mwlodar.entity.Note;

public interface NotesRepository extends JpaRepository<Note, Integer> {

	// that's it ... no need to write any code LOL!

	
	// add a method to sort by id //ADD TITLE!!!!!!!
	List<Note> findAllByOrderByIdAsc();
	
	// search by content of the note //ADD TITLE!!!!!!!!!!!
	List<Note> findByContentContainsAllIgnoreCase(String content);

}
