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

        existing.setTitle(details.getTitle());
        existing.setContent(details.getContent());
        existing.setDocumentUrl(details.getDocumentUrl()); // can be null
        existing.setDocumentName(details.getDocumentName()); // can be null
        existing.setStatus(details.getStatus());
        existing.setImageUrl(details.getImageUrl()); // can be null (FIX)

        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}