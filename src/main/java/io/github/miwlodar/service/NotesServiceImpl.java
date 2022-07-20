package io.github.miwlodar.service;

import io.github.miwlodar.dao.NotesRepository;
import io.github.miwlodar.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

	private NotesRepository notesRepository;

	private String currentUsername() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

	@Autowired // optional - as there's only 1 constructor
	public NotesServiceImpl(NotesRepository theNoteRepository) {
		notesRepository = theNoteRepository;
	}

	@Override
	public List<Note> findAll() {

		List<Note> retrievedNotes = notesRepository.findAllByOrderByTitleAsc();

		retrievedNotes.removeIf(note -> !currentUsername().equals(note.getOwner()));

		return retrievedNotes;
	}

	//no need to add logic for accessing only the notes owned by a given user, since this method is used only for update (not accessible by non-owners anyway)
	@Override
	public Note findById(int theId) {
		Optional<Note> result = notesRepository.findById(theId);
		
		Note theNote;
		
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

		theNote.setOwner(currentUsername());

		notesRepository.save(theNote);
	}

	@Override
	public void deleteById(int theId) {

		//checking if the user is the note's owner
		List<Note> retrievedNotes = notesRepository.findByIdContainsAndOwnerContainsAllIgnoreCase(theId, currentUsername());
		for (Note note: retrievedNotes) {
			if (currentUsername().equals(note.getOwner())) {
				notesRepository.deleteById(theId);
			}
		}
	}

	@Override
	public List<Note> searchBy(String theSearch) {

		List<Note> results;
		
		if (theSearch != null && (theSearch.trim().length() > 0)) {
			results = notesRepository.findByTitleContainsOrContentContainsAllIgnoreCase(theSearch, theSearch);
		}
		else {
			results = findAll();
		}
		
		results.removeIf(note -> !currentUsername().equals(note.getOwner()));

		return results;
	}
}