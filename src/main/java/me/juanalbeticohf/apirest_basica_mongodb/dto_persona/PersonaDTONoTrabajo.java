package me.juanalbeticohf.apirest_basica_mongodb.dto_persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;

import java.time.LocalDate;

/**
 * Clase DTO para representar una persona sin el campo "id" y "estaTrabajando". <br>
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
public class PersonaDTONoTrabajo {
    private String DNI;
    private String nombre;
    private String apellido;
    private Integer edad;
    private LocalDate fechaNacimiento;

    /**
     * Crea una instancia de PersonaDTONoTrabajo a partir de un objeto Persona.
     * @param persona Objeto de tipo Persona del cual se extraerán los datos para crear el DTO.
     */
    public PersonaDTONoTrabajo(Persona persona) {
        this.DNI = persona.getDNI();
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellidos();
        this.edad = persona.getEdad();
        this.fechaNacimiento = persona.getFechaNacimiento();
    }
}
