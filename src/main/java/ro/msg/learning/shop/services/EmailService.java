package ro.msg.learning.shop.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendPlainTextSimpleMessage(String to);

    void sendSimpleMessageUsingTemplate(String to, String... templateModel);

    void sendHtmlMessage(String to) throws MessagingException;

    void sendPlainTextMessageWithAttachment(String to, String pathToAttachment) throws MessagingException;
}
