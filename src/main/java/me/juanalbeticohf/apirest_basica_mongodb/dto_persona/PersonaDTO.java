package me.juanalbeticohf.apirest_basica_mongodb.dto_persona;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;

import java.time.LocalDate;

/**
 * Clase DTO para representar una persona sin el campo "id". <br>
 * Implementa las anotaciones {@code @NoArgsConstructor}, {@code @AllArgsContructor}, {@code @Getter} y {@code @Setter}
 * de Lombok para generar automáticamente los constructores, getters y setters.
 * @author JuanAlbeticoHF
 * @version 1.0.1
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonaDTO {
    @Schema(description = "Número de Documento Nacional de Identidad (DNI) de la persona. Es un campo único y no puede ser nulo.",
            example = "12345678A")
    private String dni;
    @Schema(description = "Nombre de la persona. Es un campo obligatorio y no puede ser nulo.",
            example = "Juan")
    private String nombre;
    @Schema(description = "Apellido de la persona. Es un campo obligatorio y no puede ser nulo.",
            example = "Pérez")
    private String apellidos;
    @Schema(description = "Edad de la persona. Es un campo obligatorio y no puede ser nulo.",
            example = "30")
    private Integer edad;
    @Schema(description = "Fecha de nacimiento de la persona. Es un campo obligatorio y no puede ser nulo.",
            example = "1994-05-15")
    private LocalDate fechaNacimiento;
    @Schema(description = "Indica si la persona esta trabajando actualmente. Es un campo opcional.",
            example = "true")
    private Boolean estaTrabajando;

    /**
     * Crea una instancia de PersonaDTO a partir de un objeto Persona.
     * @param persona Objeto de tipo Persona del cual se extraerán los datos para crear el DTO.
     */
    public PersonaDTO (Persona persona) {
        this.dni = persona.getDni();
        this.nombre = persona.getNombre();
        this.apellidos = persona.getApellidos();
        this.edad = persona.getEdad();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.estaTrabajando = persona.getEstaTrabajando();
    }
}
