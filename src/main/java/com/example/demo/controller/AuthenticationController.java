package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.ChangeInfomationRequest;
import com.example.demo.request.ChangePasswordRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.service.AuthenticationService;import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController extends ApiController {

    final AuthenticationService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@Valid @RequestBody RegisterRequest request) {
        return success("Success",authService.register(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> vefiry(@RequestBody Map<String, Object> payload) {
        String code = (String) payload.get("code");
        String email = (String) payload.get("email");
        Map<String,Object> res = new HashMap<>();

        if (code != null && email != null ) {
            res = authService.verify(email,code);

            if (!res.isEmpty()){
                return success("success fuly",res);
            }
        }

        return error("cannot update",res);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody AuthenticationRequest request) {
        Map<String,Object> res;
        res = authService.authenticate(request);

        if (!res.isEmpty()){
            return success("success fuly",res);
        }

        return error("Login fail",res);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("chagnePassword")
    public ResponseEntity<Map<String,Object>> chagnePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Map<String,Object> res = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) auth.getPrincipal();
        boolean error = false;

        if (!passwordEncoder.matches(request.getCurrentPassword(), userDetails.getPassword())) {
            error = true;
        }

        if (!error && !request.getNewPassword().equals(request.getConfirmationPassword())) {
            error = true;
        }

        if (!error) {
            res = authService.updatePassword(userDetails,request);

            if (!res.isEmpty()){
                return success("success fuly",res);
            }
        }

        return error("Cannot update pw",res);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/information")
    public ResponseEntity<Map<String,Object>> information(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) auth.getPrincipal();

        return success("Success",authService.getUserById(userDetails.getId()));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/updateInformation")
    public ResponseEntity<Map<String,Object>> updateInformation(@Valid @RequestBody ChangeInfomationRequest request) {
        Map<String,Object> res = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) auth.getPrincipal();

        res = authService.updateInfomation(userDetails,request);

        if (!res.isEmpty()){
            return success("success fuly",res);
        }

        return error("Cannot update",res);
    }

}

