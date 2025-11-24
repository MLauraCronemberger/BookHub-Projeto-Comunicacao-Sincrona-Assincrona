package com.example.demo.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory; 
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

//ServicoD	
    @Value("${queue.name}")
    private String queueName;

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routing.key}")
    private String routingKey;

//ServicoE
    @Value("${queue.name.serviceE}")
    private String queueNameServiceE;

    @Value("${exchange.name.serviceE}")
    private String exchangeNameServiceE;

    @Value("${routing.key.serviceE}")
    private String routingKeyServiceE;

//ServicoF
    @Value("${queue.name.serviceF}")
    private String queueNameServiceF;

    @Value("${exchange.name.serviceF}")
    private String exchangeNameServiceF;

    @Value("${routing.key.serviceF}")
    private String routingKeyServiceF;

    //Enviar fila servico D

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    //Enviar fila servico E

    @Bean
    public Queue queueServiceE() {
        return new Queue(queueNameServiceE, true);
    }

    @Bean
    public DirectExchange exchangeServiceE() {
        return new DirectExchange(exchangeNameServiceE);
    }

    @Bean
    public Binding bindingServiceE(Queue queueServiceE, DirectExchange exchangeServiceE) {
        return BindingBuilder.bind(queueServiceE).to(exchangeServiceE).with(routingKeyServiceE);
    }

    //Enviar fila servico F

    @Bean
    public Queue queueServiceF() {
        return new Queue(queueNameServiceF, true);
    }

    @Bean
    public DirectExchange exchangeServiceF() {
        return new DirectExchange(exchangeNameServiceF);
    }

    @Bean
    public Binding bindingServiceF(Queue queueServiceF, DirectExchange exchangeServiceF) {
        return BindingBuilder.bind(queueServiceF).to(exchangeServiceF).with(routingKeyServiceF);
    }

    //Conversor e Rabbit

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
