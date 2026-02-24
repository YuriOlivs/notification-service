package com.yuriolivs.notification_service.mail.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderTrackingMailDTO(
        String to,
        OrderTrackingDataEmailDTO data,
        List<ProductEmailDTO> products
) {}