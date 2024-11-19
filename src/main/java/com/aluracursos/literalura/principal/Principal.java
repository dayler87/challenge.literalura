package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.AutorService;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConvierteDatos;

import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component

public class Principal {
    private final LibroService libroService;
    private final AutorService autorService;

    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;

    Scanner scanner = new Scanner(System.in);

    public Principal(LibroService libroService, AutorService autorService, LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    @Autowired


    public void menuPrincipal() throws IOException, InterruptedException {
        System.out.println("Bienvenido");

        int opcion = -1;
        while (opcion != 0) {
            try {
                System.out.println("Selecciona la opcion que deseas ejecutar");
                System.out.println("""
                        -1. Buscar libro por titulo.
                        -2. Listar libros registrados.
                        -3. Listar autores registrados.
                        -4. Listar autores vivos en un determinado año.
                        -5. Listar libros por idioma.
                        -0. salir
                        """);

                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer del scanner


            } catch (Exception e) {
                System.out.println("Por favor ingresa un dato valido ");
                scanner.nextLine();

            }
            if (opcion == 0) {
                break;
            }
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();//funcion para obtener el primer libro de busqueda por titulo
                    break;
                case 2:
                    ListarLibrosRegistrados();//funcion listar libros registrados
                    break;
                case 3:
                    autoresRegistrados();  // funcion listar autores registrados
                    break;
                case 4:
                    autoresVivosPorAnho(); //funcion mostrar autores vivos por anho
                    break;
                case 5:
                    buscarLibrosPorIdioma();//funcion buscar libros por idioma
                    break;
                default:
                    System.out.println("Has ingresado una opción NO válida\n"); // opcion por defecto
            }
        }
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException {

        ConsumoApi consumoApi = new ConsumoApi();
        ConvierteDatos convierte = new ConvierteDatos();

        System.out.println("Ingrese el titulo del libro que desea buscar:");
        String titulo = scanner.nextLine();

        var tituloBuscado = consumoApi.consultaApi(titulo.toLowerCase().replace(" ", "+"));
        Datos datosLibro = convierte.obtenerDatos(tituloBuscado, Datos.class);

        // Obtener el primer libro de los resultados
        Optional<Libro> primerLibro = datosLibro.datosLibro().stream()
                .findFirst()
                .map(l -> {
                    Libro libro = new Libro();
                    libro.setIdApi(l.idApi());
                    libro.setTitulo(l.titulo());
                    libro.setIdiomas(l.idiomas());
                    libro.setDescargas(l.descargas());

                    // Verificar si el autor ya está en la base de datos
                    Autor autor = new Autor();
                    autor.setNombre(l.datosAutor().get(0).nombreAutor());
                    autor.setFechaDeNacimiento(l.datosAutor().get(0).fechaDeNacimiento());
                    autor.setFechaDeFallecimiento(l.datosAutor().get(0).fechaDeFallecimiento());

                    // Intentar encontrar el autor en la base de datos
                    Optional<Autor> autorExistente = autorService.findByNombre(autor.getNombre());
                    if (autorExistente.isPresent()) {
                        libro.setAutor(autorExistente.get());  // Usar el autor existente
                    } else {
                        libro.setAutor(autor);  // Usar el nuevo autor
                    }
                    return libro;
                });

        if (primerLibro.isPresent()) {
            Libro libro = primerLibro.get();

            // Validar si el libro ya está guardado en la base de datos
            boolean libroExistente = libroService.existsByIdApi(libro.getIdApi());

            if (libroExistente) {
                System.out.println("Este libro ya está registrado en la base de datos.");
            } else {
                // Guardar el autor si no existe
                if (!autorService.findByNombre(libro.getAutor().getNombre()).isPresent()) {
                    autorService.guardar(libro.getAutor());
                }
                // Guardar el libro en la base de datos
                System.out.println(libro);
                libroService.guardar(libro);
            }
        }
    }

    private void ListarLibrosRegistrados() {

        var librosRegistados = repositorioLibro.findAllLibros();
        System.out.println("\n********** Libros Registrados**********\n");
        System.out.println(librosRegistados);
    }

    private void autoresRegistrados() {
        List<Autor> autores = repositorioAutor.findAll();// Obtener todos los autores

        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private void autoresVivosPorAnho() {
        Autor autor;
        System.out.println("Aqui encontraras los autores vivos en un año especifico");
        System.out.println("Por favor ingresa el año que deseas consultar:");
        Integer busquedaFecha = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autoresVivos = repositorioAutor.autoresVivosPorAnho(busquedaFecha);
        if (!autoresVivos.isEmpty()) {
            autoresVivos.forEach(a -> System.out.println("******************** Autores Vivos ********************\n" + a.toString()
                    + "\n**************************************************"));
        } else {
            System.out.println("**************************************************\n" +
                    "No se encontraron autores vivos en el año:" + busquedaFecha +
                    "\n**************************************************");
        }

    }

    public void buscarLibrosPorIdioma() {
        System.out.println("Por favor ingresa el idioma en el cual quieres ver tus libros\n");
        System.out.println("""
                Estos son algunos ejemplos\
                
                es- Español\s
                en- English
                fr- Frances
                pt= Portugues
                """
        );
        String codigoIdioma = validarEntrada(scanner);


        List<Libro> librosFiltrados = libroService.obtenerLibrosPorIdioma(codigoIdioma);

        if (librosFiltrados.isEmpty()) {
            System.out.println("""
                    *******************************************************
                    No se encontraron libros en el idioma solicitado.
                    \n*******************************************************
                    """);
        } else {
            System.out.println("****************************************************\n");
            librosFiltrados.forEach(System.out::println);
            System.out.println("****************************************************\n");
        }
    }

    public static String validarEntrada(Scanner teclado) {
        String regex = "^[a-zA-Z]{2}$"; // Expresión regular para verificar 3 letras
        String codigoIdioma;

        while (true) {
            codigoIdioma = teclado.nextLine(); // Leer nueva entrada del usuario

            if (codigoIdioma.matches(regex)) { // Validar la entrada
                codigoIdioma = codigoIdioma.toLowerCase(); // Convertir a mayúsculas
                System.out.println("Entrada válida: " + codigoIdioma);
                return codigoIdioma; // Retornar la entrada válida
            } else {
                System.out.println("Error: El código debe contener exactamente 2 letras.");
                System.out.println("Por favor intente de nuevo:");
            }
        }
    }

}



