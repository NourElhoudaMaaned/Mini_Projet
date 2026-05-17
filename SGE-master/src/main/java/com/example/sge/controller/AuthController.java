package com.example.sge.controller;

import com.example.sge.dto.AuthResponse;
import com.example.sge.dto.LoginRequest;
import com.example.sge.model.Role;
import com.example.sge.model.Utilisateur;
import com.example.sge.repository.UtilisateurRepository;
import com.example.sge.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepo;
    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody LoginRequest req) {
        if (utilisateurRepo.findByUsername(
                req.getUsername()).isPresent())
            return ResponseEntity.badRequest()
                    .body("Username deja utilise");
        Utilisateur u = new Utilisateur();
        u.setUsername(req.getUsername());
        u.setPassword(
                passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());
        utilisateurRepo.save(u);
        return ResponseEntity.status(201).body(
                new AuthResponse(
                        jwtService.generateToken(u),
                        u.getRole().name()));
    }




    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        Utilisateur user = utilisateurRepo.findByUsername(req.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(token, user.getRole().name())
        );
    }
}
