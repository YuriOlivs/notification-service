package com.yuriolivs.notification_service.telegram.services;

import com.yuriolivs.notification_service.telegram.TelegramUser;
import com.yuriolivs.notification_service.telegram.TelegramUserRepository;
import com.yuriolivs.notification_service.telegram.dto.TelegramDeleteWebhookDTO;
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

    public TelegramUser updateFirstMessage(Long userId) throws BadRequestException {
        TelegramUser telegramUser = findByUserId(userId);
        telegramUser.setFirstMessage(false);
        return repo.save(telegramUser);
    }

    public void toggleNotifications(Long userId) throws BadRequestException {
        TelegramUser telegramUser = findByUserId(userId);
        telegramUser.setActive(!telegramUser.isActive());
        telegramUser.isActive();
    }

    public void deleteTelegramUser(TelegramDeleteWebhookDTO dto) throws BadRequestException {
        findByUserId(dto.id());
        repo.deleteByTelegramUserId(dto.id());
    }
}
