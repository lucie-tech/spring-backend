package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    List<Partnership> findByIsActiveTrueOrderByDisplayOrderAsc();
}