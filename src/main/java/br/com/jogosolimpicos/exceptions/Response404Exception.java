package br.com.jogosolimpicos.exceptions;

public class Response404Exception extends RuntimeException{

    public Response404Exception(String msg){
        super(msg);
    }
}
