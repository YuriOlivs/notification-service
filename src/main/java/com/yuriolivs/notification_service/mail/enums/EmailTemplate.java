package com.yuriolivs.notification_service.mail.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplate {
    ORDER_TRACKING("order-tracking.html"),
    ORDER_TRACKING_ITEM("order-tracking-item.html"),
    NONE("");

    private final String filename;
}