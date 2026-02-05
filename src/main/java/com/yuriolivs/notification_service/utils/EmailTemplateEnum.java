package com.yuriolivs.notification_service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplateEnum {
    ORDER_TRACKING("order-tracking.html"),
    ORDER_TRACKING_ITEM("order-tracking-item.html");

    private final String filename;
}