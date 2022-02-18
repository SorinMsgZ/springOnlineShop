package ro.msg.learning.shop.services;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.repositories.CustomerRepository;

@Service
@RabbitListener(queues = "rabbitMqQueue")
public class RabbitMqReceiverConsumerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Value("${spring.rabbitmq.consumerfeedback}")
    private String consumerFeedback;

    @RabbitHandler
    public void receiveTaskShowCustomerByFirstName(String messageFromBroker) {

        System.out.println(consumerFeedback);
        System.out.println("Hey Server, these would be the processed data/output requested by the Client: " +
                customerService.readByFirstName(messageFromBroker).toString());

    }
}
