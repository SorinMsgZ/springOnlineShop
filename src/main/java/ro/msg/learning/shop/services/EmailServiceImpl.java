package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.repositories.OrderRepository;

import javax.mail.*;
import javax.mail.internet.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    OrderRepository orderRepository;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.subject}")
    private String subject;
    @Value("#{'${spring.mail.body}'+' Id= '}")
    private String text;
    private String editedTextBody;

    @Value("${spring.mail.from}")
    private String from;


    public void addOrderIdToEmailBody() {
        Order theOrder = orderRepository.findTopByOrderByIdDesc().orElseThrow();

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("id");
        EvaluationContext context = new StandardEvaluationContext(theOrder);
        String orderId;
        try {
            orderId = "" + (int) expression.getValue(context);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        }

        this.editedTextBody = text + orderId;
    }

    @Override
    public void sendPlainTextSimpleMessage(String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(editedTextBody);
        emailSender.send(message);

    }

    @Override
    public void sendHtmlMessage(String to) throws MessagingException {
        MimeMessage msg = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(editedTextBody, true);

        emailSender.send(msg);

    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String... templateModel) {

    }

    @Override
    public void sendPlainTextMessageWithAttachment(String to, String pathToAttachment) throws MessagingException {
        MimeMessage msg = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("my_photo.png", new ClassPathResource(pathToAttachment));

        emailSender.send(msg);

    }
}
