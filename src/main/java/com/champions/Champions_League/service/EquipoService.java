package com.champions.Champions_League.service;

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

    public void eliminar(Long id) {
        equipoRepository.deleteById(id);
    }
}
