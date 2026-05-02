# APIREST Basica MySQL
Es un proyecto de prueba para practicar el desarrollo de una API RESTful en Spring Boot desde 0 con el objetivo de escalar el proyecto añadiendo funcionalidades, implementaciones y el uso de herramientas estandar para el desarrollo de APIs.

## ✅ OBJETIVOS CUMPLIDOS
Objetivos de desarrollo cumplidos en orden y destacando los aspectos mas relevantes de cada objetivo.
### 📌 Desarrollar una API RESTful Basica
> **TAG:** `API-RESTful-Basica-MySQL` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/API-RESTful-Basica-MySQL)
  - MySQL como forma de persistencia creando la BD con script SQL desde MySQL Workbench.
  - Configuración del `application.properties`.
  - Desarrollando una entidad, un repositorio, un servicio y un controlador.
  - Definición de los DTOs.
  - Documentación con JavaDoc.
  - Pruebas API con Postman.
  - Versionado con Git utilizando la metodologia "*Convetional Commits*".
---
### 📌 Migración de la forma de persistencia de MySQL a MongoDB
> **TAG:** `Migración-MongoDB` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/Migraci%C3%B3n-MongoDB)
  - Eliminación de las dependencias de Spring Data JPA.
  - Implementación de la dependencias de Spring Data MongoDB.
  - Refactorización de la entidad y repositorio.
---
### 📌 Documentación de la API con Swagger
> **TAG:** `Documentación-OpenAPI&Swagger` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/Documentaci%C3%B3n-OpenAPI%26Swagger)
  - Implementación de las anotaciones de OpenAPI en el controlador de la API.
  - Generación de la documentación con Swagger UI.
  - Pruebas de la API desde Swagger.
---
### 📌 Menu CLI con CommandLineRunner
> **TAG:** `MenuCommandLineRunner` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/MenuCommandLineRunner)
  - Desarrollo Menu CLI.
  - Implementación función CommandLineRunner.
---
### 📌 Migración entorno de MongoDB a Docker
> **TAG:** `MongoDB-Docker` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/MongoDB-Docker)
  - Creación del fichero `docker-compose.yml` definiendo el servicio del contenedor con la imagen de MongoDB.
  - Creación fichero de inicialización de la BD de MongoDB (*schema-validations*).
  - Configuracion de la nueva URI de MongoDB en el fichero `application.properties`.
---
### 📌 Dockerización de la API
> **TAG:** `APIREST-Dockerizada` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/APIREST-Dockerizada)
  - Creación `DockerFile` para la dockerización del proyecto.
  - Configuración del fichero `docker-compose.yml` definiendo el servicio del contenedor de la API y la comunicación entre contenedores.
  - Compilación y empaquetado del `.jar` de la API.
  - Pruebas de la API Dockerizada desde Postman.
---
### 📌 Migración MongoDB a MySQL, refactorización API implementando validaciones y un manejador global de excepciones
> **TAG:** `API-RESTful-Basica-MySQL2-Validations-Docker` [🏷️](https://github.com/JuanAlberticoHF/apirest-basica-mysql/tree/API-RESTful-Basica-MySQL2-Validations-Docker)
  - Eliminación de las dependencias de Spring Data MongoDB.
  - Implementación de la dependencias de Spring Data JPA.
  - Refactorización de la entidad y repositorio.
  - Desarrollo del manejador global de excepciones y excepciones personalizadas.
  - Definición de las validaciones en la entidad, objetos DTO y controlador.
  - Refactorización del controlador y servicio de la API eliminando toda la logica de negocio del controlador y lanzando excepciones desde el servicio.

## 🟨 OBJETIVOS EN CURSO
Objetivos de desarrollo en curso.
- Desarrollo de las pruebas unitarias y de integración de la API (rama `feature/tests`)

## 📋 OBJETIVOS FUTUROS
Objetivos de desarrollo en curso todavia pendientes.
- Implementación Spring Security.
