package ro.msg.learning.shop.services;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqReceiverConsumerConfiguration {
    @Bean
    public Queue rabbitMqQueue() {
        return new Queue("rabbitMqQueue");
    }

    @Bean
    public RabbitMqReceiverConsumerService receiver() {
        return new RabbitMqReceiverConsumerService();
    }

    @Bean
    public RabbitMqSenderService sender() {
        return new RabbitMqSenderService();
    }
}
