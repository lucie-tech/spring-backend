package com.nutriomedics.backend.service;

import com.nutriomedics.backend.entity.ContactMessage;
import com.nutriomedics.backend.repository.ContactMessageRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactMessageService {

    private final ContactMessageRepository repository;
    private final JavaMailSender mailSender;

    public ContactMessageService(ContactMessageRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    // Public: submit a new message
    public ContactMessage submit(ContactMessage message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus("UNREAD");
        if (message.getMessageType() == null) {
            message.setMessageType("GENERAL");
        }
        return repository.save(message);
    }

    // Admin: get all messages
    public List<ContactMessage> getAll() {
        return repository.findAll();
    }

    // Admin: get unread messages
    public List<ContactMessage> getUnread() {
        return repository.findByStatusOrderByCreatedAtDesc("UNREAD");
    }

    // Admin: get message by ID
    public ContactMessage getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact message not found with id: " + id));
    }

    // Admin: mark as read
    public ContactMessage markAsRead(Integer id) {
        ContactMessage msg = getById(id);
        msg.setStatus("READ");
        return repository.save(msg);
    }

    // Admin: reply to a message (saves reply + sends email)
    public ContactMessage reply(Integer id, String replyMessage) {
        ContactMessage msg = getById(id);
        msg.setStatus("REPLIED");
        msg.setReplyMessage(replyMessage);
        msg.setRepliedAt(LocalDateTime.now());
        ContactMessage saved = repository.save(msg);

        // Send email notification to the user
        sendReplyEmail(msg.getEmail(), msg.getFullName(), replyMessage, msg.getSubject());

        return saved;
    }

    // Helper: send email to user
    private void sendReplyEmail(String toEmail, String toName, String replyContent, String originalSubject) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(toEmail);
            email.setSubject("Re: " + originalSubject);
            email.setText("Dear " + toName + ",\n\n" + replyContent + "\n\nBest regards,\nNutriomedics Support Team");
            mailSender.send(email);
        } catch (Exception e) {
            // Log error but don't break the API – the reply is already saved in DB
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }

    // Admin: delete message
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}