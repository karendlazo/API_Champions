package com.champions.Champions_League.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity
@Table(name = "equipos")

public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    private String pais;
    private String estadio;
    private String directorTecnico;
    private int puntos;

    // Constructor vacío
    public Equipo() {
    }

    // Constructor con parámetros
    public Equipo(String nombre, String pais, String estadio, String directorTecnico, int puntos) {
        this.nombre = nombre;
        this.pais = pais;
        this.estadio = estadio;
        this.directorTecnico = directorTecnico;
        this.puntos = puntos;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstadio() {
        return estadio;
    }
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getDirectorTecnico() {
        return directorTecnico;
    }
    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico;
    }

    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
