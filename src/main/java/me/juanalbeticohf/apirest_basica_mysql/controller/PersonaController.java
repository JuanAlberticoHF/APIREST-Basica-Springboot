package me.juanalbeticohf.apirest_basica_mysql.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * Controlador REST para gestionar las operaciones CRUD de la entidad Persona.
 * Utiliza el servicio {@code PersonaService} para interactuar con la capa de negocio y la base de datos.
 *
 * @author JuanAlbeticoHF
 * @version 1.0
 */
@RestController
@RequestMapping("personas")
@Tag(name = "Gestor de Personas", description = "Endpoints para gestionar las operaciones CRUD de la entidad Persona")
public class PersonaController {

    @Autowired
    private PersonaServiceImpl personaServiceImpl;

    @Operation(summary = "Añadir una persona", description = "Registra una nueva persona en la base de datos. Retorna el objeto creado con su ID generado automáticamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto: La persona ya existe en la base de datos")
    })
    @PostMapping("/addPersona")
    public ResponseEntity<PersonaDTOId> addPersona(
            @Parameter(description = "Datos de la persona a registrar", required = true)
            @RequestBody Persona persona) {
        // Si la persona existe en la BD no se añade.
        if (personaServiceImpl.existsPersona(persona)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Persona personaRes = personaServiceImpl.addPersona(persona);
            PersonaDTOId personaDTOId = new PersonaDTOId(personaRes);
            return new ResponseEntity<>(personaDTOId, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Obtener todas las personas (Datos básicos)", description = "Devuelve un listado completo de todas las personas registradas, excluyendo el campo 'estaTrabajando' por defecto.")
    @ApiResponse(responseCode = "200", description = "Listado de personas recuperado exitosamente")
    @GetMapping("/")
    public ResponseEntity<List<Object>> allPersonas(){
        List<Object> personaRes = new ArrayList<>();
        personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTONoTrabajo(persona)));
        return ResponseEntity.ok(personaRes);
    }

    @Operation(summary = "Obtener todas las personas (Con filtro de trabajo)", description = "Devuelve el listado de personas permitiendo elegir si se muestra o no la información laboral mediante un parámetro.")
    @ApiResponse(responseCode = "200", description = "Listado de personas recuperado según el filtro aplicado")
    @GetMapping("/alternativa")
    public ResponseEntity<List<Object>> allPersonas(
            @Parameter(description = "Si es 'true', incluye el campo 'estaTrabajando' en la respuesta. Si es 'false', lo omite.", example = "true", required = true)
            @RequestParam boolean mostrarTrabajando){
        List<Object> personaRes = new ArrayList<>();
        if (mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTO(persona)));
        }
        if (!mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> personaRes.add(new PersonaDTONoTrabajo(persona)));
        }
        return ResponseEntity.ok(personaRes);
    }

    @Operation(summary = "Obtener persona por ID", description = "Busca y devuelve los detalles de una persona específica utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado es nulo o inválido"),
            @ApiResponse(responseCode = "404", description = "No existe ninguna persona con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersona(
            @Parameter(description = "Identificador de la persona", example = "1", required = true)
            @PathVariable Integer id){
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
     * Lógica de actualización: Valida que el ID de la ruta coincida con el ID del body (si se proporciona) para evitar inconsistencias.
     */
    @Operation(summary = "Actualizar persona", description = "Sobrescribe los datos de una persona existente en la base de datos basándose en su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado en la URL es nulo"),
            @ApiResponse(responseCode = "404", description = "El ID a actualizar no existe en la base de datos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El ID de la URL y el ID del cuerpo de la petición no coinciden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(
            @Parameter(description = "ID de la persona a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Objeto con los nuevos datos de la persona", required = true)
            @RequestBody Persona persona){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (persona.getId() != null && !persona.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (personaServiceImpl.getPersonaById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            persona.setId(id);
            PersonaDTO personaRes = new PersonaDTO(personaServiceImpl.updatePersona(persona));
            return ResponseEntity.ok(personaRes);
        }
    }

    @Operation(summary = "Eliminar persona", description = "Borra físicamente a una persona de la base de datos utilizando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona eliminada exitosamente (Retorna 'true')"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado es nulo"),
            @ApiResponse(responseCode = "404", description = "La persona a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePersona(
            @Parameter(description = "ID de la persona a eliminar", example = "1", required = true)
            @PathVariable Integer id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (personaServiceImpl.getPersonaById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            personaServiceImpl.deletePersona(id);
            return ResponseEntity.ok(personaServiceImpl.getPersonaById(id) == null);
        }
    }
}