package me.juanalbeticohf.apirest_basica_mysql.controller;

import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTO;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTOId;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTONoTrabajo;
import me.juanalbeticohf.apirest_basica_mysql.entity.Persona;
import me.juanalbeticohf.apirest_basica_mysql.service.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones CRUD de la entidad Persona. <br>
 * Proporciona endpoints para crear, leer, actualizar y eliminar personas en la base de datos. <br>
 * Utiliza el servicio {@code PersonaService} para interactuar con la capa de negocio y la base de datos. <br>
 * Cada endpoint devuelve una respuesta HTTP adecuada según el resultado de la operación, incluyendo códigos de estado y
 * objetos DTO para representar los datos de las personas.
 * Implementa la anotación {@code @RestController} y {@code @RequestMapping}.
 * <li>URL base -> <a href="http://localhost:8082/personas/">http://localhost:8082/personas/</a></li>
 *
 * @author JuanAlbeticoHF
 * @version 1.0
 * @since 1.0
 * @see me.juanalbeticohf.apirest_basica_mysql.service.PersonaServiceImpl
 */
@RestController
@RequestMapping("personas")
public class PersonaController {

    /**
     * Inyeccion de dependencias del servicio PersonaServiceImpl para acceder a los metodos de la capa de negocio relacionados con la entidad Persona.
     */
    @Autowired
    private PersonaServiceImpl personaServiceImpl;

    /**
     * ENDPOINT para añadir una persona a la BD.
     * <li>URL -> <a href="http://localhost:8082/addPersona">http://localhost:8082/personas/addPersona</a></li>
     * @param persona La persona a añadir a la base de datos.
     * @return La respuesta HTTP con codigo y el objeto añadido a la base de datos, incluyendo su id generado automaticamente. Si la persona ya existe en la base de datos, devuelve un código de estado HTTP 409 (Conflict) sin cuerpo en la respuesta.
     */
    @PostMapping("/addPersona")
    public ResponseEntity<PersonaDTOId> addPersona(@RequestBody Persona persona){
        // Si la persona existe en la BD no se añade.
        if (personaServiceImpl.existsPersona(persona)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Persona personaRes = personaServiceImpl.addPersona(persona); // Añadimos el nuevo registro a la BD
            PersonaDTOId personaDTOId = new PersonaDTOId(personaRes); // Construimos el objeto a devolver.
            return new ResponseEntity<>(personaDTOId, HttpStatus.CREATED);
        }
    }

    /**
     * ENDPOINT para obtener todas las personas de la BD.
     * <li>URL -> <a href="http://localhost:8082/personas/">http://localhost:8082/personas/</a></li>
     * @return La respuesta HTTP con codigo y el listado de objeto, sin mostrar el campo "estaTrabajando" en la respuesta.
     */
    @GetMapping("/")
    public ResponseEntity<List<Object>> allPersonas(){
        List<Object> personaRes = new ArrayList<>();
        personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTONoTrabajo(persona)));
        return ResponseEntity.ok(personaRes);
    }

    /**
     * ENDPOINT para obtener todas las personas de la BD y mostrar si están trabajando.
     * <li>URL -> <a href="http://localhost:8082/personas/?mostrarTrabajando=boolean">http://localhost:8082/personas/?mostrarTrabajando=boolean</a></li>
     * @param mostrarTrabajando Parámetro booleano que indica si se desea mostrar el campo "estaTrabajando" en la respuesta. Si es {@code true}, se devuelve un listado de objetos PersonaDTO con todos los datos, incluyendo el campo "estaTrabajando". Si es {@code false}, se devuelve un listado de objetos PersonaDTONoTrabajo sin el campo "estaTrabajando".
     * @return La respuesta HTTP con codigo y el listado de objeto, dependiendo del valor del parámetro "mostrarTrabajando".
     */
    @GetMapping("/alternativa")
    public ResponseEntity<List<Object>> allPersonas(@RequestParam boolean mostrarTrabajando){
        List<Object> personaRes = new ArrayList<>();
        if (mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTO(persona)));
        }
        if (!mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTONoTrabajo(persona)));
        }
        return ResponseEntity.ok(personaRes);
    }

    /**
     * ENDPOINT para obtener una persona base a su identificador sobre la BD.
     * <li>URL -> <a href="http://localhost:8082/personas/{id}">http://localhost:8082/personas/{id}</a></li>
     * @param id El identificador de la persona a obtener de la base de datos.
     * @return La respuesta HTTP con codigo y el objeto persona con dicho identificador en la base de datos. <br>
     * Si no hay personas, devuelve un código de estado HTTP 404 (Not Found) sin cuerpo en la respuesta. <br>
     * Si el identificador es nulo, devuelve un código de estado HTTP 400 (Bad Request) sin cuerpo en la respuesta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable Integer id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else  {
            Persona persona = personaServiceImpl.getPersonaById(id);
            if (persona == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(new PersonaDTO(persona));
        }
    }

    /**
     * ENDPOINT para actualizar una persona existente en la BD por nuevos datos.
     * <li>URL -> <a href="http://localhost:8082/personas/{id}">http://localhost:8082/personas/{id}</a></li>
     * @param id El identificador de la persona a actualizar en la base de datos. Parametro por URL.
     * @param persona El objeto de tipo Persona con los datos actualizados.
     * @return La respuesta HTTP con codigo y el objeto persona actualizado en la base de datos. <br>
     * Si el identificador es nulo, devuelve un código de estado HTTP 400 (Bad Request) sin cuerpo en la respuesta. <br>
     * Si el id de la persona es distinto al id de la URL se produce un conflicto, devuelve un código de estado HTTP 409 (Conflict) sin cuerpo en la respuesta. <br>
     * Si el id no existe en la BD no se puede actualizar, devuelve un código de estado HTTP 404 (Not Found) sin cuerpo en la respuesta.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable Integer id, @RequestBody Persona persona){
        if (id == null) { // Si el id es nulo la petición esta mal hecha
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (persona.getId() != null && !persona.getId().equals(id)) { // Si el id de la persona es distinto al id de la URL se produce un conflicto
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (personaServiceImpl.getPersonaById(id) == null) { // Si el id no existe en la BD no se puede actualizar
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else { // Si el parametro id no es nulo, coinciden los id o la persona no tiene id y si existe en la BD
            persona.setId(id);
            PersonaDTO personaRes = new PersonaDTO(personaServiceImpl.updatePersona(persona));
            return ResponseEntity.ok(personaRes);
        }
    }

    /**
     * ENDPOINT para eliminar una persona existente en la BD base a su id.
     * <li>URL -> <a href="http://localhost:8082/personas/{id}">http://localhost:8082/personas/{id}</a></li>
     * @param id El identificador de la persona a eliminar de la base de datos.
     * @return La respuesta HTTP con codigo y un booleano indicando si la persona se ha eliminado correctamente, es decir, si no existe en la BD. <br>
     * Si el identificador es nulo, devuelve un código de estado HTTP 400 (Bad Request) sin cuerpo en la respuesta. <br>
     * Si el id no existe en la BD no se puede eliminar, devuelve un código de estado HTTP 404 (Not Found) sin cuerpo en la respuesta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePersona(@PathVariable Integer id){
        if (id == null) { // Si el id es nulo la petición esta mal hecha
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (personaServiceImpl.getPersonaById(id) == null) { // Si el id no existe
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else { // Si el id no es nulo y existe en la BD
            personaServiceImpl.deletePersona(id);
            // Devolvemos un booleano indicando si la persona se ha eliminado correctamente, es decir, si no existe en la BD
            return ResponseEntity.ok(personaServiceImpl.getPersonaById(id) == null);
        }
    }
}
