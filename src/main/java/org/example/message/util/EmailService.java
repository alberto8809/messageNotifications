package org.example.message.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.*;


import java.util.Properties;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailService {

    private String host;
    private String port;
    private String username;
    private String password;

    public void sendEmail(String to, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        // properties.put("mail.debug", "true");
        // properties.put("mail.smtp.ssl.enable", "true");


        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the message
            Transport.send(message);
            // LOGGER.debug("Email sent successfully to {}", to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
