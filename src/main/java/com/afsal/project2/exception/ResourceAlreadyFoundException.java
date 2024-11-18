package com.afsal.project2.exception;

public class ResourceAlreadyFoundException extends RuntimeException{
    public ResourceAlreadyFoundException(String message){
        super(message);
    }
}
