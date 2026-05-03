package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.Partnership;
import com.nutriomedics.backend.repository.PartnershipRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartnershipService {

    private final PartnershipRepository repository;

    public PartnershipService(PartnershipRepository repository) {
        this.repository = repository;
    }

    public List<Partnership> getAll() {
        return repository.findAll();
    }

    public List<Partnership> getActive() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public Partnership getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partnership not found"));
    }

    public Partnership create(Partnership partnership) {
        partnership.setCreatedAt(LocalDateTime.now());
        if (partnership.getIsActive() == null)
            partnership.setIsActive(true);
        if (partnership.getDisplayOrder() == null)
            partnership.setDisplayOrder(0);
        return repository.save(partnership);
    }

    public Partnership update(Integer id, Partnership details) {
        Partnership existing = getById(id);
        if (details.getPartnerType() != null)
            existing.setPartnerType(details.getPartnerType());
        if (details.getOrganizationName() != null)
            existing.setOrganizationName(details.getOrganizationName());
        if (details.getDescription() != null)
            existing.setDescription(details.getDescription());
        if (details.getLogoUrl() != null)
            existing.setLogoUrl(details.getLogoUrl());
        if (details.getWebsiteUrl() != null)
            existing.setWebsiteUrl(details.getWebsiteUrl());
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