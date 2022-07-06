package io.github.mwlodar.service;

import java.util.List;

import io.github.mwlodar.entity.Note;

public interface NotesService {

	List<Note> findAll();
	
	Note findById(int theId);
	
	void save(Note theNote);
	
	void deleteById(int theId);

	List<Note> searchBy(String theName);
	
}
