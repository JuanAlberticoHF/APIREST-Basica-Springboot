package me.juanalbeticohf.apirest_basica_mysql.repository;

import me.juanalbeticohf.apirest_basica_mysql.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz de repositorio para la entidad Persona.<br>
 * Extiende de JpaRepository e incluye un metodo personalizado verificar la existencia de una persona por su DNI.<br>
 * @author JuanAlbeticoHF
 * @version 1.1
 * @since 1.0
 */
public interface IPersonaRepository extends JpaRepository<Persona, Long> {
    /**
     * Verifica si existe una persona en la base de datos por su DNI.
     * @param dni El DNI a buscar en la base de datos.
     * @return {@code true} si existe una persona con el DNI proporcionado, {@code false} en caso contrario.
     */
    boolean existsByDni(String dni);
}
