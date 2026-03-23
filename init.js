// Seleccionamos el nombre de la base de datos
db = db.getSiblingDB('apirest-mongo-docker');

// Creamos la colección con su esquema de validación
db.createCollection("prac_personas", {
    validator: {
        $jsonSchema: {
            title: 'Library.books',
            bsonType: 'object',
            required: [
                '_id',
                'dni',
                'nombre',
                'apellidos',
                'edad',
                'fecha_nac'
            ],
            properties: {
                _id: {
                    bsonType: 'objectId'
                },
                _class: {
                    bsonType: 'string'
                },
                dni: {
                    bsonType: 'string',
                    description: 'Documento de Identidad dela persona'
                },
                nombre: {
                    bsonType: 'string',
                    description: 'Nombre de la persona'
                },
                apellidos: {
                    bsonType: 'string',
                    description: 'Apellidos de la persona'
                },
                edad: {
                    bsonType: 'int',
                    description: 'La edad de la persona'
                },
                fecha_nac: {
                    bsonType: 'date',
                    description: 'La fecha de nacimiento de la persona'
                },
                esta_trabajando: {
                    bsonType: 'bool',
                    description: 'La fecha de nacimiento de la persona'
                }
            },
            additionalProperties: false
        }
    }
});