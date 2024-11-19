package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public void guardar(Libro libro) {
        // Verifica si ya existe un libro con el mismo id_api
        Optional<Libro> libroExistente = libroRepository.findByIdApi(libro.getIdApi());

        if (libroExistente.isPresent()) {
            System.out.println("\nEl libro que buscaste ya se encuentra registrado\n" +
                    "Puedes buscar otro libro :)\n");


        } else {
            // Si no existe, entonces guarda el libro
            libroRepository.save(libro);
            System.out.println("\nEl libro se ha guardado con éxito :)\n");
        }
    }

    public boolean existsByIdApi(Long idApi) {
        return libroRepository.existsById(idApi); // Asumiendo que `idApi` es el identificador único
    }
    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findAllLibros();
        List<Libro> librosFiltrados = new ArrayList<>();

        for (Libro libro : libros) {
            for (String idiomaLibro : libro.getIdiomas()) {

                if (idiomaLibro.toLowerCase().contains(idioma.toLowerCase()))
                { librosFiltrados.add(libro);
                    break;
                }
            }
        }

        return librosFiltrados;
    }


}
