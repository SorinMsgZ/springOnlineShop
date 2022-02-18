package ro.msg.learning.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.services.EmailServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;
    @Value("${spring.mail.subject}")
    private String emailSubject;
    @Value("#{'${spring.mail.body}'+'Hellooooo'}")
    private String emailBody;

    @RequestMapping(value = "/sendemail")
    public String sendEmail() throws AddressException, MessagingException, IOException {
        emailService.sendPlainTextSimpleMessage("test@msg.group","Test Java Mail","Hello! It works!!!");
        emailService.sendHtmlMessage("test@msg.group", emailSubject, emailBody);
        return "Email sent successfully";
    }
}
