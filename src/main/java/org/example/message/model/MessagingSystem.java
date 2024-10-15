package org.example.message.model;

import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.message.util.EmailService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessagingSystem {
    private List<User> users;
    private EmailService emailService;
    private final Logger LOGGER = LogManager.getLogger(MessagingSystem.class);

    public void receiveMessage(MessageMail message) {
        if (message.getCategory() == null || message.getBody() == null) {
            LOGGER.debug("Invalid message format.");
            return;
        }

        List<User> subscribedUsers = getUsersBySubscription(message.getCategory());
        for (User user : subscribedUsers) {
            sendMessage(user, message);
        }
    }

    private List<User> getUsersBySubscription(String category) {
        List<User> subscribedUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getSubscriptions().contains(category)) {
                subscribedUsers.add(user);
            }
        }
        return subscribedUsers;
    }

    private void sendMessage(User user, MessageMail message) {
        switch (user.getChannels()) {
            case "email":
                sendEmail(user, message);
                break;
            case "sms":
                sendSms(user, message);
                break;
            default:
                LOGGER.debug("Unknown channel: {}", user.getChannels());
        }

    }

    private void sendEmail(User user, MessageMail message) {
        String subject = "New Message Notification";
        LOGGER.debug("sending mail to {}", user);
        emailService.sendEmail(user.getEmail(), subject, message.getBody());
    }

    private void sendSms(User user, MessageMail message) {
        LOGGER.debug("Sending SMS to {}", user.getName() + ": {}", message.getBody());
    }
}
