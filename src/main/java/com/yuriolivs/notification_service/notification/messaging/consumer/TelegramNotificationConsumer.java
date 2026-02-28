package com.yuriolivs.notification_service.notification.messaging.consumer;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.notification.NotificationRepository;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationResultPublisher;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.telegram.services.TelegramMessageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@AllArgsConstructor
public class TelegramNotificationConsumer {
    private final NotificationRepository repo;
    private final TelegramMessageService messageService;
    private final ObjectMapper objectMapper;
    private final NotificationResultPublisher resultPublisher;

    @RabbitListener(queues = RabbitMqConfig.TELEGRAM_QUEUE)
    public void consume(NotificationMessage received) {
        Notification notification = repo.findById(received.getId()).orElseThrow();
        NotificationResult result = NotificationResult.from(received);

        try {
            NotificationType type = notification.getType();

            switch (type) {
                case TELEGRAM_MESSAGE -> {
                    TelegramMessageDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            TelegramMessageDTO.class
                    );
                    messageService.sendMessage(dto);
                }
            }

            notification.setStatus(NotificationStatus.SENT);

            result.setSuccess(true);
            result.setMessage("Message sent with success.");
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);

            result.setSuccess(false);
            result.setMessage("There was an error sending the message.");

            throw ex;
        }

        repo.save(notification);
        resultPublisher.publish(result);
    }
}
