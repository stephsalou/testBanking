package com.stephane.basicbanking.customException;

public class NullArgumentException extends RuntimeException{

    public NullArgumentException(String message) {
        super(message);
    }
    public NullArgumentException(){
        super();
    }
}
