package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;


    @Override
    public void sendPlainTextSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@sorin.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }


    @Override
    public void sendHtmlMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage msg = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(msg);

    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {

    }

    @Override
    public void sendPlainTextMessageWithAttachment(String to, String subject, String text,
                                              String pathToAttachment) throws MessagingException {
        MimeMessage msg = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("my_photo.png", new ClassPathResource(pathToAttachment));

        emailSender.send(msg);

    }
}
