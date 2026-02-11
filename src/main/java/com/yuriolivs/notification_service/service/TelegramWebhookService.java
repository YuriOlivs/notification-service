package com.yuriolivs.notification_service.service;

import com.yuriolivs.notification_service.dto.TelegramWebhookDTO;
import com.yuriolivs.notification_service.dto.message.MessageChatDTO;
import com.yuriolivs.notification_service.dto.message.MessageFromDTO;
import com.yuriolivs.notification_service.entitiy.TelegramUser;
import com.yuriolivs.notification_service.utils.TelegramCommands;
import com.yuriolivs.notification_service.utils.TelegramMessages;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelegramWebhookService {
    private final TelegramMessageService messageService;
    private final TelegramUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TelegramWebhookService.class);

    public void receiveUpdate(TelegramWebhookDTO update) throws BadRequestException {
        if (update.message() == null) return;

        MessageFromDTO from = update.message().from();
        MessageChatDTO chat = update.message().chat();
        String text = update.message().text();

        logger.info("""
                
                ================= TELEGRAM WEBHOOK =================
                User ID : {}
                Chat ID : {}
                Message : {}
                ====================================================
                
                """, from.id(), chat.id(), text);

        TelegramUser telegramUser = userService.registerIfNotExists(from.id(), chat.id());

        processCommand(telegramUser.getChatId(), text, from.id());
    }

    private void processCommand(Long chatId, String text, Long userId) throws BadRequestException {
        if (text == null || text.isBlank()) return;

        TelegramCommands command = TelegramCommands.fromText(text);
        if (command == null) return;

        switch(command) {
            case START -> messageService.sendStardardizedMessages(chatId, TelegramMessages.WELCOME);
            case ACTIVATE -> messageService.sendStardardizedMessages(chatId, TelegramMessages.REACTIVAED);
            case CANCEL -> {
                userService.deactivateNotifications(userId);
                messageService.sendStardardizedMessages(chatId, TelegramMessages.CANCELLED);
            }
            default -> messageService.sendStardardizedMessages(chatId, TelegramMessages.UNKNOW_COMMAND);
        }
    }
}
