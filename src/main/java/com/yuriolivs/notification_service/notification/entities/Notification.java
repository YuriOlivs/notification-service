package com.yuriolivs.notification_service.notification.entities;

import com.yuriolivs.notification_service.mail.enums.EmailTemplate;
import com.yuriolivs.notification_service.notification.enums.NotificationChannel;
import com.yuriolivs.notification_service.notification.enums.NotificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "notifications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notification_idempotency",
                        columnNames = "idempotencyKey"
                )
        }
)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String idempotencyKey;

    private NotificationChannel channel;

    private String recipient;

    private String template;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode payload;

    private LocalDateTime createdAt;

    public Notification(
        String idempotencyKey,
        NotificationChannel channel,
        String recipient,
        String template,
        NotificationStatus status,
        LocalDateTime createdAt
    ) {
        this.idempotencyKey = idempotencyKey;
        this.channel = channel;
        this.recipient = recipient;
        this.template = template;
        this.status = status;
        this.createdAt = createdAt;
    }
}
