package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private NotificationService service;

    @PostMapping
    private ResponseEntity<NotificationResponseDTO> handleNotificationRequest(
            @RequestBody @Valid NotificationRequestDTO dto
    ) throws MessagingException, IOException {
        NotificationResponseDTO response = NotificationResponseDTO.from(service.handleNotificationRequest(dto));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    private ResponseEntity<NotificationResponseDTO> getNotification(
            @RequestBody @NotBlank UUID id
    ) {
        NotificationResponseDTO response = NotificationResponseDTO.from(service.findById(id));
        return ResponseEntity.ok(response);
    }

    //Internal Endpoints
    @PostMapping("/internal")
    private ResponseEntity<NotificationResponseDTO> postNotification(
            @RequestBody @Valid NotificationRequestDTO dto
    ) {
        NotificationResponseDTO response = NotificationResponseDTO.from(service.save(dto));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/internal/payload")
    private ResponseEntity<String> getPayload(
            @RequestBody @NotEmpty UUID id
    ) {
        String payload = service.getNotificationPayload(id);
        return ResponseEntity.ok(payload);
    }
}
