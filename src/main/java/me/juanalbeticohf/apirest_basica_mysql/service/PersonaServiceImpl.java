package me.juanalbeticohf.apirest_basica_mysql.service;

import me.juanalbeticohf.apirest_basica_mysql.entity.Persona;
import me.juanalbeticohf.apirest_basica_mysql.repository.IPersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la entidad Persona.<br>
 * Implementa la logica de negocio de los metodos definidos la interfaz PersonaService, la inyección de dependencias del
 * IPersonaRepository y la anotacion {@code @Service} para declararlo como un bean de servicio.
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 * @see me.juanalbeticohf.apirest_basica_mysql.repository.IPersonaRepository
 * @see me.juanalbeticohf.apirest_basica_mysql.service.PersonaService
 */
@Service
public class PersonaServiceImpl implements PersonaService {
    /**
     * Inyeccion de dependencias del repositorio IPersonaRepository para acceder a los metodos de la capa de persistencia relacionados con la entidad Persona.
     */
    @Autowired
    private IPersonaRepository personaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Persona addPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Persona getPersonaById(Integer id) {
        return personaRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Persona updatePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Detalle implementación: <br>
     * Primero busca si el identificador "id" de la persona no es nulo y verifica su existencia en la base de datos
     * utilizando el métod0 {@code existsById} del repositorio. Si el "id" es nulo, entonces verifica si el campo "DNI"
     * no es nulo y utiliza el métod0 {@code existsByDNI} del repositorio para verificar su existencia. Si ambos campos
     * son nulos, devuelve {@code false}.
     * </p>
     */
    @Override
    public boolean existsPersona(Persona persona) {
        if (persona.getId() != null) {
            return personaRepository.existsById(persona.getId());
        } else if (persona.getDNI() != null) {
            return personaRepository.existsByDNI(persona.getDNI());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePersona(Integer id) {
        personaRepository.deleteById(id);
    }
}

