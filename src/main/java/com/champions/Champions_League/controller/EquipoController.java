package com.champions.Champions_League.controller;

import com.champions.Champions_League.model.Equipo;
import com.champions.Champions_League.repository.EquipoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository repositorio;

    public EquipoController(EquipoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Equipo> obtenerTodos() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipo crear(@RequestBody Equipo equipo) {
        return repositorio.save(equipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        return repositorio.findById(id)
                .map(equipo -> {
                    equipo.setNombre(equipoActualizado.getNombre());
                    equipo.setPais(equipoActualizado.getPais());
                    equipo.setEstadio(equipoActualizado.getEstadio());
                    equipo.setDirectorTecnico(equipoActualizado.getDirectorTecnico());
                    equipo.setPuntos(equipoActualizado.getPuntos());
                    return ResponseEntity.ok(repositorio.save(equipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
