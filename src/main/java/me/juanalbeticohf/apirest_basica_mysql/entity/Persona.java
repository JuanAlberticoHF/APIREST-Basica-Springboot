package me.juanalbeticohf.apirest_basica_mysql.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTO;

import java.time.LocalDate;

/**
 * Entidad principal que representa a una persona física en el sistema.<br>
 * Esta clase está mapeada a la tabla {@code personas} en la base de datos MongoDB.
 * Utiliza Lombok para la generación automática de métodos de acceso, constructores, equals y hashcode.
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "personas")
public class Persona {
    /**
     * Identificador único de la persona. Es un campo autogenerado por la base de datos y no puede ser nulo.
     */
//    @NotNull(message = "El Id no debe ser nulo.")
    @Positive(message = "El Id debe ser un numero entero positivo.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Número de Documento Nacional de Identidad (DNI) de la persona. Es un campo único y no puede ser nulo.
     */
    @NotBlank(message = "El DNI no debe ser nulo ni estar vacio.")
    @Size(min = 9, max = 9, message = "La logintud minima y maxima de un DNI debe ser 9.")
    @Column(unique = true, nullable = false)
    private String dni;

    @NotBlank(message = "El Nombre no debe ser nulo ni estar vacio.")
    @Size(max = 50, message = "El Nombre debe tener una longitud maxima de 50 caractes.")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El Apellido no debe ser nulo ni estar vacio.")
    @Size(max = 50, message = "El Apellido debe tener una longitud maxima de 100 caractes.")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @NotNull(message = "La Edad no debe ser nula.")
    @Positive(message = "La Edad debe ser un numero positivo")
    @Max(value = 99, message = "La Edad maxima es de 99 años.")
    @Column(nullable = false)
    private Integer edad;

    @NotNull(message = "La Fecha de Nacimiento no debe ser nula")
    @Past(message = "La Fecha de Nacimiento debe ser un valor anterior a hoy")
    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Indica si la persona esta trabajando actualmente. {@code true} si esta trabajando, {@code false} en caso contrario.
     */
    @Column(name = "esta_trabajando")
    private Boolean estaTrabajando;

    public Persona(PersonaDTO personaDTO) {
        this.id = null;
        this.dni = personaDTO.getDni();
        this.nombre = personaDTO.getNombre();
        this.apellidos = personaDTO.getApellidos();
        this.edad = personaDTO.getEdad();
        this.fechaNacimiento = personaDTO.getFechaNacimiento();
        this.estaTrabajando = false;
    }
}
