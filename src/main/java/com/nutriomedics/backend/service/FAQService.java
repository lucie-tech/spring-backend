


package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.FAQ;
import com.nutriomedics.backend.repository.FAQRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FAQService {

    private final FAQRepository repository;

    public FAQService(FAQRepository repository) {
        this.repository = repository;
    }

    public List<FAQ> getAllActiveFAQs() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public List<FAQ> getAllFAQs() {
        return repository.findAllByOrderByDisplayOrderAsc();
    }

    public FAQ getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
    }

    public FAQ create(FAQ faq) {
        faq.setCreatedAt(LocalDateTime.now());
        faq.setUpdatedAt(LocalDateTime.now());
        if (faq.getIsActive() == null) {
            faq.setIsActive(true);
        }
        if (faq.getDisplayOrder() == null) {
            faq.setDisplayOrder(0);
        }
        return repository.save(faq);
    }

    public FAQ update(Long id, FAQ details) {
        FAQ existing = getById(id);

        if (details.getQuestion() != null) {
            existing.setQuestion(details.getQuestion());
        }
        if (details.getAnswer() != null) {
            existing.setAnswer(details.getAnswer());
        }
        if (details.getAdditionalInfo() != null) {
            existing.setAdditionalInfo(details.getAdditionalInfo());
        }
        if (details.getDisplayOrder() != null) {
            existing.setDisplayOrder(details.getDisplayOrder());
        }
        if (details.getIsActive() != null) {
            existing.setIsActive(details.getIsActive());
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}