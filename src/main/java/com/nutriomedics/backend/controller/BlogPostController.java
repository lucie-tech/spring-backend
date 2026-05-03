package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.BlogPost;
import com.nutriomedics.backend.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogPostController {

    private final BlogPostService service;

    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<BlogPost>> getPublished() {
        return ResponseEntity.ok(service.getAllPublished());
    }

    @GetMapping("/posts/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BlogPost>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<BlogPost> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogPost> create(@Valid @RequestBody BlogPost post) {
        return new ResponseEntity<>(service.create(post), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogPost> update(@PathVariable Integer id, @Valid @RequestBody BlogPost post) {
        return ResponseEntity.ok(service.update(id, post));
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}