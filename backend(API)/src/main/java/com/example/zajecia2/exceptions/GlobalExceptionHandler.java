package com.example.zajecia2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(CantDeleteAuto_NotFoundException.class)
    public String handleNotFoundException(CantDeleteAuto_NotFoundException ex, Model model) {
        // Dodaj komunikat o błędzie
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";  // Wyświetlenie strony błędu
    }
}
