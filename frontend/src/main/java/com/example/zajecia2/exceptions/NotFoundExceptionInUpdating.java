package com.example.zajecia2.exceptions;

public class NotFoundExceptionInUpdating  extends RuntimeException{
    public NotFoundExceptionInUpdating(){
        super("Blad! Nie moge zaaktualizowac auta bo nie ma takiego id w bazie!");
    }
    public NotFoundExceptionInUpdating(String m){
        super(m);

    }
}
