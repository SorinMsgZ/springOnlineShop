package ro.msg.learning.shop.services;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSenderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue queue;
    @Value("${spring.rabbitmq.producerfeedback}")
    private String producerfeedback;

    public void showCustomerDetailsByFirstName(String firstName) {
        rabbitTemplate.convertAndSend(queue.getName(), firstName);
        System.out.println(producerfeedback);
    }
}
