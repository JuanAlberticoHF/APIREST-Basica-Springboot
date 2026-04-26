package me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super(message);
    }
}
