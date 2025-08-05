package com.talleriv.Backend.exceptions;

public class NameValueRequiredException extends RuntimeException{
    public NameValueRequiredException(String message){
        super(message);
    }
}