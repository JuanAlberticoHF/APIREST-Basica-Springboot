package me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions;

public class PersonaNotFoundException extends RuntimeException {
    public PersonaNotFoundException(String message) {
        super(message);
    }
}
