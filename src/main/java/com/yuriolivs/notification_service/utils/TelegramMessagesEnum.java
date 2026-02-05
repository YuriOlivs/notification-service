package com.yuriolivs.notification_service.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TelegramMessagesEnum {
    WELCOME(
            "Olá! 👋" +
            "Você agora receberá lembretes e notificações relacionadas a promoções no seu carrinho!"
    );

    private final String text;

    public String text() {
        return text;
    }
}