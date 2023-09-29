package net.safety.exception;

public class DataLoadErrorException extends RuntimeException{

    public DataLoadErrorException(String msg){
        super(msg);
    }
}
