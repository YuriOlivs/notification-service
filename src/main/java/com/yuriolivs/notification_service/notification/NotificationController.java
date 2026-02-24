package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private ResponseEntity<NotificationResponseDTO> findById(
            @RequestBody @NotBlank UUID id
    ) {
        NotificationResponseDTO response = NotificationResponseDTO.from(service.findById(id));
        return ResponseEntity.ok(response);
    }
}
