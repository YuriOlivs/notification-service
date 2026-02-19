package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification_service.mail.MailService;
import com.yuriolivs.notification_service.mail.dto.MailDTO;
import com.yuriolivs.notification_service.mail.dto.OrderTrackingMailDTO;
import com.yuriolivs.notification_service.notification.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.entities.Notification;
import com.yuriolivs.notification_service.notification.enums.NotificationStatus;
import com.yuriolivs.notification_service.notification.enums.NotificationType;
import com.yuriolivs.notification_service.telegram.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.telegram.services.TelegramMessageService;
import com.yuriolivs.notification_service.telegram.services.TelegramUserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationRepository repo;
    private MailService mailService;
    private TelegramMessageService messageService;
    private TelegramUserService userService;
    private ObjectMapper objectMapper;

    public Notification handleNotificationRequest(NotificationRequestDTO dto) throws IOException, MessagingException {
        Optional<Notification> notificationFound = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (notificationFound.isPresent()) return notificationFound.get();

        Notification newNotification = new Notification(
                dto.idempotencyKey(),
                dto.channel(),
                dto.recipient(),
                dto.template().name() ,
                NotificationStatus.CREATED,
                LocalDateTime.now()
        );

        repo.save(newNotification);

        try {
            switch (dto.channel()) {
                case EMAIL ->
                        dispatchEmailNotification(dto.type(), dto.payload());
                case TELEGRAM ->
                        dispatchTelegramNotification(dto.type(), dto.payload());
            }

            newNotification.setStatus(NotificationStatus.SENT);
            return repo.save(newNotification);

        } catch (Exception ex) {
            newNotification.setStatus(NotificationStatus.FAILED);
            repo.save(newNotification);
            throw ex;
        }
    }


    private void dispatchEmailNotification(NotificationType type, JsonNode payload) throws MessagingException, IOException {
        switch (type) {
            case SIMPLE_EMAIL -> {
                MailDTO dto = objectMapper.treeToValue(payload, MailDTO.class);
                mailService.sendEmail(dto);
            }

            case EMAIL_ORDER_TRACKING -> {
                OrderTrackingMailDTO dto = objectMapper.treeToValue(payload, OrderTrackingMailDTO.class);
                mailService.sendOrderTrackingMail(dto);
            }
        }
    }

    private void dispatchTelegramNotification(NotificationType type, JsonNode payload) throws BadRequestException {
        switch (type) {
            case TELEGRAM_MESSAGE -> {
                TelegramMessageDTO dto = objectMapper.treeToValue(payload, TelegramMessageDTO.class);
                messageService.sendMessage(dto);
            }
        }
    }

}
