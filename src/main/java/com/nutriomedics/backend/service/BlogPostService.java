package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.BlogPost;
import com.nutriomedics.backend.repository.BlogPostRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository repository;

    public BlogPostService(BlogPostRepository repository) {
        this.repository = repository;
    }

    public List<BlogPost> getAllPublished() {
        return repository.findByStatusOrderByCreatedAtDesc("PUBLISHED");
    }

    public List<BlogPost> getAll() {
        return repository.findAll();
    }

    public BlogPost getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));
    }

    public BlogPost create(BlogPost post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        if (post.getStatus() == null) {
            post.setStatus("DRAFT");
        }
        return repository.save(post);
    }

    public BlogPost update(Integer id, BlogPost details) {
        BlogPost existing = getById(id);

        if (details.getTitle() != null) {
            existing.setTitle(details.getTitle());
        }
        if (details.getContent() != null) {
            existing.setContent(details.getContent());
        }
        if (details.getDocumentUrl() != null) {
            existing.setDocumentUrl(details.getDocumentUrl());
        }
        if (details.getDocumentName() != null) {
            existing.setDocumentName(details.getDocumentName());
        }
        if (details.getStatus() != null) {
            existing.setStatus(details.getStatus());
        }
        if (details.getImageUrl() != null) { // Add this
            existing.setImageUrl(details.getImageUrl());
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}