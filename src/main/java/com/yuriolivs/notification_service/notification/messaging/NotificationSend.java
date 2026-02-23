package com.yuriolivs.notification_service.notification.messaging;

import com.yuriolivs.notification_service.notification.enums.NotificationPriority;
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
    private NotificationPriority priority;
    private Map<String, String> payload;
}
