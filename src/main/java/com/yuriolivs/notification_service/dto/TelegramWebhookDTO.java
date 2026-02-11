package com.yuriolivs.notification_service.dto;

import com.yuriolivs.notification_service.dto.message.MessageDTO;
import jakarta.validation.constraints.NotNull;

public record TelegramWebhookDTO(
        @NotNull
        MessageDTO message
) {}
