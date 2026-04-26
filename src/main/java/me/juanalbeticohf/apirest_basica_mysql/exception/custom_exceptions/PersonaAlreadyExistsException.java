package me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions;

public class PersonaAlreadyExistsException extends RuntimeException {
    public PersonaAlreadyExistsException(String message) {
        super(message);
    }
}
