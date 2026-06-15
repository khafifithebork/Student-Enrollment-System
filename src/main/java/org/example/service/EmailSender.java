package org.example.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailSender implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        System.out.println("[SENDING] mail :" +message);
    }

}
