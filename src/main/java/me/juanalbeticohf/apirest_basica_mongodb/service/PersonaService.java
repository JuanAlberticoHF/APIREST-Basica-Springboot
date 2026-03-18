package me.juanalbeticohf.apirest_basica_mongodb.service;

import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;

import java.util.List;

/**
 * Interfaz de servicio para la entidad Persona.<br>
 * Define los métodos para realizar operaciones CRUD sobre la entidad Persona.
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 */
public interface PersonaService {
    /**
     * Agrega una nueva persona a la base de datos.
     * @param persona Objeto de tipo Persona que se desea agregar a la base de datos.
     * @return Devuelve el objeto Persona que ha sido agregado a la base de datos, incluyendo su ID generado
     * automáticamente.
     */
    Persona addPersona(Persona persona);

    /**
     * Obtiene una lista de objetos persona de la base de datos.
     * @return Devuelve una lista de objetos Persona que se encuentran en la base de datos. Si no hay personas,
     * devuelve una lista vacía.
     */
    List<Persona> getAllPersonas();

    /**
     * Obtiene un objeto persona de la base de datos base a su identificador.
     * @param id El identificador de la persona que se desea obtener de la base de datos.
     * @return Devuelve la persona con dicho identificador en la base de datos. Si no hay personas,
     * devuelve una {@code null}.
     */
    Persona getPersonaById(Integer id);

    /**
     * Actualiza un objeto persona en la base de datos base a sus datos.
     * @param persona El objeto de tipo Persona con los datos actualizados. El campo "id" debe contener el identificador de la persona que se desea actualizar.
     * @return Devuelve la persona con los datos actualizados. Si no existe una persona con el identificador proporcionado, devuelve {@code null}.
     */
    Persona updatePersona(Persona persona);

    /**
     * Verifica si existe una persona en la base de datos base a sus datos.
     * @param persona El objeto de tipo Persona con todos los datos.
     * @return {@code true} si existe el id o DNI y {@code false} si no existe.
     */
    boolean existsPersona(Persona persona);

    /**
     * Eliminar una persona de la base de datos base a su identificador.
     * @param id El identificador de la persona a eliminar de la base de datos.
     */
    void deletePersona(Integer id);
}
