package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {

        this.autorRepository = autorRepository;
    }

    public void guardar(Autor autor) {
        autorRepository.save(autor);
    }

    public Optional<Autor> findByNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }
}
