package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(String category); // ✅ used in service
}