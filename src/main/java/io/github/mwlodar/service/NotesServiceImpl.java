package io.github.mwlodar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mwlodar.dao.NotesRepository;
import io.github.mwlodar.entity.Note;

@Service
public class NotesServiceImpl implements NotesService {

	private NotesRepository notesRepository;
	
	@Autowired
	public NotesServiceImpl(NotesRepository theNoteRepository) {
		notesRepository = theNoteRepository;
	}
	
	@Override
	public List<Note> findAll() {
		return notesRepository.findAllByOrderByIdAsc();
	}

	@Override
	public Note findById(int theId) {
		Optional<Note> result = notesRepository.findById(theId);
		
		Note theNote = null;
		
		if (result.isPresent()) {
			theNote = result.get();
		}
		else {
			// we didn't find the note
			throw new RuntimeException("Did not find note id - " + theId);
		}
		
		return theNote;
	}

	@Override
	public void save(Note theNote) {
		notesRepository.save(theNote);
	}

	@Override
	public void deleteById(int theId) {
		notesRepository.deleteById(theId);
	}

	@Override
	public List<Note> searchBy(String theContent) {
		
		List<Note> results = null;
		
		if (theContent != null && (theContent.trim().length() > 0)) {
			results = notesRepository.findByContentContainsAllIgnoreCase(theContent);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

}






