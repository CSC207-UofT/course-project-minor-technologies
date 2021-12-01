package com.minortechnologies.workr_backend.usecase.fileio;

public class MalformedDataException extends Exception{

    public MalformedDataException(String message){
        super (message);
    }

    public MalformedDataException(){
        super();
    }
}
