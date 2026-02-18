package com.yuriolivs.notification_service.notification.dto;

import com.yuriolivs.notification_service.mail.enums.EmailTemplate;
import com.yuriolivs.notification_service.notification.enums.NotificationChannel;
import com.yuriolivs.notification_service.notification.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.JsonNode;

public record NotificationRequestDTO(
    @NotBlank
    String idempotencyKey,

    NotificationChannel channel,

    @NotBlank
    String recipient,

    NotificationType type,

    EmailTemplate template,

    @NotNull
    JsonNode payload
) {
}
