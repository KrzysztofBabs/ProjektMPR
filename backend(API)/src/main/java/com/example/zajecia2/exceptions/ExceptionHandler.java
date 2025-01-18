package com.example.zajecia2.exceptions;

import com.example.zajecia2.model.Auto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

//    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotFoundException.class})
//    ResponseEntity<Object> handleNotFound(RuntimeException e, WebRequest request) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

//@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
//public ResponseEntity<String> handleNotFound(NotFoundException ex) {
//    // Zwrócenie komunikatu o błędzie do frontendu
//    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//}
    @org.springframework.web.bind.annotation.ExceptionHandler(CantDeleteAuto_NotFoundException.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("exception", e);
        return "error"; // Zwróci widok error.html
    }





    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CarAlreadyExistsException.class})
    ResponseEntity<Object>  handleAlreadyExists(RuntimeException e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CantDeleteAuto_NotFoundException.class})
//    ResponseEntity<Object> handleNotFound2(RuntimeException e, WebRequest request){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CantUpdateAuto_NotFoundException.class})
    ResponseEntity<Object> handleNotFound3(RuntimeException e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CantAddAuto_IncorrectData.class})
    ResponseEntity<Object> handleCantAdd(RuntimeException e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }




}
