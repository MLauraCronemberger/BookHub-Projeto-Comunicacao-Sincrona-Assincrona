package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;



@Configuration
public class RabbitMQConfig {

    // 1. Injeta o nome da fila definido em application.properties
    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue queue() {
        // 2. Cria e declara a fila no RabbitMQ.
        // O valor 'true' indica que a fila é durável (permanece após reinicialização do RabbitMQ).
        return new Queue(queueName, true); 
    }
    
}
