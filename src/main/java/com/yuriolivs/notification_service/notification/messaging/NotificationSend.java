package com.yuriolivs.notification_service.notification.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class NotificationSend {
    private UUID id;
    private Map<String, String> payload;
}
