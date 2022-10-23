//one of the most complex classes of the app - implementing all the logic for CRUD operations on notes (taking into account the different approach to OAuth2 objects)
package io.github.miwlodar.service;

import io.github.miwlodar.config.GoogleOauth2User;
import io.github.miwlodar.db.NotesRepository;
import io.github.miwlodar.db.UsersRepository;
import io.github.miwlodar.entity.Note;
import io.github.miwlodar.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;

    private final UsersRepository usersRepository;

    public NotesServiceImpl(NotesRepository notesRepository, UsersRepository usersRepository) {
        this.notesRepository = notesRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public List<Note> findAll() {
        if (isAdmin()) {
            return notesRepository.findAllByOrderByTitleAsc();
        }

        return notesRepository.findAllByOwnerOrderByTitleAsc(loadCurrentUserEmail());
    }

    @Override
    public Note findById(Long id) {
        return notesRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find note id - " + id));
    }

    @Override
    public void save(Note note) {
        note.setOwner(loadCurrentUserEmail());
        notesRepository.save(note);
    }

    @Override
    public void deleteById(Long id) {
        //retrieving the note and checking if the user is the note's owner. Admin can delete every note.
        notesRepository
                .findById(id)
                .ifPresentOrElse(note -> {
                    if (!isAdmin() && !loadCurrentUserEmail().equals(note.getOwner())) {
                        throw new AccessDeniedException("Forbidden");
                    }
                    notesRepository.deleteById(id);
                }, () -> {
                    throw new RuntimeException("Not found");
                });
    }

    @Override
    public List<Note> searchBy(String name) {
        final List<Note> results;

        if (name != null && (name.trim().length() > 0)) {
            results = notesRepository.findByTitleContainsOrContentContainsAllIgnoreCase(name, name);
        } else {
            results = findAll();
        }

        //admin can see all the notes, but all other user - only their own notes
        if (!isAdmin()) {
            results.removeIf(note -> !loadCurrentUserEmail().equals(note.getOwner()));
        }

        return results;
    }

    private String currentUsername() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    private String loadCurrentUserEmail() {
        //checking if the user was authorised by Google or in other way and retrieving user's email (to assign it to 'owner' field in DB)
        if (isGoogleUser()) {
            final OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final GoogleOauth2User oauthUser = new GoogleOauth2User(oAuth2User);

            return oauthUser.getEmail();
        }

        final String userName = currentUsername();
        final User user = usersRepository.findByUserName(userName);

        return user.getEmail();
    }

    private boolean isGoogleUser() {
        return currentUsername().contains("www.googleapis.com/auth/userinfo.profile");
    }

    private boolean isAdmin() {
        final String userName = currentUsername();

        if (!isGoogleUser()) {
            User user = usersRepository.findByUserName(userName);
            return (user.getRoles().toString()).contains("ROLE_ADMIN");
        }

        return false;
    }
}
