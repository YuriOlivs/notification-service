package com.yuriolivs.notification_service.notification.messaging.consumer;

import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.mail.MailService;
import com.yuriolivs.notification_service.mail.domain.dto.MailDTO;
import com.yuriolivs.notification_service.mail.domain.dto.OrderTrackingMailDTO;
import com.yuriolivs.notification_service.notification.NotificationRepository;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification_service.notification.domain.enums.NotificationStatus;
import com.yuriolivs.notification_service.notification.domain.enums.NotificationType;
import com.yuriolivs.notification_service.notification.messaging.NotificationSend;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@AllArgsConstructor
public class MailNotificationConsumer {
    private final NotificationRepository repo;
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMqConfig.MAIL_QUEUE)
    public void consume(NotificationSend received) throws MessagingException, IOException {
        Notification notification = repo.findById(received.getId()).orElseThrow();

        try {
            NotificationType type = notification.getType();
            switch (type) {
                case SIMPLE_EMAIL -> {
                    MailDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            MailDTO.class
                    );
                    mailService.sendEmail(dto);
                }

                case EMAIL_ORDER_TRACKING -> {
                    OrderTrackingMailDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            OrderTrackingMailDTO.class
                    );
                    mailService.sendOrderTrackingMail(dto);
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