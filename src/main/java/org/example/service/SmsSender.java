package org.example.service;

public class SmsSender implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        System.out.println("[SENDING] Sms :" +message);
    }
}
