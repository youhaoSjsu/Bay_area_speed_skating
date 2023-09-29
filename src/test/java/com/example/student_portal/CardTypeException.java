package com.example.student_portal;

public class CardTypeException extends Exception {
    public CardTypeException(){
        super("card type unavailable");
    }
}
