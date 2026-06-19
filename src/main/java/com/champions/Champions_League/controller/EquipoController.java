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
    // Aquí usamos el DTO y @Valid para que Spring bloquee datos inválidos antes de entrar
    @PostMapping
    public ResponseEntity<Equipo> guardar(@Valid @RequestBody EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setPais(equipoDTO.getPais());
        equipo.setEstadio(equipoDTO.getEstadio());
        equipo.setDirectorTecnico(equipoDTO.getDirectorTecnico());
        equipo.setPuntos(equipoDTO.getPuntos());
        Equipo nuevoEquipo = equipoService.guardar(equipo);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED); // Devuelve 201 Created (Buena práctica)
    }
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        return equipoService.obtenerPorId(id)
                .map(equipo -> {
                    if (equipoActualizado.getNombre() != null) equipo.setNombre(equipoActualizado.getNombre());
                    if (equipoActualizado.getPais() != null) equipo.setPais(equipoActualizado.getPais());
                    if (equipoActualizado.getEstadio() != null) equipo.setEstadio(equipoActualizado.getEstadio());
                    if (equipoActualizado.getDirectorTecnico() != null) equipo.setDirectorTecnico(equipoActualizado.getDirectorTecnico());
                    equipo.setPuntos(equipoActualizado.getPuntos());
                    return ResponseEntity.ok(equipoService.guardar(equipo));
                })
                .orElse(ResponseEntity.notFound().build());
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