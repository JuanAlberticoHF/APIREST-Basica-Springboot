package me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions;

public class NotMatchIdsException extends RuntimeException {
    public NotMatchIdsException(String message) {
        super(message);
    }
}
