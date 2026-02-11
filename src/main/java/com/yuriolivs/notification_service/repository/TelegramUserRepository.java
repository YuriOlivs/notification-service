package com.yuriolivs.notification_service.repository;

import com.yuriolivs.notification_service.entitiy.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    TelegramUser findByTelegramUserId(Long telegramUserId);
    void deleteByTelegramUserId(Long telegramUserId);
}
