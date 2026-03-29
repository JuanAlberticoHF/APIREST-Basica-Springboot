package me.juanalbeticohf.apirest_basica_mongodb.controller;

import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;
import me.juanalbeticohf.apirest_basica_mongodb.service.PersonaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test para el PersonaController (CAMINO FELIZ)
 * Utiliza la anotacion @WebMvcTest(clase) para cargar solo el contexto de la capa web y permitir probar los
 * endpoints del controlador de forma aislada, sin cargar toda la aplicacion ni la capa de servicio o repositorio.
 */
@WebMvcTest(PersonaController.class)
class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulador de peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @MockitoBean
    private PersonaServiceImpl personaService; // Mock de la capa de servicio

    @Test
    void addPersona() throws Exception {
        // --- PREPARAR (GIVEN) ---
        // Preparamos los datos de entrada y salida para el metodo addPersona.
        Persona persona = new Persona(
                null,
                "00000000A",
                "JuanAlberticoHF",
                "Youtube GitHub",
                25,
                LocalDate.of(2000, 1, 1),
                false);

        Persona personaGuardada = new Persona(
                "id_generado",
                "00000000A",
                "JuanAlberticoHF",
                "Youtube GitHub",
                25,
                LocalDate.of(2000, 1, 1),
                false);

        // Preparamos el mock del servicio para que simule la base de datos añadiendo una persona
        // Cuando se llame a addPersona con CUALQUIER Persona que devuelva personaGuardada
        when(personaService.addPersona(any(Persona.class))).thenReturn(personaGuardada);

        // --- EJECUTAR (WHEN) ---
        // 3. Ejecutamos la petición HTTP simulada
        mockMvc.perform(post("/personas/addPersona") // Cambia la ruta por la real que tengas en el controlador
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos el formato de la peticion (datos que enviamos)
                        .content(objectMapper.writeValueAsString(persona)) // Convertimos el objeto persona a JSON para enviarlo en el cuerpo de la petición
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)

                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé respuesta sea 201 Created
                .andExpect(status().isCreated())
                // Verificamos que la respuesta JSON contenga los datos correctos
                .andExpect(jsonPath("$.id").exists()) // Verificamos que se generó un ID
                .andExpect(jsonPath("$.dni").value(personaGuardada.getDni()))
                .andExpect(jsonPath("$.nombre").value(personaGuardada.getNombre()))
                .andExpect(jsonPath("$.apellidos").value(personaGuardada.getApellidos()))
                .andExpect(jsonPath("$.edad").value(personaGuardada.getEdad()))
                .andExpect(jsonPath("$.fechaNacimiento").value(personaGuardada.getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.estaTrabajando").value(personaGuardada.getEstaTrabajando()));
    }

    @Test
    void allPersonas() throws Exception {
        // --- PREPARAR (GIVEN) ---
        // Preparamos los datos de salida que sera un listado de personas.
        List<Persona> personasDevueltas = new ArrayList<>(List.of(
                new Persona(
                    "1",
                    "00000000A",
                    "JuanAlbertoHF",
                    "Youtube GitHub",
                    20,
                    LocalDate.of(2005, 1, 1),
                    true
                ),
                new Persona(
                    "2",
                    "11111111B",
                    "Maria Luisa",
                    "Gomez Sanchez",
                    25,
                    LocalDate.of(1995, 1, 1),
                    true
                )
        ));

        // Preparamos el mock del servicio para que simule la base de datos devolviendo un listado de personas
        // Cuando se llame a getAllPersonas() devuelva personasDevueltas
        when(personaService.getAllPersonas()).thenReturn(personasDevueltas);

        // --- EJECUTAR (WHEN) ---
        // Ejecutamos la petición HTTP simulada
        // Realiza una petición GET a la ruta del controlador que devuelve el listado de personas
        mockMvc.perform(get("/personas/")
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos correctos
                .andExpect(jsonPath("$").isArray()) // Verificamos que la respuesta es un array
                .andExpect(jsonPath("$.length()").value(personasDevueltas.size())) // Verificamos el tamaño de la lista
                // Datos persona 1
                .andExpect(jsonPath("$.[0].id").value(personasDevueltas.getFirst().getId()))
                .andExpect(jsonPath("$.[0].dni").value(personasDevueltas.getFirst().getDni()))
                .andExpect(jsonPath("$.[0].nombre").value(personasDevueltas.getFirst().getNombre()))
                .andExpect(jsonPath("$.[0].apellidos").value(personasDevueltas.getFirst().getApellidos()))
                .andExpect(jsonPath("$.[0].edad").value(personasDevueltas.getFirst().getEdad()))
                .andExpect(jsonPath("$.[0].fechaNacimiento").value(personasDevueltas.getFirst().getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[0].estaTrabajando").value(personasDevueltas.getFirst().getEstaTrabajando()))
                // Datos persona 2
                .andExpect(jsonPath("$.[1].id").value(personasDevueltas.get(1).getId()))
                .andExpect(jsonPath("$.[1].dni").value(personasDevueltas.get(1).getDni()))
                .andExpect(jsonPath("$.[1].nombre").value(personasDevueltas.get(1).getNombre()))
                .andExpect(jsonPath("$.[1].apellidos").value(personasDevueltas.get(1).getApellidos()))
                .andExpect(jsonPath("$.[1].edad").value(personasDevueltas.get(1).getEdad()))
                .andExpect(jsonPath("$.[1].fechaNacimiento").value(personasDevueltas.get(1).getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[1].estaTrabajando").value(personasDevueltas.get(1).getEstaTrabajando()));
    }

    @Test
    void getAllPersonasAlternativaFalse() throws Exception{
        // --- PREPARAR (GIVEN) ---
        // Preparamos los datos de salida que sera un listado de personas.
        List<Persona> personasDevueltas = new ArrayList<>(List.of(
                new Persona(
                        "1",
                        "00000000A",
                        "JuanAlbertoHF",
                        "Youtube GitHub",
                        20,
                        LocalDate.of(2005, 1, 1),
                        true
                ),
                new Persona(
                        "2",
                        "11111111B",
                        "Maria Luisa",
                        "Gomez Sanchez",
                        25,
                        LocalDate.of(1995, 1, 1),
                        true
                )
        ));

        // Preparamos el mock del servicio para que simule la base de datos devolviendo un listado de personas.
        // Cuando se llame a getAllPersonas() devuelva personasDevueltas
        when(personaService.getAllPersonas()).thenReturn(personasDevueltas);

        // --- EJECUTAR (WHEN) ---
        // Ejecutamos la petición HTTP simulada
        // Realiza una petición GET a la ruta del controlador que devuelve todas las personas.
        mockMvc.perform(get("/personas/alternativa")
                        .param("mostrarTrabajando", "false") // Agregamos el parámetro para mostrar el campo "estaTrabajando"
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos el formato de la peticion (datos que enviamos)
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos correctos
                .andExpect(jsonPath("$").isArray()) // Verificamos que la respuesta es un array
                .andExpect(jsonPath("$.length()").value(personasDevueltas.size())) // Verificamos el tamaño de la lista
                // Datos persona 1
                .andExpect(jsonPath("$.[0].id").doesNotExist())
                .andExpect(jsonPath("$.[0].dni").value(personasDevueltas.getFirst().getDni()))
                .andExpect(jsonPath("$.[0].nombre").value(personasDevueltas.getFirst().getNombre()))
                .andExpect(jsonPath("$.[0].apellidos").value(personasDevueltas.getFirst().getApellidos()))
                .andExpect(jsonPath("$.[0].edad").value(personasDevueltas.getFirst().getEdad()))
                .andExpect(jsonPath("$.[0].fechaNacimiento").value(personasDevueltas.getFirst().getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[0].estaTrabajando").doesNotExist())
                // Datos persona 2
                .andExpect(jsonPath("$.[1].id").doesNotExist())
                .andExpect(jsonPath("$.[1].dni").value(personasDevueltas.get(1).getDni()))
                .andExpect(jsonPath("$.[1].nombre").value(personasDevueltas.get(1).getNombre()))
                .andExpect(jsonPath("$.[1].apellidos").value(personasDevueltas.get(1).getApellidos()))
                .andExpect(jsonPath("$.[1].edad").value(personasDevueltas.get(1).getEdad()))
                .andExpect(jsonPath("$.[1].fechaNacimiento").value(personasDevueltas.get(1).getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[1].estaTrabajando").doesNotExist());
    }

    @Test
    void getAllPersonasAlternativaTrue() throws Exception{
        // --- PREPARAR (GIVEN) ---
        // Preparamos los datos de salida que sera un listado de personas con todos los datos.
        List<Persona> personasDevueltas = new ArrayList<>(List.of(
                new Persona(
                        "1",
                        "00000000A",
                        "JuanAlbertoHF",
                        "Youtube GitHub",
                        20,
                        LocalDate.of(2005, 1, 1),
                        true
                ),
                new Persona(
                        "2",
                        "11111111B",
                        "Maria Luisa",
                        "Gomez Sanchez",
                        25,
                        LocalDate.of(1995, 1, 1),
                        true
                )
        ));

        // Preparamos el mock del servicio para que simule la base de datos devolviendo un listado de personas.
        // Cuando se llame a getAllPersonas() devuelva el listado de personasDevueltas.
        when(personaService.getAllPersonas()).thenReturn(personasDevueltas);

        // --- EJECUTAR (WHEN) ---
        // 3. Ejecutamos la petición HTTP simulada
        // Realiza una petición GET a la ruta del controlador para devolver todas las personas.
        mockMvc.perform(get("/personas/alternativa")
                        // Agregamos el parámetro para mostrar el campo "estaTrabajando"
                        .param("mostrarTrabajando", "true")
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos correctos
                .andExpect(jsonPath("$").isArray()) // Verificamos que la respuesta es un array
                .andExpect(jsonPath("$.length()").value(personasDevueltas.size())) // Verificamos el tamaño de la lista
                // Datos persona 1
                .andExpect(jsonPath("$.[0].id").doesNotExist())
                .andExpect(jsonPath("$.[0].dni").value(personasDevueltas.getFirst().getDni()))
                .andExpect(jsonPath("$.[0].nombre").value(personasDevueltas.getFirst().getNombre()))
                .andExpect(jsonPath("$.[0].apellidos").value(personasDevueltas.getFirst().getApellidos()))
                .andExpect(jsonPath("$.[0].edad").value(personasDevueltas.getFirst().getEdad()))
                .andExpect(jsonPath("$.[0].fechaNacimiento").value(personasDevueltas.getFirst().getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[0].estaTrabajando").value(personasDevueltas.getFirst().getEstaTrabajando()))
                // Datos persona 2
                .andExpect(jsonPath("$.[1].id").doesNotExist())
                .andExpect(jsonPath("$.[1].dni").value(personasDevueltas.get(1).getDni()))
                .andExpect(jsonPath("$.[1].nombre").value(personasDevueltas.get(1).getNombre()))
                .andExpect(jsonPath("$.[1].apellidos").value(personasDevueltas.get(1).getApellidos()))
                .andExpect(jsonPath("$.[1].edad").value(personasDevueltas.get(1).getEdad()))
                .andExpect(jsonPath("$.[1].fechaNacimiento").value(personasDevueltas.get(1).getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.[1].estaTrabajando").value(personasDevueltas.get(1).getEstaTrabajando()));
    }

    @Test
    void getPersona() throws Exception {
        // --- PREPARAR (GIVEN) ---
        // Preparamos los datos de salida para el id 1 y el id 2
        Persona persona1 = new Persona(
                "1",
                "00000000A",
                "JuanAlbertoHF",
                "Youtube GitHub",
                20,
                LocalDate.of(2005, 1, 1),
                true
        );
        Persona persona2 = new Persona(
                "2",
                "11111111B",
                "Maria Luisa",
                "Gomez Sanchez",
                25,
                LocalDate.of(1995, 1, 1),
                true
        );

        // Preparamos el mock del servicio para que simule la base de datos obteniendo una persona por su identificador
        // Cuando se llame a getPersonaById(id) devuelva persona1 si el id es "1" y persona2 si el id es "2"
        when(personaService.getPersonaById("1")).thenReturn(persona1);
        when(personaService.getPersonaById("2")).thenReturn(persona2);//

        // --- EJECUTAR (WHEN) ---
        // Ejecutamos la petición HTTP simulada
        // 1. Realiza una petición GET a la ruta del controlador que devuelve la persona con el id 1
        mockMvc.perform(get("/personas/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos correctos (datos persona1)
                .andExpect(jsonPath("$.id").value(persona1.getId()))
                .andExpect(jsonPath("$.dni").value(persona1.getDni()))
                .andExpect(jsonPath("$.nombre").value(persona1.getNombre()))
                .andExpect(jsonPath("$.apellidos").value(persona1.getApellidos()))
                .andExpect(jsonPath("$.edad").value(persona1.getEdad()))
                .andExpect(jsonPath("$.fechaNacimiento").value(persona1.getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.estaTrabajando").value(persona1.getEstaTrabajando()));

        // 2. Realiza una petición GET a la ruta del controlador que devuelve la persona con el id 2
        mockMvc.perform(get("/personas/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos correctos (datos persona2)
                .andExpect(jsonPath("$.id").value(persona2.getId()))
                .andExpect(jsonPath("$.dni").value(persona2.getDni()))
                .andExpect(jsonPath("$.nombre").value(persona2.getNombre()))
                .andExpect(jsonPath("$.apellidos").value(persona2.getApellidos()))
                .andExpect(jsonPath("$.edad").value(persona2.getEdad()))
                .andExpect(jsonPath("$.fechaNacimiento").value(persona2.getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.estaTrabajando").value(persona2.getEstaTrabajando()));
    }

    @Test
    void updatePersona() throws Exception {
        // --- PREPARAR (GIVEN) ---
        // Preparamos el objeto persona que el metodo comprobara si existe para poder eliminar a la persona.
        Persona persona1 = new Persona(
                "1",
                "00000000A",
                "JuanAlbertoHF",
                "Youtube GitHub",
                20,
                LocalDate.of(2005, 1, 1),
                true
        );
        Persona persona1Modificada = new Persona(
                "1",
                "00000000B",
                "JuanAlbertoHF Modificado",
                "Youtube GitHub",
                21,
                LocalDate.of(2004, 1, 1),
                false
        );

        /* Preparamos el mock del servicio para que simule:
         * - El obtener una persona por su identificador (existe) y devuelva persona1
         * - El actualizar una persona y devuelva la persona actualizada (persona1Modificada
         */
        when(personaService.getPersonaById("1")).thenReturn(persona1);
        when(personaService.updatePersona(persona1Modificada)).thenReturn(persona1Modificada);//

        // --- EJECUTAR (WHEN) ---
        // Ejecutamos la petición HTTP simulada
        /* Realiza una petición PUT a la ruta del controlador que actualizar la persona 1, enviado el objeto por el
         * cuerpo de la petición en formato JSON (persona1Modificada), enviado el identificador por parametro y
         * verificando que el estado dé la respuesta sea 200 OK y que la respuesta JSON contenga los datos correctos
         * de la persona actualizada. */
        mockMvc.perform(put("/personas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos el formato de la peticion (datos que enviamos)
                        .accept(MediaType.APPLICATION_JSON) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                .content(objectMapper.writeValueAsString(persona1Modificada))) // Convertimos el objeto a JSON
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verificamos que la respuesta JSON contenga los datos modificados
                // Datos persona 1
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.dni").value(persona1Modificada.getDni()))
                .andExpect(jsonPath("$.nombre").value(persona1Modificada.getNombre()))
                .andExpect(jsonPath("$.apellidos").value(persona1Modificada.getApellidos()))
                .andExpect(jsonPath("$.edad").value(persona1Modificada.getEdad()))
                .andExpect(jsonPath("$.fechaNacimiento").value(persona1Modificada.getFechaNacimiento().toString()))
                .andExpect(jsonPath("$.estaTrabajando").value(persona1Modificada.getEstaTrabajando()));
    }

    @Test
    void deletePersona() throws Exception {
        // --- PREPARAR (GIVEN) ---
        // Preparamos el objeto persona que el metodo comprobara si existe para poder eliminar a la persona.
        Persona persona1 = new Persona(
                "1",
                "00000000A",
                "JuanAlbertoHF",
                "Youtube GitHub",
                20,
                LocalDate.of(2005, 1, 1),
                true
        );

        /* Preparamos el mock del servicio para que simule el devolver una persona (existe) y ejecute el metodo
         * deletePersona sin hacer nada (doNothing), ya que es un metodo void. */
        when(personaService.getPersonaById("1")).thenReturn(persona1);
        doNothing().when(personaService).deletePersona("1");

        // --- EJECUTAR (WHEN) ---
        // Ejecutamos la petición HTTP simulada
        // Realiza una petición con el met0do delete a la ruta del controlador que elimina la persona con el id 1
        mockMvc.perform(delete("/personas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos el formato de la peticion (datos que enviamos)
                        .accept(MediaType.APPLICATION_JSON)) // Indicamos el formato de la respuesta que esperamos (datos que recibimos)
                // --- VERIFICAR (THEN)  ---
                // Verificamos que el estado dé la respuesta 200 (OK) y devuelva false (se ha ejecutado correctamente)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}