package com.example.saveinformation.notification.sms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "Local")
public class NoSmsNotificationServiceImpl implements SmsNotificationService {
    @Override
    public void sendNotification(String phoneNumber, String message) {
        System.out.printf("%s: %s\n",phoneNumber,message);

    }
}
