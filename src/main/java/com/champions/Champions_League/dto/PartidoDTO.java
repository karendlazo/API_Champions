package com.champions.Champions_League.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PartidoDTO {

    @NotNull(message = "El equipo local es obligatorio")
    private Long equipoLocalId;

    @NotNull(message = "El equipo visitante es obligatorio")
    private Long equipoVisitanteId;

    @NotNull(message = "Los goles locales son obligatorios (pon 0 si no han jugado)")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocal;

    @NotNull(message = "Los goles visitantes son obligatorios (pon 0 si no han jugado)")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitante;

    @NotBlank(message = "La fase es obligatoria")
    private String fase;

    private String estadio;

    private LocalDate fecha; // Fecha agregada

    // Getters y Setters
    public Long getEquipoLocalId() { return equipoLocalId; }
    public void setEquipoLocalId(Long equipoLocalId) { this.equipoLocalId = equipoLocalId; }

    public Long getEquipoVisitanteId() { return equipoVisitanteId; }
    public void setEquipoVisitanteId(Long equipoVisitanteId) { this.equipoVisitanteId = equipoVisitanteId; }

    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }

    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }

    public String getFase() { return fase; }
    public void setFase(String fase) { this.fase = fase; }

    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; } // Getter y setter de fecha
}
