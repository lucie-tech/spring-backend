
package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.Service;
import com.nutriomedics.backend.repository.ServiceRepository;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service 
public class NutriService {

    private final ServiceRepository serviceRepository;

    public NutriService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<Service> getActiveServices() {
        return serviceRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public Service getServiceById(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }

    public Service createService(Service service) {
        service.setCreatedAt(LocalDateTime.now());
        if (service.getIsActive() == null)
            service.setIsActive(true);
        if (service.getDisplayOrder() == null)
            service.setDisplayOrder(0);
        if (service.getSlug() == null || service.getSlug().isEmpty()) {
            service.setSlug(service.getTitle().toLowerCase().replace(" ", "-"));
        }
        return serviceRepository.save(service);
    }

    public Service updateService(Integer id, Service details) {
        Service existing = getServiceById(id);
        if (details.getTitle() != null)
            existing.setTitle(details.getTitle());
        if (details.getDescription() != null)
            existing.setDescription(details.getDescription());
        if (details.getSlug() != null)
            existing.setSlug(details.getSlug());
        if (details.getIconKey() != null)
            existing.setIconKey(details.getIconKey());
        if (details.getDisplayOrder() != null)
            existing.setDisplayOrder(details.getDisplayOrder());
        if (details.getIsActive() != null)
            existing.setIsActive(details.getIsActive());
        return serviceRepository.save(existing);
    }

    public void deleteService(Integer id) {
        serviceRepository.deleteById(id);
    }
}