package com.nutriomedics.backend.repository;

import com.nutriomedics.backend.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
    List<ContactMessage> findByStatusOrderByCreatedAtDesc(String status);
}