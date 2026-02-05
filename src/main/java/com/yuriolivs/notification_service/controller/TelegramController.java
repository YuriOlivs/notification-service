package com.yuriolivs.notification_service.controller;

import com.yuriolivs.notification_service.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.service.TelegramService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("telegram")
@AllArgsConstructor
public class TelegramController {
    private final TelegramService service;

    @PostMapping
    private ResponseEntity<String> sendMessage(
            @RequestBody @Valid TelegramMessageDTO dto
    ) {
        return ResponseEntity.ok(service.sendMessage(dto));
    }
}
