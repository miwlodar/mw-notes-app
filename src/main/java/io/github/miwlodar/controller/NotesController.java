//1 of 3 controllers - handling all CRUD operations for notes and returning pages accordingly
package io.github.miwlodar.controller;

import io.github.miwlodar.entity.Note;
import io.github.miwlodar.service.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public String listNotes(Model model) {
        final List<Note> notes = notesService.findAll();
        model.addAttribute("notes", notes);

        return "/notes/list-notes";
    }

    @GetMapping("/form-for-add")
    public String formForAdd(Model model) {
        final Note note = new Note();
        model.addAttribute("notes", note);

        return "/notes/notes-form";
    }

    @GetMapping("/form-for-update")
    public String formForUpdate(@RequestParam("noteId") Long noteId, Model model) {
        final Note note = notesService.findById(noteId);
        model.addAttribute("notes", note);

        return "/notes/notes-form";
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute("notes") Note note) {
        notesService.save(note);

        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        notesService.deleteById(id);

        return "redirect:/notes";
    }

    @GetMapping("/search")
    public String search(@RequestParam("noteName") String name,
                         Model model) {
        final List<Note> notes = notesService.searchBy(name);
        model.addAttribute("notes", notes);

        return "/notes/list-notes";
    }
}
