package ro.msg.learning.shop.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendPlainTextSimpleMessage(String to,
                           String subject,
                           String text);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        String... templateModel);

    void sendHtmlMessage(String to,
                                   String subject,
                                   String text) throws MessagingException;

    void sendPlainTextMessageWithAttachment(String to,
                                       String subject,
                                       String text,
                                       String pathToAttachment) throws MessagingException;
}
