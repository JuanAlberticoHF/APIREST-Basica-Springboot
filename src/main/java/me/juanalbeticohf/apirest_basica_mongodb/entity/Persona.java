package me.juanalbeticohf.apirest_basica_mongodb.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class Persona {
    /**
     * Identificador único de la persona.
     */
    @Id
    private String id;

    /**
     * Número de Documento Nacional de Identidad (DNI) de la persona. Es un campo único y no puede ser nulo.
     */
    private String dni;

    private String nombre;

    private String apellidos;

    private Integer edad;

    @Field(name = "fecha_nac")
    private LocalDate fechaNacimiento;

    /**
     * Indica si la persona esta trabajando actualmente. {@code true} si esta trabajando, {@code false} en caso contrario.
     */
    @Field(name = "esta_trabajando")
    private Boolean estaTrabajando;
}
