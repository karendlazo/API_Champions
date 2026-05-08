package com.champions.Champions_League.controller;

import com.champions.Champions_League.model.Partido;
import com.champions.Champions_League.repository.PartidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final PartidoRepository repositorio;

    public PartidoController(PartidoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Partido> obtenerTodos() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> obtenerPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Partido crear(@RequestBody Partido partido) {
        return repositorio.save(partido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> actualizar(@PathVariable Long id, @RequestBody Partido partidoActualizado) {
        return repositorio.findById(id)
                .map(partido -> {
                    partido.setEquipoLocal(partidoActualizado.getEquipoLocal());
                    partido.setEquipoVisitante(partidoActualizado.getEquipoVisitante());
                    partido.setGolesLocal(partidoActualizado.getGolesLocal());
                    partido.setGolesVisitante(partidoActualizado.getGolesVisitante());
                    partido.setFecha(partidoActualizado.getFecha());
                    partido.setFase(partidoActualizado.getFase());
                    partido.setEstadio(partidoActualizado.getEstadio());
                    return ResponseEntity.ok(repositorio.save(partido));
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
