package com.example.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.domain.serviced.MediaAdaptation;

@Service
public class ServiceD {
	
    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    
    public ServiceD(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void enviarParaFila(MediaAdaptation nova_adaptacao) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, nova_adaptacao);
        System.out.println("Nova adaptação do livro " + nova_adaptacao.getTitulo()+ " enviada para RabbitMQ!");
    }

}
