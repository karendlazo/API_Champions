package com.champions.Champions_League.service;

import com.champions.Champions_League.dto.EquipoDTO;
import com.champions.Champions_League.model.Equipo;
import com.champions.Champions_League.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> obtenerPorId(Long id) {
        return equipoRepository.findById(id);
    }

    public Equipo guardar(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    // Método limpio que guarda usando DTO
    public Equipo guardar(EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setPais(equipoDTO.getPais());
        equipo.setEstadio(equipoDTO.getEstadio());
        equipo.setDirectorTecnico(equipoDTO.getDirectorTecnico());
        equipo.setPuntos(equipoDTO.getPuntos());
        return equipoRepository.save(equipo);
    }

    // Método limpio que actualiza usando DTO
    public Equipo actualizar(Long id, EquipoDTO equipoDTO) {
        return equipoRepository.findById(id).map(equipo -> {
            equipo.setNombre(equipoDTO.getNombre());
            equipo.setPais(equipoDTO.getPais());
            if (equipoDTO.getEstadio() != null) equipo.setEstadio(equipoDTO.getEstadio());
            if (equipoDTO.getDirectorTecnico() != null) equipo.setDirectorTecnico(equipoDTO.getDirectorTecnico());
            equipo.setPuntos(equipoDTO.getPuntos());
            return equipoRepository.save(equipo);
        }).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
    }

    public void eliminar(Long id) {
        equipoRepository.deleteById(id);
    }
}
