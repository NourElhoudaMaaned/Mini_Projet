package com.example.sge.config;

import com.example.sge.model.Role;
import com.example.sge.model.Utilisateur;
import com.example.sge.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        System.out.println("SEEDER STARTED");

        Utilisateur admin = new Utilisateur();
        admin.setUsername("test123");
        admin.setPassword("test123");
        admin.setRole(Role.ADMIN);

        Utilisateur saved = repo.save(admin);

        System.out.println("SAVED ID = " + saved.getId());
    }
}