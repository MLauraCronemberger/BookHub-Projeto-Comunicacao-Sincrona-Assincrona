package com.example.demo.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.config.ServiceFFeignClient;

@Service
public class ServiceF {
    @Value("${exchange.name.serviceF}")
    private String exchangeName;

    @Value("${routing.key.serviceF}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    
	private final ServiceFFeignClient Fclient;
    
    public ServiceF(RabbitTemplate rabbitTemplate, ServiceFFeignClient Fclient) {
        this.rabbitTemplate = rabbitTemplate;
        this.Fclient = Fclient;
    }
    
    public void enviarParaFila() {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, "true");
        System.out.println("Nova visualização adicionada na fila");
    }
    
	public int getContador() {
		return Fclient.getContador();
	}

}
