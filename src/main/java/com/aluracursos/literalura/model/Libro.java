package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idApi;
    private String titulo;
    private List<String> idiomas;
    private double descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(DatosLibro datosLibro) {
        this.idApi = datosLibro.idApi();
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.datosAutor().get(0));
        this.idiomas = datosLibro.idiomas();
        this.descargas = datosLibro.descargas();
    }

    public Libro() {

    }

    public Long getIdApi() {
        return idApi;
    }

    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public double getDescargas() {
        return descargas;
    }

    public void setDescargas(double descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n***************Datos Libro***************" +
                "\ntitulo = " + titulo +
                "\nautor = " + autor.getNombre() +
                "\ndescargas = " + descargas +
                "\nidiomas + " + idiomas +
                "\n__________________________________________";
    }
}
