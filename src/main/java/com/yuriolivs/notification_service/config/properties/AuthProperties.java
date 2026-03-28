package com.yuriolivs.notification_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("auth")
@Getter
@Setter
public class AuthProperties {
    private String key;
    private String header;
}
