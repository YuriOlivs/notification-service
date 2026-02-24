package com.yuriolivs.notification_service.notification.domain.dto;

import com.yuriolivs.notification.shared.domain.email.enums.EmailTemplate;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
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
