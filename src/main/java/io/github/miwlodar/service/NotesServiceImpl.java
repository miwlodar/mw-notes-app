//probably the most complex class of the app - implementing all the logic for CRUD operations on notes (taking into account the different approach to OAuth2 objects)
package io.github.miwlodar.service;

import io.github.miwlodar.config.CustomOAuth2User;
import io.github.miwlodar.dao.NotesRepository;
import io.github.miwlodar.dao.UserDao;
import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

	private final NotesRepository notesRepository;

	@Autowired
	private UserDao userDao;

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

	private String currentUserEmail() {
		//checking if the user was authorised by Google or in other way and retrieving user's email (to assign it to 'owner' field in DB)
		if (currentUsername().contains("www.googleapis.com/auth/userinfo.profile")) {
			final OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final CustomOAuth2User oauthUser = new CustomOAuth2User(oAuth2User);

			return oauthUser.getEmail();
		}
		else {
			String userName = currentUsername();
			Users user = userDao.findByUserName(userName);

			return user.getEmail();
		}
	}

	private boolean isAdmin() {
		String userName = currentUsername();

		if (!currentUsername().contains("www.googleapis.com/auth/userinfo.profile")) {
			Users user = userDao.findByUserName(userName);
			return (user.getRoles().toString()).contains("ROLE_ADMIN");
		}
		return false;
	}

	@Autowired
	public NotesServiceImpl(NotesRepository noteRepository) {
		notesRepository = noteRepository;
	}

	@Override
	public List<Note> findAll() {

		List<Note> retrievedNotes = notesRepository.findAllByOrderByTitleAsc();

		//admin can see all the notes, but all other user - only their notes
		if (!isAdmin()) {
			retrievedNotes.removeIf(note -> !currentUserEmail().equals(note.getOwner()));
		}
		return retrievedNotes;
	}

	@Override
	public Note findById(int id) {
		Optional<Note> result = notesRepository.findById(id);
		
		Note note;
		
		if (result.isPresent()) {
			note = result.get();
		}
		else {
			throw new RuntimeException("Did not find note id - " + id);
		}
		
		return note;
	}

	@Override
	public void save(Note note) {

		note.setOwner(currentUserEmail());

		notesRepository.save(note);
	}

	@Override
	public void deleteById(int id) {
		//retrieving the note and checking if the user is the note's owner. Admin can delete every note.
		List<Note> retrievedNotes = notesRepository.findByIdContainsAllIgnoreCase(id);

		if (!isAdmin()) {
			retrievedNotes.removeIf(note -> !currentUserEmail().equals(note.getOwner()));
		}
		notesRepository.deleteById(id);
	}

	@Override
	public List<Note> searchBy(String name) {

		List<Note> results;
		
		if (name != null && (name.trim().length() > 0)) {
			results = notesRepository.findByTitleContainsOrContentContainsAllIgnoreCase(name, name);
		}
		else {
			results = findAll();
		}

		//admin can see all the notes, but all other user - only their own notes
		if (!isAdmin()) {
			results.removeIf(note -> !currentUserEmail().equals(note.getOwner()));
		}
		return results;
	}
}
