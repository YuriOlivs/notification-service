package com.yuriolivs.notification_service.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private Long telegramUserId;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Column(nullable = false)
    private String username;

    @Setter
    @Column()
    private boolean active;

    public TelegramUser(Long telegramUserId, Long chatId, String username, boolean active) {
        this.telegramUserId = telegramUserId;
        this.chatId = chatId;
        this.username = username;
        this.active = active;
    }

}
