package com.hkrox.todoproj.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private boolean  isCompleted;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        applyDefaults();
    }

    @PreUpdate
    protected void onUpdate() {
        applyDefaults();
    }

    private void applyDefaults() {
        if (priority == null) {
            priority = Priority.MEDIUM;
        }

        if (category == null || category.isBlank()) {
            category = "General";
        }
    }
}
