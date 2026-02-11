package com.yuriolivs.notification_service.service;

import com.yuriolivs.notification_service.dto.TelegramWebhookDTO;
import com.yuriolivs.notification_service.entitiy.TelegramUser;
import com.yuriolivs.notification_service.repository.TelegramUserRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelegramUserService {
    private final TelegramUserRepository repo;

    public TelegramUser findByUserId(Long userId) throws BadRequestException {
        TelegramUser telegramUser = repo.findByTelegramUserId(userId);

        if(telegramUser == null) {
            throw new BadRequestException("User was not found.");
        }

        return telegramUser;
    }

    public TelegramUser registerIfNotExists(
            Long telegramUserId,
            Long chatId
    ) {
        TelegramUser exists = repo.findByTelegramUserId(telegramUserId);

        if(exists == null) {
            TelegramUser telegramUser = new TelegramUser(telegramUserId, chatId, true);
            return repo.save(telegramUser);
        }

        return exists;
    }

    public void deactivateNotifications(Long userId) throws BadRequestException {
        TelegramUser telegramUser = findByUserId(userId);
        telegramUser.setActive(false);
        repo.save(telegramUser);
    }

    public void deleteTelegramUser(Long userId) throws BadRequestException {
        findByUserId(userId);
        repo.deleteByTelegramUserId(userId);
    }
}
