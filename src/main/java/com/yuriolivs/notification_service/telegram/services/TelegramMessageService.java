package com.yuriolivs.notification_service.telegram.services;

import com.yuriolivs.notification_service.telegram.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.config.TelegramProperties;
import com.yuriolivs.notification_service.telegram.TelegramUser;
import com.yuriolivs.notification_service.telegram.enums.TelegramMessages;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class TelegramMessageService {
    private final TelegramUserService userService;
    private final RestTemplate restTemplate;
    private final TelegramProperties config;

    public String sendMessage(TelegramMessageDTO dto) throws BadRequestException {
        TelegramUser telegramUser = userService.findByUserId(dto.userId());
        if (!telegramUser.isActive()) throw new BadRequestException("User does not have notifications activated.");

        String url = String.format(
                "%s/bot%s/sendMessage",
                config.getApiUrl(),
                config.getToken()
        );

        Map<String, Object> body = Map.of(
                "chat_id", telegramUser.getChatId(),
                "text", dto.message()
        );

        restTemplate.postForEntity(url, body, Void.class);

        return "Message sent successfully";
    }

    public void sendStardardizedMessages(Long chatId, TelegramMessages message) {
        String url = String.format(
                "%s/bot%s/sendMessage",
                config.getApiUrl(),
                config.getToken()
        );

        Map<String, Object> body = Map.of(
                "chat_id", chatId,
                "text", message.value()
        );

        restTemplate.postForEntity(url, body, Void.class);
    }
}
