package io.github.miwlodar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.github.miwlodar.dao.NotesRepository;
import io.github.miwlodar.entity.Note;

@Service
public class NotesServiceImpl implements NotesService {

	private NotesRepository notesRepository;
	
	@Autowired // optional - as there's only 1 constructor
	public NotesServiceImpl(NotesRepository theNoteRepository) {
		notesRepository = theNoteRepository;
	}
	
	@Override
	public List<Note> findAll() {
		return notesRepository.findAllByOrderByTitleAsc();
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
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		theNote.setOwner(username);

		notesRepository.save(theNote);
	}

	@Override
	public void deleteById(int theId) {
		notesRepository.deleteById(theId);
	}

	@Override
	public List<Note> searchBy(String theSearch) {
		
		List<Note> results = null;
		
		if (theSearch != null && (theSearch.trim().length() > 0)) {
			results = notesRepository.findByTitleContainsOrContentContainsAllIgnoreCase(theSearch, theSearch);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

}