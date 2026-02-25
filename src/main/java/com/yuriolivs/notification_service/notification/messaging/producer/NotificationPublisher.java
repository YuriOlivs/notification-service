package com.yuriolivs.notification_service.notification.messaging.producer;

import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.NotificationSend;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationPublisher {
    @Autowired private RabbitTemplate rabbitTemplate;

    public void publish(Notification notification, Map<String, String> payload) {
        String routingKey = notification.getChannel().name().toLowerCase();

        NotificationSend send = new NotificationSend(notification.getId(), notification.getPriority(), payload);

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                routingKey,
                send,
                msg -> {
                    msg.getMessageProperties()
                            .setPriority(notification.getPriority().value());

                    return msg;
                }
        );
    }
}
