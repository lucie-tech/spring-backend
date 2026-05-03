package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.ResearchArea;
import com.nutriomedics.backend.repository.ResearchAreaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResearchAreaService {
    private final ResearchAreaRepository repository;

    public ResearchAreaService(ResearchAreaRepository repository) {
        this.repository = repository;
    }

    public List<ResearchArea> getAll() {
        return repository.findAll();
    }

    public List<ResearchArea> getActive() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public ResearchArea getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public ResearchArea create(ResearchArea area) {
        area.setCreatedAt(LocalDateTime.now());
        if (area.getIsActive() == null)
            area.setIsActive(true);
        if (area.getDisplayOrder() == null)
            area.setDisplayOrder(0);
        return repository.save(area);
    }

    public ResearchArea update(Integer id, ResearchArea details) {
        ResearchArea existing = getById(id);
        if (details.getTitle() != null)
            existing.setTitle(details.getTitle());
        if (details.getDescription() != null)
            existing.setDescription(details.getDescription());
        if (details.getFocusTag() != null)
            existing.setFocusTag(details.getFocusTag());
        if (details.getDisplayOrder() != null)
            existing.setDisplayOrder(details.getDisplayOrder());
        if (details.getIsActive() != null)
            existing.setIsActive(details.getIsActive());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}