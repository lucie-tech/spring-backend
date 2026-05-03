package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.Service;
import com.nutriomedics.backend.service.NutriService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final NutriService nutriService;

    public ServiceController(NutriService nutriService) {
        this.nutriService = nutriService;
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(nutriService.getAllServices());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Service>> getActiveServices() {
        return ResponseEntity.ok(nutriService.getActiveServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok(nutriService.getServiceById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Service> createService(@Valid @RequestBody Service serviceData) {
        Service saved = nutriService.createService(serviceData);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Service> updateService(@PathVariable Integer id, @Valid @RequestBody Service serviceData) {
        return ResponseEntity.ok(nutriService.updateService(id, serviceData));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        nutriService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}