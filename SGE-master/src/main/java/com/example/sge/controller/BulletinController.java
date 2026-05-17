package com.example.sge.controller;

import com.example.sge.dto.BulletinDTO;
import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;
import com.example.sge.service.EtudiantService;
import com.example.sge.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bulletins")
public class BulletinController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private NoteService noteService;

    @GetMapping
    public String liste(Model model) {

        List<Etudiant> etudiants =
                etudiantService.listerTous();

        List<BulletinDTO> bulletins =
                new ArrayList<>();

        for (Etudiant e : etudiants) {

            BulletinDTO b = construireBulletin(e);

            bulletins.add(b);
        }

        model.addAttribute("bulletins",
                bulletins);

        return "bulletins/liste";
    }

    @GetMapping("/{etudiantId}")
    public String detail(
            @PathVariable Long etudiantId,
            Model model) {

        Etudiant e = etudiantService
                .trouverParId(etudiantId)
                .orElseThrow();

        BulletinDTO bulletin =
                construireBulletin(e);

        model.addAttribute("bulletin",
                bulletin);

        return "bulletins/detail";
    }

    private BulletinDTO construireBulletin(
            Etudiant e) {

        BulletinDTO b = new BulletinDTO();

        List<Note> notes =
                noteService
                        .listerParEtudiant(e.getId());

        Double moyenne =
                noteService
                        .calculerMoyenne(e.getId());

        double m = moyenne != null
                ? moyenne : 0;

        b.setEtudiant(e);
        b.setNotes(notes);
        b.setMoyenneGenerale(m);

        b.setMention(
                noteService.calculerMention(m));

        b.setAdmis(m >= 10);

        return b;
    }
}