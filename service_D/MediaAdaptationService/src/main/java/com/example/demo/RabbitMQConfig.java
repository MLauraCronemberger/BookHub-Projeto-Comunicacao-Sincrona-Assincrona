package com.example.demo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory; // IMPORTANTE: o do Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        
        // foi preciso criar um converter para fazer contentType.startsWith("text")...
        // pq isso vem nulo quando publica mensagem pelo painel do rabbit
        factory.setMessageConverter(new SafeSimpleMessageConverter());

        
        // resolve o problema inicial do "priority nulo"
        factory.setAfterReceivePostProcessors((Message message) -> {
            MessageProperties props = message.getMessageProperties();
            if (props != null) {
                if (props.getPriority() == null) {
                    props.setPriority(0);
                }
            }
            return message;
        });

        return factory;
    }
}

