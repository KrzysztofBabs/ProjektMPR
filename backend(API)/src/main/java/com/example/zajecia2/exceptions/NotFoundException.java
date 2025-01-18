package com.example.zajecia2.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Nie mogę zaaktualizować takiego auta, ponieważ ono nie istnieje!");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
