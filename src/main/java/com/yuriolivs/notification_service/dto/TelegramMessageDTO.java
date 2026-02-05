package com.yuriolivs.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramMessageDTO(
    String chatId,
    String message
) {}
