package com.example.zajecia2.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("Blad! Nie moge usunac takiego auta bo nie ma takiego id w bazie!");


    }
}
