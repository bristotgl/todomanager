package io.github.siegjor.todomanager.task;

import io.github.siegjor.todomanager.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 280)
    private String description;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @PrePersist
    private void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}
