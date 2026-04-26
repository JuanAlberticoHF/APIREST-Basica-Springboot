package me.juanalbeticohf.apirest_basica_mysql.exception;

import jakarta.validation.ConstraintViolationException;
import me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions.InvalidIdException;
import me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions.NotMatchIdsException;
import me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions.PersonaAlreadyExistsException;
import me.juanalbeticohf.apirest_basica_mysql.exception.custom_exceptions.PersonaNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja la excepción {@code PersonaAlreadyExistsException} cuando al crear una persona esta ya existe.
     * @param ex Excepción de tipo {@code PersonaAlreadyExistsException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 409 Conflict}
     */
    @ExceptionHandler(PersonaAlreadyExistsException.class)
    public ResponseEntity<String> personaAlreadyExistsException(PersonaAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code NotMatchIdsException} cuando el identificador del parametro no coincide con el identificador del cuerpo de la petición.
     * @param ex Excepción de tipo {@code NotMatchIdsException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 400 Bad Request}
     */
    @ExceptionHandler(NotMatchIdsException.class)
    public ResponseEntity<String> notMatchIdsException(NotMatchIdsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code PersonaNotFoundException} cuando la persona solicitada no existe.
     * @param ex Excepción de tipo {@code PersonaNotFoundException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 404 Not Found}
     */
    @ExceptionHandler(PersonaNotFoundException.class)
    public ResponseEntity<String> personaNotFoundException(PersonaNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code PersonaNotFoundException} cuando el identificador proporcionado es nulo o no válido.
     * @param ex Excepción de tipo {@code PersonaNotFoundException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 400 Bad Request}
     */
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> invalidIdException(InvalidIdException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code MethodArgumentNotValidException} cuando el argumento o parametro no es valido (capa web).
     * @param ex Excepción de tipo {@code MethodArgumentNotValidException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 400 Bad Request}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    /**
     * Maneja la excepción {@code MethodArgumentTypeMismatchException} cuando el argumento o parametro no es un tipo valido.
     * @param ex Excepción de tipo {@code MethodArgumentTypeMismatchException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 400 Bad Request}
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code ConstraintViolationException} cuando varios argumentos no son correctos (capa web) o
     * al validar un registro que no es correcto base entidad (Spring Data JPA/Hibernate)
     * @param ex Excepción de tipo {@code ConstraintViolationException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 400 Bad Request}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@code DataIntegrityViolationException} cuando la base de datos emite un error.
     * @param ex Excepción de tipo {@code DataIntegrityViolationException}.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP {@code 409 Conflict}
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolationException(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
