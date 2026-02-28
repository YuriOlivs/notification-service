package com.yuriolivs.notification_service.notification.messaging.producer;

import com.yuriolivs.notification_service.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.remote.NotificationResult;

@Component
@RequiredArgsConstructor
public class NotificationResultPublisher {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public void publish(NotificationResult result) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.RESULT_ROUTING_KEY,
                result
        );
    }
}
