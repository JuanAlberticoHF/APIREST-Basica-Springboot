DROP DATABASE IF EXISTS prac_personas;

CREATE DATABASE prac_personas;

USE prac_personas;

CREATE TABLE personas (
    id INT NOT NULL auto_increment,
    DNI CHAR(9) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR (100) NOT NULL,
    edad INT NOT NULL,
    fecha_nac DATE NOT NULL,
    esta_trabajando BOOL,
    PRIMARY KEY (id)
);

INSERT INTO personas (DNI, nombre, apellidos, edad, fecha_nac, esta_trabajando) VALUES
    ('12345678A', 'Carlos', 'García Pérez', 30, '1996-05-14', TRUE),
    ('87654321B', 'Lucía', 'Fernández Ruiz', 25, '2001-11-20', FALSE),
    ('11223344C', 'Mateo', 'López Castro', 42, '1984-02-03', TRUE),
    ('55667788D', 'Elena', 'Sánchez Mora', 19, '2007-08-12', FALSE),
    ('99887766E', 'Javier', 'Martín Soler', 55, '1971-12-30', TRUE);