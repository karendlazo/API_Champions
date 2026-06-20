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
        partido.setEstadio(partidoDTO.getEstadio());
        partido.setFecha(partidoDTO.getFecha()); // Guardamos la fecha

        return partidoRepository.save(partido);
    }

    public void eliminar(Long id) {
        partidoRepository.deleteById(id);
    }
}
