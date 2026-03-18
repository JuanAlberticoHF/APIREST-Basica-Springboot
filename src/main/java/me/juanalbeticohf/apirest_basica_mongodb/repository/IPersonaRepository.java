package me.juanalbeticohf.apirest_basica_mongodb.repository;

import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interfaz de repositorio para la entidad Persona.<br>
 * Extiende de MongoRepository e incluye un metodo personalizado verificar la existencia de una persona por su DNI.<br>
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 */
public interface IPersonaRepository extends MongoRepository<Persona, String> {
    /**
     * Verifica si existe una persona en la base de datos por su DNI.
     * @param dni El DNI a buscar en la base de datos.
     * @return {@code true} si existe una persona con el DNI proporcionado, {@code false} en caso contrario.
     */
    boolean existsByDni(String dni);
}
