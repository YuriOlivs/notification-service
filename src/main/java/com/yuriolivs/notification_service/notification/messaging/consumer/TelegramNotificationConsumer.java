package com.yuriolivs.notification_service.notification.messaging.consumer;

import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.notification.NotificationRepository;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.telegram.services.TelegramMessageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TelegramNotificationConsumer {
    private final NotificationRepository repo;
    private final TelegramMessageService messageService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMqConfig.TELEGRAM_QUEUE)
    public void consume(UUID notificationId, Map<String, String> payload) {
        Notification notification = repo.findById(notificationId).orElseThrow();

        try {
            NotificationType type = notification.getType();

            switch (type) {
                case TELEGRAM_MESSAGE -> {
                    TelegramMessageDTO dto = objectMapper.convertValue(
                            payload,
                            TelegramMessageDTO.class
                    );
                    messageService.sendMessage(dto);
                }
            }

            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            throw ex;
        }

        repo.save(notification);
    }
}
