package org.example.message.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.message.model.MessageMail;
import org.example.message.model.MessagingSystem;
import org.example.message.model.User;
import org.example.message.repository.MessageRepository;
import org.example.message.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Value("${user.host}")
    String host;
    @Value("${user.port}")
    String port;
    @Value("${user.mail}")
    String mail;
    @Value("${user.password}")
    String password;
    @Value("${sms.account_sid}")
    String ACCOUNT_SID;
    @Value("${sms.auth_token}")
    String AUTH_TOKEN;

    private final Logger LOGGER = LogManager.getLogger(MessageService.class);

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;

    }

    public void sendMessage(String message1) {
        EmailService emailService = new EmailService(host, port, mail, password);
        List<User> users = new ArrayList<>();
        users.add(new User("1", "Alice", "category2", "email", "mario-09@live.com.ar"));
        users.add(new User("2", "Bob", "category1", "sms", "bob@example.com"));

        MessagingSystem messagingSystem = new MessagingSystem(users, emailService);

        // Example of receiving a message
        MessageMail message = new MessageMail(1, "category2", "body message");
        messagingSystem.receiveMessage(message);
        MessageMail message2 = new MessageMail(2, "categor1", "body message");
        messagingSystem.receiveMessage(message2);
    }

    public void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Message message2 = Message.creator(
                        new PhoneNumber("+525512767436"),
                        new PhoneNumber("+19095513958"),
                        "Mario's test message ")
                .create();

        LOGGER.debug("Message sent with SID: {}", message2.getSid());
    }


}
