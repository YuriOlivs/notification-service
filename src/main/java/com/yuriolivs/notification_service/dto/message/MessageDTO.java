package com.yuriolivs.notification_service.dto.message;

public record MessageDTO (
    MessageChatDTO chat,
    MessageFromDTO from,
    String text
) {}