package com.champions.Champions_League.repository;

import com.champions.Champions_League.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // Spring nos da automáticamente:
    // findAll()       → buscar todos
    // findById(id)    → buscar por ID
    // save(equipo)    → guardar o actualizar
    // deleteById(id)  → eliminar por ID
}
