package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification_service.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationPublisher;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationRepository repo;
    private NotificationPublisher publisher;

    public Notification handleNotificationRequest(NotificationRequestDTO dto) throws IOException, MessagingException {
        Optional<Notification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent()) {
            return existing.get();
        }


        Notification notification = new Notification(
                dto.idempotencyKey(),
                dto.channel(),
                dto.recipient(),
                dto.template().name() ,
                dto.type(),
                NotificationStatus.CREATED,
                dto.priority(),
                LocalDateTime.now(),
                dto.payload().toString()
        );

        repo.save(notification);
        publisher.publish(notification, dto.payload());

        return notification;
    }

    public Notification findById(UUID id) {
        Optional<Notification> existing = repo.findById(id);
        if (existing.isEmpty()) {
            throw new HttpNotFoundException("Notification not found.");
        }

        return existing.get();
    }
}
