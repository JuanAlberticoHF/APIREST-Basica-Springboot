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
@Document(collection = "prac_personas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "personas")
public class Persona {
    /**
     * Identificador único de la persona.
     */
    @Id
    private String id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Número de Documento Nacional de Identidad (DNI) de la persona. Es un campo único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String dni;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false)
    private Integer edad;

    @Field(name = "fecha_nac")
    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Indica si la persona esta trabajando actualmente. {@code true} si esta trabajando, {@code false} en caso contrario.
     */
    @Column(name = "esta_trabajando")
    private Boolean estaTrabajando;
}
