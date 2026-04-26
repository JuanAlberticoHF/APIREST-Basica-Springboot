package me.juanalbeticohf.apirest_basica_mysql.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTO;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTOId;
import me.juanalbeticohf.apirest_basica_mysql.dto_persona.PersonaDTONoTrabajo;
import me.juanalbeticohf.apirest_basica_mysql.entity.Persona;
import me.juanalbeticohf.apirest_basica_mysql.service.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones CRUD de la entidad Persona.
 * Utiliza el servicio {@code PersonaService} para interactuar con la capa de negocio y la base de datos.
 *
 * @author JuanAlbeticoHF
 * @version 2.0.0
 * @since 1.0
 */
@RestController
@RequestMapping("personas")
@Validated // Activa las anotaciones para parametros especificos
@Tag(name = "Gestor de Personas", description = "Endpoints para gestionar las operaciones CRUD de la entidad Persona")
public class PersonaController {

    @Autowired
    private PersonaServiceImpl personaServiceImpl;

    @Operation(summary = "Añadir una persona", description = "Registra una nueva persona en la base de datos. Retorna el objeto creado con su ID generado automáticamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la petición es nulo o no cumple con las validaciones"),
            @ApiResponse(responseCode = "409", description = "Conflicto: La persona ya existe en la base de datos")
    })
    @PostMapping("/")
    public ResponseEntity<PersonaDTOId> addPersona(
            @Parameter(description = "Datos de la persona a registrar", required = true)
            @Valid @RequestBody PersonaDTO personaDTO) {
        Persona personaRes = personaServiceImpl.addPersona(new Persona(personaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(new PersonaDTOId(personaRes));
    }

    @Operation(summary = "Obtener todas las personas (Datos básicos)", description = "Devuelve un listado completo de todas las personas registradas, excluyendo el campo 'estaTrabajando' por defecto.")
    @ApiResponse(responseCode = "200", description = "Listado de personas recuperado exitosamente")
    @GetMapping("/")
    public ResponseEntity<List<PersonaDTOId>> allPersonas(){
        List<PersonaDTOId> listadoPersonasRes = new ArrayList<>();
        personaServiceImpl.getAllPersonas().forEach(persona -> listadoPersonasRes.add(new PersonaDTOId(persona)));
        return ResponseEntity.status(HttpStatus.OK).body(listadoPersonasRes);
    }

    @Operation(summary = "Obtener todas las personas (Con filtro de trabajo)", description = "Devuelve el listado de personas permitiendo elegir si se muestra o no la información laboral mediante un parámetro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de personas recuperado según el filtro aplicado"),
            @ApiResponse(responseCode = "400", description = "El parametro 'mostrarTrabajando' es nulo o no es un valor booleano válido")
    })
    @GetMapping("/alternativa")
    public ResponseEntity<List<Object>> allPersonas(
            @Parameter(description = "Si es 'true', incluye el campo 'estaTrabajando' en la respuesta. Si es 'false', lo omite.", example = "true", required = true)
            @RequestParam boolean mostrarTrabajando){
        List<Object> listadoPersonasRes = new ArrayList<>();
        if (mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> listadoPersonasRes.add(new PersonaDTO(persona)));
        }
        if (!mostrarTrabajando){
            personaServiceImpl.getAllPersonas().forEach(persona -> listadoPersonasRes.add(new PersonaDTONoTrabajo(persona)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(listadoPersonasRes);
    }

    @Operation(summary = "Obtener persona por ID", description = "Busca y devuelve los detalles de una persona específica utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado es nulo o inválido"),
            @ApiResponse(responseCode = "404", description = "No existe ninguna persona con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTOId> getPersona(
            @Parameter(description = "Identificador de la persona", example = "1", required = true)
            @NotNull(message = "El identificador no puede ser nulo.")
            @Positive(message = "El identificador debe ser positivo")
            @PathVariable Long id){
        Persona persona = personaServiceImpl.getPersonaById(id);
        return ResponseEntity.ok(new PersonaDTOId(persona));
    }

    /**
     * Lógica de actualización: Válida que el ID de la ruta coincida con el ID del body (si se proporciona) para evitar inconsistencias.
     */
    @Operation(summary = "Actualizar persona", description = "Sobrescribe los datos de una persona existente en la base de datos basándose en su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado en la URL es nulo o el formato del body no es valido."),
            @ApiResponse(responseCode = "404", description = "El ID a actualizar no existe en la base de datos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El DNI proporcionado ya existe.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(
            @Parameter(description = "ID de la persona a actualizar", example = "1", required = true)
            @NotNull(message = "El identificador no puede ser nulo.")
            @Positive(message = "El identificador debe ser positivo")
            @PathVariable Long id,
            @Parameter(description = "Objeto con los nuevos datos de la persona", required = true)
            @Valid @RequestBody PersonaDTO personaDTO){
        PersonaDTO personaRes = new PersonaDTO(personaServiceImpl.updatePersona(id, new Persona(personaDTO)));
        return ResponseEntity.ok(personaRes);
    }

    @Operation(summary = "Eliminar persona", description = "Borra físicamente a una persona de la base de datos utilizando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona eliminada exitosamente (Retorna 'true')"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado es nulo o no es un valor valido."),
            @ApiResponse(responseCode = "404", description = "La persona a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePersona(
            @Parameter(description = "ID de la persona a eliminar", example = "1", required = true)
            @NotNull(message = "El identificador no puede ser nulo.")
            @Positive(message = "El identificador debe ser positivo")
            @PathVariable Long id){
        personaServiceImpl.deletePersona(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}