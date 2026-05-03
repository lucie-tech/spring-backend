package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.Product;
import com.nutriomedics.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        // Set timestamps
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Set default values if not provided
        if (product.getStatus() == null) {
            product.setStatus("active");
        }
        if (product.getFeatured() == null) {
            product.setFeatured(false);
        }

        // The 'userId' column exists in DB but we ignore it (set to null)
        product.setUserId(null);

        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product details) {
        Product existing = getProductById(id);

        if (details.getName() != null)
            existing.setName(details.getName());
        if (details.getDescription() != null)
            existing.setDescription(details.getDescription());
        if (details.getCategory() != null)
            existing.setCategory(details.getCategory());
        if (details.getSlug() != null)
            existing.setSlug(details.getSlug());
        if (details.getTagline() != null)
            existing.setTagline(details.getTagline());
        if (details.getStatus() != null)
            existing.setStatus(details.getStatus());
        if (details.getFeatured() != null)
            existing.setFeatured(details.getFeatured());
        if (details.getClinicalBenefits() != null)
            existing.setClinicalBenefits(details.getClinicalBenefits());
        if (details.getImageUrl() != null)
            existing.setImageUrl(details.getImageUrl());

        existing.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existing);
    }

    public void deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.deleteById(id);
    }
}