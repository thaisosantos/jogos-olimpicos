package br.com.jogosolimpicos.exceptions;

public class Response400Exception extends RuntimeException{

    public Response400Exception(String msg){
        super(msg);
    }
}
