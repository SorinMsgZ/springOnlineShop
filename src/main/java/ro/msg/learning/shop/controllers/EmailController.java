package ro.msg.learning.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.services.EmailServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@RestController
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping(value = "/sendemail")
    public String sendEmail() throws MessagingException {
        emailService.sendPlainTextSimpleMessage("test@msg.group");
        emailService.sendHtmlMessage("test@msg.group");
        return "Email sent successfully";
    }
}
