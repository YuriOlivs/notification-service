package com.yuriolivs.notification_service.notification.messaging.producer;

import com.yuriolivs.notification_service.notification.entities.Notification;
import com.yuriolivs.notification_service.notification.messaging.NotificationSend;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationPublisher {
    @Autowired private RabbitTemplate rabbitTemplate;
    private final String exchange = "notification.exchange";

    public void publish(Notification notification, Map<String, String> payload) {
        String routingKey = notification.getChannel().name().toLowerCase();

        NotificationSend send = new NotificationSend(notification.getId(), payload);

        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                send
        );
    }
}
