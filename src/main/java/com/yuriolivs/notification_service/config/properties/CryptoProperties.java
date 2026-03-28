package com.yuriolivs.notification_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("crypto")
@Getter
@Setter
public class CryptoProperties {
    private String base64Key;
}
