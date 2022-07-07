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

	private NotesService notesService;
	
	public NotesController(NotesService theNotesService) {
		notesService = theNotesService;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listNotes(Model theModel) {
		
		// get notes from db
		List<Note> theNotes = notesService.findAll();
		
		// add to the spring model
		theModel.addAttribute("notes", theNotes);
		
		return "/notes/list-notes";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Note theNote = new Note();
		
		theModel.addAttribute("notes", theNote);
		
		return "/notes/notes-form";
	}


	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("noteId") int theId,
									Model theModel) {
		
		// get the note from the service
		Note theNote = notesService.findById(theId);
		
		// set note as a model attribute to pre-populate the form
		theModel.addAttribute("notes", theNote);
		
		// send over to our form
		return "/notes/notes-form";
	}
	
	
	@PostMapping("/save")
	public String saveNote(@ModelAttribute("notes") Note theNote) {
		
		// save the note
		notesService.save(theNote);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/notes/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("noteId") int theId) {
		
		// delete the note
		notesService.deleteById(theId);
		
		// redirect to /notes/list
		return "redirect:/notes/list";
		
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("noteName") String theName,
						 Model theModel) {
		
		// delete the note
		List<Note> theNotes = notesService.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("notes", theNotes);
		
		// send to /notes/list
		return "/notes/list-notes";
		
	}
	
}


















