package com.afsal.project2.exception;

public class RuleViolationException extends RuntimeException{
    public RuleViolationException(String message){
        super(message);
    }
}
