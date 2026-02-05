package com.yuriolivs.notification_service.service;

import com.yuriolivs.notification_service.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.config.TelegramProperties;
import com.yuriolivs.notification_service.dto.TelegramUpdateDTO;
import com.yuriolivs.notification_service.dto.message.MessageChatDTO;
import com.yuriolivs.notification_service.dto.message.MessageFromDTO;
import com.yuriolivs.notification_service.utils.TelegramMessagesEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class TelegramService {
    private final RestTemplate restTemplate;
    private final TelegramProperties config;

    public String sendMessage(TelegramMessageDTO dto) {
        String url = String.format(
                "%s/bot%s/sendMessage",
                config.getApiUrl(),
                config.getToken()
        );

        Map<String, Object> body = Map.of(
                "chat_id", dto.chatId(),
                "text", dto.message()
        );

        restTemplate.postForEntity(url, body, Void.class);

        return "Mensagem enviada com sucesso";
    }

    public void handleTelegramWebhook(TelegramUpdateDTO update) {
        if (update.message() == null) return;

        MessageFromDTO from = update.message().from();
        MessageChatDTO chat = update.message().chat();
        String text = update.message().text();

        if (TelegramMessagesEnum.WELCOME.text().equals(text)) {
            //lógica
        }
    }
}
