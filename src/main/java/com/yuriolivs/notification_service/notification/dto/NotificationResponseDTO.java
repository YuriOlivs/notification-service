package com.yuriolivs.notification_service.notification.dto;

import com.yuriolivs.notification_service.notification.entities.Notification;
import com.yuriolivs.notification_service.notification.enums.NotificationChannel;
import com.yuriolivs.notification_service.notification.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        NotificationStatus status,
        NotificationChannel channel,
        LocalDateTime createdAt
) {
    public static NotificationResponseDTO from(Notification entity) {
        return new NotificationResponseDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt()
        );
    }
}
