package com.mahaveer.myHelpDesk.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Builder
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;

    @Column(columnDefinition = "TEXT")
    private String message;
    private String sender;

    private LocalDateTime createdAt;

    public Conversation() {
    }

    public Conversation(Long id, Long ticketId, String message, String sender, LocalDateTime createdAt) {
        this.id = id;
        this.ticketId = ticketId;
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(id, that.id) && Objects.equals(ticketId, that.ticketId) && Objects.equals(message, that.message) && Objects.equals(sender, that.sender) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketId, message, sender, createdAt);
    }
}
