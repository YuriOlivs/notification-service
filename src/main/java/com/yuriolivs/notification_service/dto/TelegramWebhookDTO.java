package com.yuriolivs.notification_service.dto;

import com.yuriolivs.notification_service.dto.message.MessageDTO;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

public record TelegramWebhookDTO(
        @NotNull
        MessageDTO message
) {}
