package com.example.demo.validation;


import com.example.demo.repository.UserRepository;import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExists, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !userRepository.existsByEmail(email);
    }
}
