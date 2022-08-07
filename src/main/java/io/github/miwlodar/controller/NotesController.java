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
	
	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}

	@GetMapping("/list")
	public String listNotes(Model model) {

		List<Note> notes = notesService.findAll();

		model.addAttribute("notes", notes);
		
		return "/notes/list-notes";
	}
	
	@GetMapping("/form-for-add")
	public String formForAdd(Model model) {

		Note note = new Note();
		
		model.addAttribute("notes", note);
		
		return "/notes/notes-form";
	}


	@GetMapping("/form-for-update")
	public String formForUpdate(@RequestParam("noteId") int id,
									Model model) {

		Note note = notesService.findById(id);

		model.addAttribute("notes", note);

		return "/notes/notes-form";
	}
	
	
	@PostMapping("/save")
	public String saveNote(@ModelAttribute("notes") Note theNote) {

		notesService.save(theNote);

		return "redirect:/notes/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("noteId") int id) {

		notesService.deleteById(id);

		return "redirect:/notes/list";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("noteName") String name,
						 Model model) {

		List<Note> notes = notesService.searchBy(name);

		model.addAttribute("notes", notes);

		return "/notes/list-notes";
		
	}
}
