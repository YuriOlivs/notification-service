package com.yuriolivs.notification_service.dto.mail;

import java.util.List;
import java.util.Map;

public record OrderTrackingMailDTO(
        String to,
        OrderTrackingDataEmailDTO data,
        List<ProductEmailDTO> products
) {}