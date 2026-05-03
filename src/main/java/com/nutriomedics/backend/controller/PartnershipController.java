package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.Partnership;
import com.nutriomedics.backend.service.PartnershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/partnerships")
public class PartnershipController {

    private final PartnershipService service;

    public PartnershipController(PartnershipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Partnership>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Partnership>> getActive() {
        return ResponseEntity.ok(service.getActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partnership> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Partnership> create(@Valid @RequestBody Partnership partnership) {
        return new ResponseEntity<>(service.create(partnership), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Partnership> update(@PathVariable Integer id, @Valid @RequestBody Partnership partnership) {
        return ResponseEntity.ok(service.update(id, partnership));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}