package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.FAQ;
import com.nutriomedics.backend.service.FAQService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {

    private final FAQService service;

    public FAQController(FAQService service) {
        this.service = service;
    }

    // Public endpoint - get all active FAQs
    @GetMapping("/active")
    public ResponseEntity<List<FAQ>> getActiveFAQs() {
        return ResponseEntity.ok(service.getAllActiveFAQs());
    }

    // Admin only endpoints
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FAQ>> getAllFAQs() {
        return ResponseEntity.ok(service.getAllFAQs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FAQ> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FAQ> create(@Valid @RequestBody FAQ faq) {
        return new ResponseEntity<>(service.create(faq), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FAQ> update(@PathVariable Long id, @Valid @RequestBody FAQ faq) {
        return ResponseEntity.ok(service.update(id, faq));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}