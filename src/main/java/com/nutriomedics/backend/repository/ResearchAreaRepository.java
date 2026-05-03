package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.ResearchArea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResearchAreaRepository extends JpaRepository<ResearchArea, Integer> {
    List<ResearchArea> findByIsActiveTrueOrderByDisplayOrderAsc();
}