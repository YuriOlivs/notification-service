package com.yuriolivs.notification_service.dto;

import com.yuriolivs.notification_service.dto.message.MessageDTO;

public record TelegramUpdateDTO (
        MessageDTO message
) {}
