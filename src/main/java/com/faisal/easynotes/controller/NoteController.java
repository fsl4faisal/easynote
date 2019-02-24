package com.faisal.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faisal.easynotes.exception.ResourceNotFoundException;
import com.faisal.easynotes.model.Note;
import com.faisal.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	NoteRepository noteRepository;

	/*
	 * Get All Notes
	 * 
	 * @GetMapping("/notes") is Short form
	 * of @RequestMapping(value="/notes",method=RequestMethod.GET)
	 * 
	 * (GET /api/notes)
	 */
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	/*
	 * Create a new Note
	 * 
	 * (POST /api/notes)
	 */
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}

	/*
	 * Get a Single Note
	 * 
	 * (GET /api/notes/{noteId})
	 */
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}

	/*
	 * Update a Note
	 * 
	 * (PUT /api/notes/{noteId})
	 */
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	/*
	 * Delete a Note
	 * 
	 * (DELETE /api/notes/{noteId})
	 */
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);

		return ResponseEntity.ok().build();
	}

}
