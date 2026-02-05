package com.yuriolivs.notification_service.controller;

import com.yuriolivs.notification_service.dto.mail.MailDTO;
import com.yuriolivs.notification_service.dto.mail.OrderTrackingMailDTO;
import com.yuriolivs.notification_service.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("mail")
@AllArgsConstructor
public class MailController {
    private final MailService service;

    @PostMapping
    public ResponseEntity<String> sendMail(
            @RequestBody @Valid MailDTO dto
    ) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(service.sendEmail(dto));
    }

    @PostMapping("/template/order-tracking")
    public ResponseEntity<String> sendOrderTrackingEmail(
            @RequestBody @Valid OrderTrackingMailDTO dto
    ) throws IOException, MessagingException {
        return ResponseEntity.ok(service.sendOrderTrackingMail(dto));
    }
}
