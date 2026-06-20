package com.champions.Champions_League.service;

import com.champions.Champions_League.dto.PartidoDTO;
import com.champions.Champions_League.model.Equipo;
import com.champions.Champions_League.model.Partido;
import com.champions.Champions_League.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoService equipoService;

    public PartidoService(PartidoRepository partidoRepository, EquipoService equipoService) {
        this.partidoRepository = partidoRepository;
        this.equipoService = equipoService;
    }

    public List<Partido> obtenerTodos() {
        return partidoRepository.findAll();
    }

    public Optional<Partido> obtenerPorId(Long id) {
        return partidoRepository.findById(id);
    }

    // --- EL CEREBRO MATEMÁTICO ---
    private void actualizarPuntos(Equipo local, Equipo visitante, Integer golesLocal, Integer golesVisitante, boolean esReversa) {
        int puntosLocal = 0;
        int puntosVisitante = 0;

        if (golesLocal > golesVisitante) {
            puntosLocal = 3;
        } else if (golesVisitante > golesLocal) {
            puntosVisitante = 3;
        } else {
            puntosLocal = 1;
            puntosVisitante = 1;
        }

        if (esReversa) { // Si estamos borrando o editando, restamos los puntos viejos
            local.setPuntos(local.getPuntos() - puntosLocal);
            visitante.setPuntos(visitante.getPuntos() - puntosVisitante);
        } else { // Si estamos creando un partido, sumamos los puntos nuevos
            local.setPuntos(local.getPuntos() + puntosLocal);
            visitante.setPuntos(visitante.getPuntos() + puntosVisitante);
        }

        equipoService.guardar(local);
        equipoService.guardar(visitante);
    }

    public Partido crearPartido(PartidoDTO partidoDTO) {
        if (partidoDTO.getEquipoLocalId().equals(partidoDTO.getEquipoVisitanteId())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo");
        }

        Equipo local = equipoService.obtenerPorId(partidoDTO.getEquipoLocalId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo local no encontrado"));
        Equipo visitante = equipoService.obtenerPorId(partidoDTO.getEquipoVisitanteId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo visitante no encontrado"));

        Partido partido = new Partido();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setGolesLocal(partidoDTO.getGolesLocal());
        partido.setGolesVisitante(partidoDTO.getGolesVisitante());
        partido.setFase(partidoDTO.getFase());
        partido.setFecha(partidoDTO.getFecha());

        // REGLA DEL ESTADIO
        if (!"Final".equalsIgnoreCase(partidoDTO.getFase())) {
            partido.setEstadio(local.getEstadio());
        } else {
            partido.setEstadio(partidoDTO.getEstadio());
        }

        // SUMAMOS LOS PUNTOS
        actualizarPuntos(local, visitante, partido.getGolesLocal(), partido.getGolesVisitante(), false);

        return partidoRepository.save(partido);
    }

    public Partido actualizar(Long id, PartidoDTO partidoDTO) {
        if (partidoDTO.getEquipoLocalId().equals(partidoDTO.getEquipoVisitanteId())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo");
        }

        return partidoRepository.findById(id).map(partido -> {
            Equipo local = equipoService.obtenerPorId(partidoDTO.getEquipoLocalId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipo local no encontrado"));
            Equipo visitante = equipoService.obtenerPorId(partidoDTO.getEquipoVisitanteId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipo visitante no encontrado"));

            // 1. DESHACER los puntos del resultado anterior
            actualizarPuntos(partido.getEquipoLocal(), partido.getEquipoVisitante(), partido.getGolesLocal(), partido.getGolesVisitante(), true);

            // 2. Actualizar los datos del partido
            partido.setEquipoLocal(local);
            partido.setEquipoVisitante(visitante);
            partido.setGolesLocal(partidoDTO.getGolesLocal());
            partido.setGolesVisitante(partidoDTO.getGolesVisitante());
            partido.setFase(partidoDTO.getFase());
            partido.setFecha(partidoDTO.getFecha());

            // 3. REGLA DEL ESTADIO
            if (!"Final".equalsIgnoreCase(partidoDTO.getFase())) {
                partido.setEstadio(local.getEstadio());
            } else {
                partido.setEstadio(partidoDTO.getEstadio());
            }

            // 4. APLICAR los puntos del nuevo resultado
            actualizarPuntos(local, visitante, partido.getGolesLocal(), partido.getGolesVisitante(), false);

            return partidoRepository.save(partido);
        }).orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));
    }

    public void eliminar(Long id) {
        partidoRepository.findById(id).ifPresent(partido -> {
            // DESHACER los puntos antes de borrar la historia
            actualizarPuntos(partido.getEquipoLocal(), partido.getEquipoVisitante(), partido.getGolesLocal(), partido.getGolesVisitante(), true);
            partidoRepository.delete(partido);
        });
    }
}
