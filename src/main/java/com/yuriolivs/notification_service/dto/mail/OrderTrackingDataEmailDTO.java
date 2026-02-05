package com.yuriolivs.notification_service.dto.mail;

import java.util.List;

public record OrderTrackingDataEmailDTO(
        String customerName,
        String orderId,
        String orderDate,
        String shippmentMethod,
        String trackingCode,
        String trackingUrl,
        String status,
        String statusDescription
) {}