package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.HeroImage;
import com.nutriomedics.backend.repository.HeroImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hero")
@CrossOrigin(origins = "http://localhost:4200")
public class HeroImageController {

    @Autowired
    private HeroImageRepository repository;

    @GetMapping("/images")
    public List<HeroImage> getActiveHeroImages() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    @GetMapping("/images/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<HeroImage> getAllHeroImages() {
        return repository.findAll();
    }

    @GetMapping("/images/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HeroImage> getHeroImageById(@PathVariable Long id) {
        HeroImage image = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero image not found"));
        return ResponseEntity.ok(image);
    }

    @PostMapping("/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HeroImage> createHeroImage(@RequestBody Map<String, Object> raw) {
        System.out.println("📥 Received hero image data: " + raw);

        HeroImage heroImage = new HeroImage();
        heroImage.setId(null);
        heroImage.setCreatedAt(LocalDateTime.now());
        heroImage.setUpdatedAt(LocalDateTime.now());

        // Extract and convert fields safely
        heroImage.setImageUrl((String) raw.get("imageUrl"));
        heroImage.setTitle((String) raw.get("title"));
        heroImage.setSubtitle((String) raw.get("subtitle"));
        heroImage.setButtonText((String) raw.get("buttonText"));
        heroImage.setButtonLink((String) raw.get("buttonLink"));

        Object displayOrderObj = raw.get("displayOrder");
        if (displayOrderObj != null) {
            if (displayOrderObj instanceof Number) {
                heroImage.setDisplayOrder(((Number) displayOrderObj).intValue());
            } else {
                heroImage.setDisplayOrder(Integer.parseInt(displayOrderObj.toString()));
            }
        } else {
            heroImage.setDisplayOrder(0);
        }

        Object isActiveObj = raw.get("isActive");
        if (isActiveObj instanceof Boolean) {
            heroImage.setIsActive((Boolean) isActiveObj);
        } else if (isActiveObj != null) {
            heroImage.setIsActive(Boolean.parseBoolean(isActiveObj.toString()));
        } else {
            heroImage.setIsActive(true);
        }

        HeroImage saved = repository.save(heroImage);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/images/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HeroImage> updateHeroImage(@PathVariable Long id, @RequestBody Map<String, Object> raw) {
        HeroImage existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero image not found"));

        if (raw.containsKey("imageUrl")) {
            existing.setImageUrl((String) raw.get("imageUrl")); // allows null to clear image
        }
        if (raw.containsKey("title")) {
            existing.setTitle((String) raw.get("title"));
        }
        if (raw.containsKey("subtitle")) {
            existing.setSubtitle((String) raw.get("subtitle"));
        }
        if (raw.containsKey("buttonText")) {
            existing.setButtonText((String) raw.get("buttonText"));
        }
        if (raw.containsKey("buttonLink")) {
            existing.setButtonLink((String) raw.get("buttonLink"));
        }
        if (raw.containsKey("displayOrder")) {
            Object order = raw.get("displayOrder");
            if (order instanceof Number) {
                existing.setDisplayOrder(((Number) order).intValue());
            } else if (order != null) {
                existing.setDisplayOrder(Integer.parseInt(order.toString()));
            }
        }
        if (raw.containsKey("isActive")) {
            Object active = raw.get("isActive");
            if (active instanceof Boolean) {
                existing.setIsActive((Boolean) active);
            } else if (active != null) {
                existing.setIsActive(Boolean.parseBoolean(active.toString()));
            }
        }

        existing.setUpdatedAt(LocalDateTime.now());
        HeroImage updated = repository.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/images/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHeroImage(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero image not found");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}