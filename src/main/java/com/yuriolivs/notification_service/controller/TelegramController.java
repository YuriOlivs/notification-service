package com.yuriolivs.notification_service.controller;

import com.yuriolivs.notification_service.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.dto.TelegramWebhookDTO;
import com.yuriolivs.notification_service.service.TelegramMessageService;
import com.yuriolivs.notification_service.service.TelegramUserService;
import com.yuriolivs.notification_service.service.TelegramWebhookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("telegram")
@AllArgsConstructor
public class TelegramController {
    private final TelegramMessageService messageService;
    private final TelegramWebhookService webhookService;
    private final TelegramUserService userService;

    @PostMapping
    private ResponseEntity<String> sendMessage(
            @RequestBody @Valid TelegramMessageDTO dto
    ) throws BadRequestException{
        return ResponseEntity.ok(messageService.sendMessage(dto));
    }

    @PostMapping("/webhooks")
    private ResponseEntity<Void> receiveUpdate(
            @RequestBody @Valid TelegramWebhookDTO dto
    ) throws BadRequestException {
        webhookService.receiveUpdate(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/webhooks")
    private ResponseEntity<Void> deleteWebhook(
            @RequestBody Long userId
    ) throws BadRequestException {
        userService.deleteTelegramUser(userId);
        return ResponseEntity.ok().build();
    }
}
