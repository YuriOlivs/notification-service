package com.yuriolivs.notification_service.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TelegramMessages {
    WELCOME("You will now receive notifications from our system. To cancel, send /cancel."),
    CANCELLED("You will no longer receive notifications. To reactivate them, send /activate"),
    REACTIVAED("Your notifications have been reactivated."),
    UNKNOW_COMMAND("Sorry, I didn't understand. To see all possible commands, send /help.");

    private final String text;

    public String value() {
        return text;
    }
}