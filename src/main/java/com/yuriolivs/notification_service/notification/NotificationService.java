package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification.shared.exceptions.http.HttpBadRequestException;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_service.notification.domain.NotificationServiceInterface;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationPublisher;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService implements NotificationServiceInterface {
    private NotificationRepository repo;
    private NotificationPublisher publisher;

    @Override
    public Notification handleNotificationRequest(NotificationRequestDTO dto) throws IOException, MessagingException {
        Optional<Notification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent()) {
            return existing.get();
        }

        Notification notification = Notification.fromRequest(dto);

        repo.save(notification);
        publisher.publish(notification, dto.payload());

        return notification;
    }

    @Override
    public Notification findById(UUID id) {
        Optional<Notification> existing = repo.findById(id);
        if (existing.isEmpty()) {
            throw new HttpNotFoundException("Notification not found.");
        }

        return existing.get();
    }

    @Override
    public Notification save(NotificationRequestDTO dto) {
        Optional<Notification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent()) throw new HttpBadRequestException("Notification Request already exists.");

        Notification notification = Notification.fromRequest(dto);
        return repo.save(notification);
    }

    public ScheduledPayloadResponseDTO getNotificationsPayload(
            SchedulePayloadRequestDTO dto
    ) {
        List<SchedulePayloadDTO> payloads = new ArrayList<>();

        List<Notification> notifications = repo.findAllById(dto.ids());

        for (Notification notification : notifications) {
            SchedulePayloadDTO payload = new SchedulePayloadDTO(
                    notification.getId(),
                    notification.getPayload(),
                    notification.getChannel()
            );

            payloads.add(payload);
        }

        return new ScheduledPayloadResponseDTO(payloads);
    }
}
