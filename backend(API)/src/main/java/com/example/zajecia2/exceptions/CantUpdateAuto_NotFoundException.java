package com.example.zajecia2.exceptions;

public class CantUpdateAuto_NotFoundException extends RuntimeException{
    public CantUpdateAuto_NotFoundException(){
        super("Nie mogę zaktualizować auta, ponieważ ono nie istnieje!");
    }
}
