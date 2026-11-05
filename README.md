# AD04_TareaEvaluativa

## Gestión de actividades y reservas · ACTIVA

Aplicación Java de consola para gestionar monitores, actividades, clientes y reservas mediante JPA e Hibernate, utilizando MySQL como sistema de almacenamiento.

## Tecnologías utilizadas

- Java 21
- Maven
- Hibernate ORM 6.6.18.Final
- Jakarta Persistence 3.1.0
- MySQL
- MySQL Connector/J 8.4.0

## Estructura del proyecto

- `src/main/java/es/activa/config`: configuración de la persistencia.
- `src/main/java/es/activa/domain`: entidades Monitor, Actividad, Cliente y Reserva.
- `src/main/java/es/activa/service`: servicios y operaciones de gestión.
- `src/main/java/es/activa/main`: menú principal de la aplicación.
- `src/main/resources/META-INF`: configuración de JPA.
- `sql`: script SQL de la base de datos.
- `pom.xml`: configuración del proyecto y dependencias Maven.

## Base de datos

La aplicación utiliza el esquema MySQL `dbactiva`.

Antes de ejecutar el programa:

1. Iniciar el servidor MySQL.
2. Ejecutar una sola vez el archivo `sql/dbactiva.sql` sobre una base de datos vacía.
3. Comprobar que se han creado las tablas y los datos iniciales.

La aplicación utiliza `hibernate.hbm2ddl.auto=validate` para comprobar que el mapeo de las entidades coincide con la estructura existente, sin crear ni modificar las tablas.

## Configuración de la conexión

Por seguridad, el archivo `persistence.xml` con las credenciales locales no se incluye en el repositorio.

Para configurar la conexión:

1. Copiar `persistence.xml.example` dentro de `src/main/resources/META-INF`.
2. Renombrar la copia como `persistence.xml`.
3. Configurar el usuario y la contraseña de MySQL en las propiedades correspondientes.
4. Comprobar que la URL de conexión apunta al esquema `dbactiva`.

## Ejecución del proyecto

1. Abrir el proyecto como proyecto Maven en Eclipse.
2. Actualizar las dependencias Maven si es necesario.
3. Ejecutar `clean verify` para comprobar la compilación.
4. Ejecutar la clase `es.activa.main.Main` como Java Application.

## Funcionalidades

El menú principal permite:

1. Crear monitores.
2. Crear actividades.
3. Buscar actividades disponibles por localidad, fecha y plazas.
4. Crear clientes.
5. Crear reservas.
6. Cancelar reservas.
7. Listar todas las actividades.
8. Salir de la aplicación.

Las operaciones se realizan mediante JPA y utilizan transacciones para mantener la integridad de los datos.

## Persistencia

Los datos se almacenan en MySQL y permanecen disponibles después de cerrar y volver a iniciar la aplicación.

## Autora

Laura Mediavilla Valdelvira

Desarrollo de Aplicaciones Multiplataforma  
Módulo: Acceso a Datos  
Curso 2026/2027