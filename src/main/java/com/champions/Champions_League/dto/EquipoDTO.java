package com.champions.Champions_League.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class EquipoDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
    private String estadio;
    private String directorTecnico;
    @NotNull
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer puntos;

    // Métodos Getter y Setter obligatorios para que Spring pueda leer el JSON
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }

    public String getDirectorTecnico() { return directorTecnico; }
    public void setDirectorTecnico(String directorTecnico) { this.directorTecnico = directorTecnico; }

    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }
}