package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    Long id;

    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombreAutor();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    public Autor() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    @Override
    public String toString() {
        // Construir el nombre del autor y sus libros
        StringBuilder sb = new StringBuilder();
        sb.append("******************** Autor **************************\n");
        sb.append("Autor: ").append(nombre).append("\n");
        sb.append("Fecha de nacimiento ").append(fechaDeNacimiento).append("\n");
        sb.append("Fecha de Fallecimiento ").append(fechaDeFallecimiento).append("\n");

        // Solo mostrar los títulos de los libros
        if (libros != null && !libros.isEmpty()) {
            sb.append("Libros escritos: ");
            for (Libro libro : libros) {
                sb.append(libro.getTitulo()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        } else {
            sb.append("No tiene libros.");
        }
        sb.append("\n____________________________________________________");
        return sb.toString();
    }
}
