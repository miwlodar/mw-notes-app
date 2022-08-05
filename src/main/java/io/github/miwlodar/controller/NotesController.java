//1 of 3 controllers - handling all CRUD operations for notes and returning pages accordingly
package io.github.miwlodar.controller;

import java.util.List;
import io.github.miwlodar.service.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.miwlodar.entity.Note;

@Controller
@RequestMapping("/notes")
public class NotesController {

	private final NotesService notesService;
	
	public NotesController(NotesService theNotesService) {
		notesService = theNotesService;
	}

	@GetMapping("/list")
	public String listNotes(Model theModel) {

		List<Note> theNotes = notesService.findAll();

		theModel.addAttribute("notes", theNotes);
		
		return "/notes/list-notes";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Note theNote = new Note();
		
		theModel.addAttribute("notes", theNote);
		
		return "/notes/notes-form";
	}


	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("noteId") int theId,
									Model theModel) {

		Note theNote = notesService.findById(theId);

		theModel.addAttribute("notes", theNote);

		return "/notes/notes-form";
	}
	
	
	@PostMapping("/save")
	public String saveNote(@ModelAttribute("notes") Note theNote) {

		notesService.save(theNote);

		return "redirect:/notes/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("noteId") int theId) {

		notesService.deleteById(theId);

		return "redirect:/notes/list";
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("noteName") String theName,
						 Model theModel) {

		List<Note> theNotes = notesService.searchBy(theName);

		theModel.addAttribute("notes", theNotes);

		return "/notes/list-notes";
		
	}
}


















