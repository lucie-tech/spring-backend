package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    List<BlogPost> findByStatusOrderByCreatedAtDesc(String status);
}