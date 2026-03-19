package me.juanalbeticohf.apirest_basica_mongodb;

import me.juanalbeticohf.apirest_basica_mongodb.entity.Persona;
import me.juanalbeticohf.apirest_basica_mongodb.service.PersonaService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Scanner;

@SpringBootApplication
public class ApirestBasicaMongoDBApplication implements CommandLineRunner {

	@Autowired
	PersonaService personaService;

	private static final Logger logger =  LoggerFactory.getLogger(ApirestBasicaMongoDBApplication.class);

	final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(ApirestBasicaMongoDBApplication.class, args);
	}

	@Override
	public void run(String @NonNull ... args) {
		menu();
	}

	/**
	 * Menu de opciones para gestionar personas en la base de datos. <br>
	 * Permite crear, listar, buscar por ID, actualizar y eliminar personas.
	 */
	private void menu() {
		while (true) {
			System.out.print("""
				\n----- MENU PERSONAS -----
				1. Crear Persona
				2. Listar Personas
				3. Buscar Persona por ID
				4. Actualizar Persona
				5. Eliminar Persona
				0. Salir
				-------------------------
				Elige una opción:\s""");

			try {
				int opcion = Integer.parseInt(sc.nextLine());

				switch (opcion) {
					case 1 -> crearPersona();
					case 2 -> listarPersonas();
					case 3 -> buscarPersonaPorID();
					case 4 -> actualizarPersona();
					case 5 -> eliminarPersona();
					case 0 -> {
						logger.info("Saliendo del programa...");
						return;
					}
					default -> logger.warn("Opción no válida. Por favor, elige una opción del menú.");
				}
			} catch (NumberFormatException e) {
				logger.warn("Entrada no válida. Por favor, introduce un número.");
            }
		}
	}

	/**
	 * Opcion del Menu para crear una persona en la base de datos. <br>
	 * Solcita por consola los datos necesarios para crear una persona, crea el objeto 'Persona' y lo intenta añadir a
	 * la base de datos. Si la persona se crea exitosamente, se imprimira por logs el resultado.
	 */
	private void crearPersona() {
		logger.info("Opcion 'Crear Persona' seleccionada...");

		// Datos solicitados por consola
		System.out.print("Introduce el DNI de la persona: ");
		String dni = sc.nextLine();

		System.out.print("Introduce el Nombre de la persona: ");
		String nombre = sc.nextLine();

		System.out.print("Introduce el Apellido de la persona: ");
		String apellido = sc.nextLine();

		System.out.print("Introduce la Edad de la persona: ");
		int edad = Integer.parseInt(sc.nextLine());

		System.out.print("Introduce la Fecha de Nacimiento de la persona (YYYY-MM-DD): ");
		LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine());

		System.out.print("¿La persona está trabajando actualmente? (true/false): ");
		boolean estaTrabajando = Boolean.parseBoolean(sc.nextLine());

		Persona persona = new Persona(null, dni, nombre, apellido, edad, fechaNacimiento, estaTrabajando);

		// Creacion de la persona en la BD
		Persona personaRes = personaService.addPersona(persona);

		// Si 'personaRes' no es null, se ha creado correctamente y se muestra por consola
		if (personaRes != null) {
			logger.info("Persona creada exitosamente: {}", personaRes);
		} else {
			logger.warn("La persona no se ha creado");
		}
	}

	/**
	 * Opcion del Menu para mostrar el listado de todas las personas almacenadas en la base de datos.
	 */
	private void listarPersonas() {
		logger.info("Opcion 'Listar Personas' seleccionada...");

		personaService.getAllPersonas().forEach(persona -> System.out.println("- "+persona));
	}

	/**
	 * Opcion del Menu para buscar una persona base a su identificador en la base de datos. <br>
	 * Solicita por consola el identificador y busca la persona con el identificador proporcionado en la base de datos.
	 * Si la busqueda es exitosa, se muestra por consola la persona encontrada. Si no se encuentra ninguna persona con
	 * el identificador proporcionado, se muestra un mensaje de advertencia por consola.
	 */
	private void buscarPersonaPorID() {
		logger.info("Opcion 'Buscar Persona por ID' seleccionada...");

		System.out.print("Introduce el ID de la persona a buscar: ");
		String id = sc.nextLine();

		Persona personaRes = personaService.getPersonaById(id);

		// Si 'personaRes' no es null, se ha encontrado la persona y se muestra por consola
		if (personaRes != null) {
			logger.info("Persona encontrada: \n- {}", personaRes);
		} else {
			logger.warn("No se encontró ninguna persona con el ID proporcionado.");
		}
	}

	/**
	 * Opcion del Menu para actualizar los datos de una persona. <br>
	 * Solicita por consola introducir los datos que se desean actualizar, si el usuario omite un campo (ENTER) se
	 * conserva el valor actual de dicho campo. Si la actualización es exitosa, se muestra por consola la persona actualizada.
	 * Si no se encuentra ninguna persona con el identificador proporcionado, se muestra un mensaje de advertencia por consola.
	 */
	private void actualizarPersona() {
		logger.info("Opcion 'Actualizar Persona por ID' seleccionada...");

		System.out.print("Introduce el ID de la persona a actualizar: ");
		String id = sc.nextLine();

		Persona persona = personaService.getPersonaById(id);

		if (persona != null) {
			System.out.println("Persona encontrada...");

			System.out.print("Introduce el DNI de la persona ("+persona.getDni()+"): ");
			String dni = sc.nextLine();

			if (!dni.isEmpty()) {
				persona.setDni(dni);
			}

			System.out.print("Introduce el Nombre de la persona ("+persona.getNombre()+"): ");
			String nombre = sc.nextLine();

			if (!nombre.isEmpty()) {
				persona.setNombre(nombre);
			}

			System.out.print("Introduce el Apellido de la persona ("+persona.getApellidos()+"): ");
			String apellidos = sc.nextLine();

			if (!apellidos.isEmpty()) {
				persona.setApellidos(apellidos);
			}

			System.out.print("Introduce la Edad de la persona ("+persona.getEdad()+"): ");

			String edadStr = sc.nextLine();
			if (!edadStr.isEmpty()) {
				int edad = Integer.parseInt(edadStr);
				persona.setEdad(edad);
			}

			System.out.print("Introduce la Fecha de Nacimiento de la persona ("+persona.getFechaNacimiento()+") (YYYY-MM-DD): ");
			String fechaNacimientoStr = sc.nextLine();

			if (!fechaNacimientoStr.isEmpty()) {
				LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
				persona.setFechaNacimiento(fechaNacimiento);
			}

			System.out.print("¿La persona está trabajando actualmente? ("+persona.getEstaTrabajando()+") (true/false): ");
			String estaTrabajandoStr = sc.nextLine();

			if (!estaTrabajandoStr.isEmpty()) {
				boolean estaTrabajando = Boolean.parseBoolean(estaTrabajandoStr);
				persona.setEstaTrabajando(estaTrabajando);
			}

			Persona personaRes = personaService.updatePersona(persona);

			// Si 'personaRes' no es null, se ha actualizado correctamente y se muestra por consola
			if (personaRes != null) {
				logger.info("Persona actualizada exitosamente: {}", personaRes);
			} else {
				logger.warn("La persona no se ha podido actualizar");
			}
		} else {
			logger.warn("La persona no se ha encontrado");
		}
	}

	/**
	 * Opcion del Menu para eliminar una persona de la base de datos. <br>
	 * Solicita por consola el identificador de la persona a eliminar, si se encuentra una persona con el identificador
	 * proporcionado, se elimina de la base de datos y se muestra un mensaje
	 */
	private void eliminarPersona() {
		logger.info("Opcion 'Eliminar Persona' seleccionada...");

		System.out.print("Introduce el ID de la persona a eliminar: ");
		String id = sc.nextLine();

		Persona personaRes = personaService.getPersonaById(id);

		// Si 'personaRes' no es null, se ha encontrado la persona y se procede a eliminarla, mostrando un mensaje de éxito por consola
		if (personaRes != null) {
			personaService.deletePersona(id);
			logger.info("Persona eliminada exitosamente.");
		} else {
			logger.info("No se encontró ninguna persona con el ID proporcionado.");
		}
	}

}
