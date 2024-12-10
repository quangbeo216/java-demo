package com.example.demo.controller;


import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PreAuthorize("hasRole('USER') || hasRole('MANAGER')")
    @PostMapping("/information")
    public ResponseEntity<String> information() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) auth.getPrincipal();
        authService.updateEnabel(userDetails);
        System.out.println("User has authorities: " + userDetails.getFirstname() + " " + userDetails.getLastname());
        return ResponseEntity.ok("information");
    }


}

