package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.entity.ContactMessage;
import com.nutriomedics.backend.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageService service;

    public ContactController(ContactMessageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> submit(@Valid @RequestBody ContactMessage message) {
        service.submit(message);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Your message has been sent successfully. We will get back to you soon.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin/messages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/admin/messages/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessage>> getUnreadMessages() {
        return ResponseEntity.ok(service.getUnread());
    }

    @GetMapping("/admin/messages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> getMessageById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/admin/messages/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> markAsRead(@PathVariable Integer id) {
        return ResponseEntity.ok(service.markAsRead(id));
    }

    @PostMapping("/admin/messages/{id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> replyToMessage(
            @PathVariable Integer id,
            @RequestBody Map<String, String> payload) {
        String replyMessage = payload.get("replyMessage");
        if (replyMessage == null || replyMessage.trim().isEmpty()) {
            throw new RuntimeException("Reply message cannot be empty");
        }
        return ResponseEntity.ok(service.reply(id, replyMessage));
    }

    @DeleteMapping("/admin/messages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}