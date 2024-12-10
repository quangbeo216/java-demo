package com.example.demo.service;



import com.example.demo.constant.Helper;
import com.example.demo.constant.Role;
import com.example.demo.constant.TokenType;
import com.example.demo.entity.*;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.ChangeInfomationRequest;
import com.example.demo.request.ChangePasswordRequest;
import com.example.demo.request.RegisterRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> register(RegisterRequest request) {
        var code = Helper.generatePassword(4,false,false);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MANAGER)
                .verifyCode(code)
                .build();
        var savedUser = repository.save(user);
        Map<String,Object> res = new HashMap<>();
        // Send code to email usser
        //res.put("code",code);
        return res;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public Map<String, Object> verify(String email, String code) {
        User user = repository.findByEmainCode(email,code).orElse(null);
        Map<String,Object> res = new HashMap<>();

        if (user != null) {
            user.setEnabled(true);
            var savedUser = repository.save(user);
            res.put("success", 1);
        }

        return res;
    }

    public void updateEnabel(User user) {
        user.setEnabled(true);
        var savedUser = repository.save(user);
    }

    public Map<String, Object> updatePassword(User user, ChangePasswordRequest request) {
        Map<String,Object> res = new HashMap<>();
        try {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
            res.put("success",1);
        } catch (Exception e) {

        }

        // Option logout all device other
        // create new and clear all token

        return res;
    }


    public Map<String, Object> updateInfomation(User user, ChangeInfomationRequest request) {
        Map<String,Object> res = new HashMap<>();
        try {
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            repository.save(user);
            res.put("success",1);
        } catch (Exception e) {

        }

        // Option logout all device other
        // create new and clear all token

        return res;
    }

    public Map<String, Object> authenticate(AuthenticationRequest request) {
        Map<String,Object> res = new HashMap<>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            System.out.println("111");
            return res;
        }

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        //res.put("user",user);
        res.put("jwtToken",jwtToken);
        res.put("refreshToken",refreshToken);
        return res;
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public Map<String, Object> getUserById(Integer id) {
        String sql = "SELECT u.id,u.email,u.firstname, u.lastname,u.enabled,u.role  FROM user u WHERE u.id = "+id+" LIMIT 1";
        UserDTO user = (UserDTO) entityManager.createNativeQuery(sql, UserDTO.class).getSingleResult();

       /* List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        for (Object[] row : results) {
            Long id1 = ((Number) row[0]).longValue(); // Cast and convert if necessary
            String name = (String) row[1];

            System.out.println("ID: " + id1 + ", Name: " + name);
        }*/
        // Optional<User> user = repository.findById(id);
        Map<String,Object> res = new HashMap<>();
        res.put("user",user);
        return res;
    }


}
