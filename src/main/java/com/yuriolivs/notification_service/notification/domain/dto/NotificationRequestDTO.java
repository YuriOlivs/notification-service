package com.yuriolivs.notification_service.notification.domain.dto;

import com.yuriolivs.notification_service.mail.domain.enums.EmailTemplate;
import com.yuriolivs.notification_service.notification.domain.enums.NotificationChannel;
import com.yuriolivs.notification_service.notification.domain.enums.NotificationPriority;
import com.yuriolivs.notification_service.notification.domain.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record NotificationRequestDTO(
    @NotBlank
    String idempotencyKey,

    NotificationChannel channel,

    @NotBlank
    String recipient,

    NotificationType type,

    EmailTemplate template,

    NotificationPriority priority,

    @NotNull
    Map<String, String> payload
) {
}
