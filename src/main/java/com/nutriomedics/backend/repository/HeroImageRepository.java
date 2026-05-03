
package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.HeroImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HeroImageRepository extends JpaRepository<HeroImage, Long> {
    List<HeroImage> findByIsActiveTrueOrderByDisplayOrderAsc();
}