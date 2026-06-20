package com.champions.Champions_League.controller;

import com.champions.Champions_League.dto.PartidoDTO;
import com.champions.Champions_League.model.Partido;
import com.champions.Champions_League.service.PartidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoService(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping
    public List<Partido> obtenerTodos() {
        return partidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> obtenerPorId(@PathVariable Long id) {
        return partidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Partido> guardar(@Valid @RequestBody PartidoDTO partidoDTO) {
        Partido nuevoPartido = partidoService.crearPartido(partidoDTO);
        return new ResponseEntity<>(nuevoPartido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> actualizar(@PathVariable Long id, @Valid @RequestBody PartidoDTO partidoDTO) {
        if (partidoService.obtenerPorId(id).isPresent()) {
            partidoService.eliminar(id);
            Partido partidoActualizado = partidoService.crearPartido(partidoDTO);
            return ResponseEntity.ok(partidoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (partidoService.obtenerPorId(id).isPresent()) {
            partidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
