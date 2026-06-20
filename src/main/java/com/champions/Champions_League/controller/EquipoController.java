package com.champions.Champions_League.controller;

import com.champions.Champions_League.dto.EquipoDTO;
import com.champions.Champions_League.model.Equipo;
import com.champions.Champions_League.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<Equipo> obtenerTodos() {
        return equipoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(@PathVariable Long id) {
        return equipoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Equipo> guardar(@Valid @RequestBody EquipoDTO equipoDTO) {
        Equipo nuevoEquipo = equipoService.guardar(equipoDTO);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @Valid @RequestBody EquipoDTO equipoDTO) {
        try {
            Equipo equipoActualizado = equipoService.actualizar(id, equipoDTO);
            return ResponseEntity.ok(equipoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (equipoService.obtenerPorId(id).isPresent()) {
            equipoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
