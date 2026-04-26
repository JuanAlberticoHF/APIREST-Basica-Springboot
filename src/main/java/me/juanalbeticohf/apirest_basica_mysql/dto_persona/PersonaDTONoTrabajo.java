package me.juanalbeticohf.apirest_basica_mysql.dto_persona;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.juanalbeticohf.apirest_basica_mysql.entity.Persona;

import java.time.LocalDate;

/**
 * Clase DTO para representar una persona sin el campo "id" y "estaTrabajando". <br>
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
public class PersonaDTONoTrabajo {
    @NotBlank(message = "El DNI no debe ser nulo ni estar vacio.")
    @Size(min = 9, max = 9, message = "La logintud minima y maxima de un DNI debe ser 9")
    private String dni;

    @NotBlank(message = "El Nombre no debe ser nulo ni estar vacio.")
    @Size(max = 50, message = "El Nombre debe tener una longitud maxima de 50 caractes.")
    private String nombre;

    @NotBlank(message = "El Apellido no debe ser nulo ni estar vacio.")
    @Size(max = 50, message = "El Apellido debe tener una longitud maxima de 100 caractes.")
    private String apellidos;

    @NotNull(message = "La Edad no debe ser nula.")
    @Positive(message = "La Edad debe ser un numero positivo")
    @Max(value = 99, message = "La Edad maxima es de 99 años.")
    private Integer edad;

    @NotNull(message = "La Fecha de Nacimiento no debe ser nula")
    @Past(message = "La Fecha de Nacimiento debe ser un valor anterior a hoy")
    private LocalDate fechaNacimiento;

    /**
     * Crea una instancia de PersonaDTONoTrabajo a partir de un objeto Persona.
     * @param persona Objeto de tipo Persona del cual se extraerán los datos para crear el DTO.
     */
    public PersonaDTONoTrabajo(Persona persona) {
        this.dni = persona.getDni();
        this.nombre = persona.getNombre();
        this.apellidos = persona.getApellidos();
        this.edad = persona.getEdad();
        this.fechaNacimiento = persona.getFechaNacimiento();
    }
}
