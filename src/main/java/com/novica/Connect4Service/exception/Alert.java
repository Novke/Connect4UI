package com.novica.Connect4Service.exception;

import lombok.Getter;

@Getter
public class Alert extends RuntimeException{

    protected final String message;

    public Alert(String message){
        super(message);
        this.message = message;
    }

}
