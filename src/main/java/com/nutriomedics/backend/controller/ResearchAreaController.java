package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.ResearchArea;
import com.nutriomedics.backend.service.ResearchAreaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/research-areas")
public class ResearchAreaController {
    private final ResearchAreaService service;

    public ResearchAreaController(ResearchAreaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResearchArea>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ResearchArea>> getActive() {
        return ResponseEntity.ok(service.getActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchArea> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResearchArea> create(@Valid @RequestBody ResearchArea area) {
        return new ResponseEntity<>(service.create(area), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResearchArea> update(@PathVariable Integer id, @Valid @RequestBody ResearchArea area) {
        return ResponseEntity.ok(service.update(id, area));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}