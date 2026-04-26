# Refactorización API

## Introducción
En este proyecto se ha llevado a cabo la creación de una API RESTful utilizando Spring Boot. La API tiene
aspecto a mejorar como:
- Controlador con logica de negocio: El controlador no debe contener logica de negocio (condicionales,
  validaciones, etc), sino que esa responsabilidad debe delegarse a la capa de servicio. La mejora consiste
  en refactorizar el controlador eliminando la logica de negocio y que las validaciones y condicionales se
  deleguen al servicio correspondiente.
- La validación de datos: La validación de datos se ha manejado con logica de negocio en el controlador,
  por lo que para mejorar este aspecto se utilizaran anotaciones de validación de Spring (como @Valid)
  para validar los datos de entrada en el controlador.
- Manejo de excepciones: Spring Boot tiene un mecanismo para manejar excepciones específicas de forma global lanzadas 
  durante la ejecución de una API, centralizando el manejo de excepciones, devolviendo errores JSON y reduciendo los errores
  repetidos. Este mecanismo se desarrolla creando una clase con la anotación `@RestControllerAdvice`,
  y `@ExceptionHandler(excepcion.class)`.

## Pasos de la refactorización
1. Definir las excepciones y codigos que devolveran: Analizando todos los endpoints del controlador se ha distinguido 4
excepciones principales:
   - `PersonaAlreadyExistsException`: **La persona ya existe** en la base de datos. Se lanzará solo en metodos **POST** y 
   devuelve codigo `409 Conflict`.
   - `PersonaNotFoundException`: **La persona no existe** en la base de datos. Se lanzará cuando se quiere buscar una 
   persona que no existe en la base de datos, en metodos **GET**, **PUT** y **DELETE** al buscar por ID y devolvera el codigo 
   `404 Not Found`.
   - `InvalidIdException`: El identificardor es nulo o no válido. Se lanzara cuando el id no sea valido, en los metodos
     **GET**, **PUT** y **DELETE** al operar con los ID y devolvera el codigo `400 Bad Request`
   - `NotMatchIdsException`: El identificador proporcionado y de la persona no coinciden. Se lanzara solo en metodos
   **PUT** que necesitan que el id por parametro y el de la persona en el cuerpo coincidan, devolviendo el codigo `409 
   Conflict`.
2. `@Valid` permite realizar validaciones de las peticiones que reciba el controlador, validando la entrada con la entidad
de la base de datos. ``@Valid`` puede lanzar 3 excepciones:
   - `@MethodArgumentNotValidException`: Cuando un  argumento o parametro no es valido (capa web)
   - `@ConstraintViolationException`: Cuando varios argumentos no son  correctos (capa web) o al validar un registro que 
   no es correcto base entidad (Spring Data JPA/Hibernate).
   - `@DataIntegrityViolationException`: Ocurre cuando la base de datos emite un error. 
3. Migración de MongoDB a MySQL:
   - [X] Cambiar los nombres de las clases, paquetes y proyecto de MongoDB a MySQL.
   - [X] Añadir la dependencia de Spring Data JPA y eliminar Spring Data MongoDB.
   - [X] Añadir la configuración de Spring Data JPA en la ``application.properties``, eliminar la de MongoDB y añadir una
   linea de configuración para utilizar una base de datos local.
   - [ ] Cambiar la imagen de Docker para utilizar una base de datos MySQL.
   - [ ] Definir create-drop en la creación del esquema de Hibernate.
4. Implementar ``@Valid``:
   - Fuente: https://dev.to/gianfcop98/spring-boot-and-validation-a-complete-guide-with-valid-and-validated-471p
   - Añadir depedencia:
     ```xml
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
     </dependency>
     ```
   - Definir las validaciones en la entidad y los DTOs:
     - ID: No debe ser nulo (`@NotNull`)
     - DNI: No debe ser nulo ni estar vacio, con una logintud minima y maxima de 9. (`@NotBlank`, `@Size(min=9, max=9)`)
     - Nombre: No debe ser nulo ni estar vacio, con una longitud maxima de 50 (`@NotBlank`, `@Size(max=50)`)
     - Apellidos: No debe ser nulo ni estar vacio, con una longitud maxima de 100 (`@NotBlank`, `@Size(max=100)`)
     - Edad: No debe ser nulo, con un valor maximo de 99 (`@NotNull`, `@Max(99)`, `@Positive`)
     - Fecha Nacimiento: No debe ser nulo, con valores anteriores a hoy (`@NotNull`, `@Past`)
     - Esta Trabajando: Puede ser nulo y de cualquier valor booleano.
     Definir una descripción a cada validación con el parametro `message`.

   - Añadir @Validated en la clase del controlador para poder validar los parametros sueltos.
   - Añadir @Valid a los objetos personas y DTO's.

5. Reemplazar todos los objetos persona de las peticiones al servidor por objetos persona DTO. El DTO seleccionado es
   PersonaDTOID. Definir un constructor en la clase Persona que reciba un objeto PersonaDTO, facilitando la conversión.
6. Crear el paquete exception y custom_exception donde crear todas las excepciones personalizadas.
7. Crear la clase GlobalExceptionHandler donde se definiran todas las excepciones.
8. Refactorizar el controlador eliminando toda la logica de negocio y delegando las validaciones y condicionales a la 
capa de servicio, a su vez lanzando excepciones.
9. Modificar la documentación de la API para reflejar los cambios realizados en la refactorización.
10. Realizar pruebas para verificar que la API sigue funcionando correctamente después de la refactorización.
    - POST /personas/: Crear una nueva persona.
    - GET /personas/: Obtener una lista de todas las personas.
    - GET /personas/alternativa: Obtener una lista de todas las personas con o sin el campo estaTrabajando.
    - GET /personas/{id}: Obtener una persona por su ID.
    - PUT /personas/{id}: Actualizar una persona existente por su ID.
    - DELETE /personas/{id}: Eliminar una persona por su ID.
11. Actualizar la configuracion de Docker para utilizar una base de datos MySQL en lugar de MongoDB, que incluye:
    - Modificar cualquier referencia a MongoDB por MySQL, incluyendo el nombre del servicio..
    - Sustituir la imagen de MongoDB por una imagen de MySQL en la configuración de docker-compose.yml.
    - Configurar las variables de entorno necesarias para la conexión a MySQL (contraseña).
    - Configurar el puerto de MySQL en lugar del puerto de MongoDB.
    - Establecer la nueva ruta del volumen para persistir los datos de MySQL.
    - Definir una verificación de salud para MySQL, asegurando que el contenedor de MySQL esté listo antes de iniciar la API.
    - Modificar el enlace de conexión del contenedor de la API apuntando al servicio de MySQL.
    - Establecer la condición de dependencia en el servicio de la API para que espere a que MySQL esté listo antes de iniciar.
    
    Comandos utilizados:
    ```bash
    # Construir los contenedores y regenerar la imagenes.
    docker-compose up -d --build
    ```
    
     ```bash
     # Eliminar los contenedores y volumenes para limpiar la configuración anterior.
     docker-compose down -v
     ```

      ```bash
      # Verificar los logs de los contenedores para asegurarse de que no hay errores.
      docker-compose logs apirest-basica-mysql2
      docker-compose logs apirest-mysql-docker
      ```
12. Versionar todos los cambios realizados en el proyecto utilizando Git y subirlo al repositorio con un nuevo tag.
    - Refactorización del nombre del proyecto, paquetes y clases de MongoDB a MySQL.
    - Migración de la base de datos.
    - Implementación del manejador de excepciones gobales.
    - Refactorización del controlador y servicio, además de las validaciones y documentación.
    - Actualización de la configuración de Docker para utilizar MySQL.