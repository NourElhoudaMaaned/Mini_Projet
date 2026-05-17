package com.example.sge.service;

import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;
import com.example.sge.repository.EtudiantRepository;
import com.example.sge.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    public Note ajouterNote(Note note) {

        if (note.getValeur() < 0
                || note.getValeur() > 20) {
            throw new RuntimeException(
                    "La note doit etre entre 0 et 20");
        }

        Note saved = noteRepository.save(note);

        mettreAJourMoyenne(
                note.getEtudiant().getId());

        return saved;
    }

    public List<Note> listerParEtudiant(
            Long etudiantId) {

        return noteRepository
                .findByEtudiantId(etudiantId);
    }

    public List<Note> listerParModule(
            Long moduleId) {

        return noteRepository
                .findByModuleId(moduleId);
    }

    public Double calculerMoyenne(
            Long etudiantId) {

        return noteRepository
                .calculerMoyenne(etudiantId);
    }

    public void mettreAJourMoyenne(
            Long etudiantId) {

        Double moyenne =
                calculerMoyenne(etudiantId);

        Etudiant etudiant =
                etudiantRepository.findById(etudiantId)
                        .orElseThrow();

        etudiant.setMoyenne(
                moyenne != null ? moyenne : 0);

        etudiantRepository.save(etudiant);
    }

    public Note modifier(Long id, Note note) {

        Note existing =
                noteRepository.findById(id)
                        .orElseThrow();

        existing.setValeur(note.getValeur());

        Note updated =
                noteRepository.save(existing);

        mettreAJourMoyenne(
                existing.getEtudiant().getId());

        return updated;
    }

    public void supprimer(Long id) {

        Note note = noteRepository.findById(id)
                .orElseThrow();

        Long etudiantId =
                note.getEtudiant().getId();

        noteRepository.deleteById(id);

        mettreAJourMoyenne(etudiantId);
    }
    public String calculerMention(double moyenne) {

        if (moyenne >= 16)
            return "Excellent";

        if (moyenne >= 14)
            return "Très bien";

        if (moyenne >= 12)
            return "Bien";

        if (moyenne >= 10)
            return "Passable";

        return "Ajourné";
    }
}