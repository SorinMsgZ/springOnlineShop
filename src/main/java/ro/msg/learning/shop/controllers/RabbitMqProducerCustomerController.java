package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.RabbitMqReceiverConsumerService;
import ro.msg.learning.shop.services.RabbitMqSenderService;

@RestController
@RequestMapping("/api")
public class RabbitMqProducerCustomerController {
    @Autowired
    private RabbitMqSenderService rabbitMqSenderService;

    @Autowired
    private RabbitMqReceiverConsumerService rabbitMqReceiverConsumerService;

    @Value("${spring.rabbitmq.clientfeedback}")
    private String feedbackToClient;

    @GetMapping("/sendtask/{name}")
    public String sendTaskShowCustomerDetailsByFirstName(@PathVariable String name) {
        rabbitMqSenderService.showCustomerDetailsByFirstName(name);
        return feedbackToClient;
    }
}
