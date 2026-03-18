package me.juanalbeticohf.apirest_basica_mongodb.dto_persona;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;

import java.time.LocalDate;

/**
 * Clase DTO para representar una persona con todos los datos. <br>
 * Implementa las anotaciones {@code @NoArgsConstructor}, {@code @AllArgsContructor}, {@code @Getter} y {@code @Setter}
 * de Lombok para generar automáticamente los constructores, getters y setters.
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonaDTOId {
    @Schema(description = "Identificador único de la persona. Es un campo autogenerado por la base de datos y no puede ser nulo.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    private String DNI;
    private String nombre;
    private String apellido;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private Boolean estaTrabajando;

    /**
     * Crea una instancia de PersonaDTOId a partir de un objeto Persona.
     * @param persona Objeto de tipo Persona del cual se extraerán los datos para crear el DTO.
     */
    public PersonaDTOId(Persona persona) {
        this.id = persona.getId();
        this.DNI = persona.getDNI();
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellidos();
        this.edad = persona.getEdad();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.estaTrabajando = persona.getEstaTrabajando();
    }
}
