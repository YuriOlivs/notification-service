package com.yuriolivs.notification_service.notification.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationPriority {
    URGENT("10"),
    WARNING("7"),
    INFO("4"),
    MARKETING("1");

    private final String text;

    public Integer value() {
        return Integer.parseInt(text);
    }

    public static NotificationPriority fromText(String text) {
        for (NotificationPriority priority : values()) {
            if (priority.text.equalsIgnoreCase(text)) {
                return priority;
            }
        }
        return null;
    }
}

