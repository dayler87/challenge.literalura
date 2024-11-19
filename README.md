# Literalura

Esta aplicación permite consultar libros utilizando la API de Gutendex y gestionar información relacionada con libros y autores almacenada en una base de datos PostgreSQL. Está desarrollada con **Spring Framework** y ofrece funcionalidades como la búsqueda de libros, la consulta de autores registrados, y la filtración de datos específicos.  

## Características  

- **Consulta de libros**: Permite buscar libros disponibles en la API de Gutendex mediante el menú interactivo.  
- **Gestión de datos locales**: Utiliza JPA para gestionar datos en una base de datos PostgreSQL, permitiendo:  
  - Obtener la lista de libros registrados.  
  - Consultar autores registrados.  
  - Filtrar autores vivos en un año específico.  
  - Buscar libros por un idioma específico.  

## Requisitos Previos  

1. **Java**: JDK 17 o superior.  
2. **Spring Framework**: Configurado en el proyecto.  
3. **Base de datos PostgreSQL**: Instalada y configurada.  
4. **Conexión a Internet**: Para acceder a la API de Gutendex.  

## Instalación  

1. **Clona el repositorio**:  
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd <NOMBRE_DEL_PROYECTO>
Configura la base de datos:

Crea una base de datos PostgreSQL.
Actualiza las credenciales en el archivo application.properties:
properties
Copiar código
```
spring.datasource.url=jdbc:postgresql://<HOST>:<PUERTO>/<NOMBRE_BD>
spring.datasource.username=<USUARIO>
spring.datasource.password=<CONTRASEÑA>
```
Ejecuta la aplicación:

Utiliza tu IDE (como IntelliJ o VSCode) para ejecutar la clase principal.

Uso
Inicia la aplicación.
Selecciona una opción del menú:
```bash
Selecciona la opcion que deseas ejecutar
-1. Buscar libro por titulo.
-2. Listar libros registrados.
-3. Listar autores registrados.
-4. Listar autores vivos en un determinado año. 
-5. Listar libros por idioma.
-0. salir
```
la opcion 1 permitira buscar un libro perteneciente al proyecto Gutenberg, ingresa el titulo de un libro y si existe la aplicación te mostrará el primer registro que coincida con el texto buscado, en el siguiente ejemplo se muestra la busqueda del libro Crimen y castigo de Fyodor Dostoyevsky y su respesctivo resultado
**Ejemplo**
```bash
1
Ingrese el titulo del libro que desea buscar:
crimen y castigo

***************Datos Libro***************
titulo = El crimen y el castigo
autor = Dostoyevsky, Fyodor
descargas = 1418.0
idiomas + [es]
__________________________________________
```
EL programa tambien informa si el libro ya se encuentra registrado en la base de datos o si se agregó con éxito a la misma.
```
El libro que buscaste ya se encuentra registrado
Puedes buscar otro libro :)

```
Este es un ejemplo de la salida al listar los libros registrados
``` bash
********** Libros Registrados**********

[
***************Datos Libro***************
titulo = Don Quijote
autor = Cervantes Saavedra, Miguel de
descargas = 12877.0
idiomas + [es]
__________________________________________, 
***************Datos Libro***************
titulo = Pride and Prejudice
autor = Austen, Jane
descargas = 58812.0
idiomas + [en]
__________________________________________, 
***************Datos Libro***************
titulo = Emma
autor = Austen, Jane
descargas = 5798.0
idiomas + [en]
__________________________________________]
```
## Tecnologías Utilizadas
1. Spring Framework: Para la gestión de controladores, servicios y acceso a datos.
2. PostgreSQL: Como base de datos relacional.
3. JPA: Para la persistencia y consulta de datos.
4. Gutendex API: Para la consulta de libros en línea.


