//interface for Notes service - all CRUD methods, implemented in a separate class - in line with a common design pattern
package io.github.miwlodar.service;

import java.util.List;

import io.github.miwlodar.entity.Note;

public interface NotesService {

	List<Note> findAll();
	
	Note findById(int theId);
	
	void save(Note theNote);
	
	void deleteById(int theId);

	List<Note> searchBy(String theName);
	
}
