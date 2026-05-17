package com.example.sge.controller;

import com.example.sge.model.Note;
import com.example.sge.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> ajouter(
            @RequestBody Note note) {

        return ResponseEntity.status(201)
                .body(noteService.ajouterNote(note));
    }

    @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<Note>>
    notesEtudiant(@PathVariable Long id) {

        return ResponseEntity.ok(
                noteService.listerParEtudiant(id));
    }

    @GetMapping("/module/{id}")
    public ResponseEntity<List<Note>>
    notesModule(@PathVariable Long id) {

        return ResponseEntity.ok(
                noteService.listerParModule(id));
    }

    @GetMapping("/moyenne/{etudiantId}")
    public ResponseEntity<Double>
    moyenne(@PathVariable Long etudiantId) {

        return ResponseEntity.ok(
                noteService.calculerMoyenne(
                        etudiantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> modifier(
            @PathVariable Long id,
            @RequestBody Note note) {

        return ResponseEntity.ok(
                noteService.modifier(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        noteService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}