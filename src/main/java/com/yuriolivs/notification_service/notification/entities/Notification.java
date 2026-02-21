package com.yuriolivs.notification_service.notification.entities;

import com.yuriolivs.notification_service.notification.enums.NotificationChannel;
import com.yuriolivs.notification_service.notification.enums.NotificationStatus;
import com.yuriolivs.notification_service.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

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
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String idempotencyKey;

    private NotificationChannel channel;

    private String recipient;

    private String template;

    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;

    private String payload;

    public Notification(
        String idempotencyKey,
        NotificationChannel channel,
        String recipient,
        String template,
        NotificationType type,
        NotificationStatus status,
        LocalDateTime createdAt,
        String payload
    ) {
        this.idempotencyKey = idempotencyKey;
        this.channel = channel;
        this.recipient = recipient;
        this.template = template;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.payload = payload;
    }
}
