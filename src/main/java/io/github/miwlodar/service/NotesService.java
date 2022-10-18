//interface for Notes service - all CRUD methods, implemented in a separate class - in line with a common design pattern
package io.github.miwlodar.service;

import io.github.miwlodar.entity.Note;

import java.util.List;

public interface NotesService {

    List<Note> findAll();

    Note findById(Long id);

    void save(Note note);

    void deleteById(Long id);

    List<Note> searchBy(String name);

}
